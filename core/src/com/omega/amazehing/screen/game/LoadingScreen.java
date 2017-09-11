package com.omega.amazehing.screen.game;

import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters.LoadedCallback;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.gdx.extension.screen.BaseScreen;
import com.gdx.extension.screen.ScreenManager;
import com.gdx.extension.ui.ProgressBar;
import com.mensa.database.sqlite.core.Database;
import com.mensa.database.sqlite.core.DatabaseFactory;
import com.mensa.database.sqlite.core.SQLiteContext;
import com.omega.amazehing.Assets;
import com.omega.amazehing.Constants;
import com.omega.amazehing.GameCore;
import com.omega.amazehing.database.DatabaseConstants;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.NamedThreadFactory;
import com.omega.amazehing.factory.entity.PlayerFactory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.system.DelaySystem;
import com.omega.amazehing.game.entity.system.EntityNotifierSystem;
import com.omega.amazehing.game.entity.system.GeneratorSystem;
import com.omega.amazehing.game.entity.system.HealthSystem;
import com.omega.amazehing.game.entity.system.InteractiveObjectSystem;
import com.omega.amazehing.game.entity.system.MovementSystem;
import com.omega.amazehing.game.entity.system.PhysicsSystem;
import com.omega.amazehing.game.entity.system.SaveSystem;
import com.omega.amazehing.game.entity.system.TriggerSystem;
import com.omega.amazehing.game.entity.system.TweenSystem;
import com.omega.amazehing.game.entity.system.ai.AiSystem;
import com.omega.amazehing.game.entity.system.ai.BehaviorTreeSystem;
import com.omega.amazehing.game.entity.system.ai.PathfindingSystem;
import com.omega.amazehing.game.entity.system.ai.SteeringSystem;
import com.omega.amazehing.game.entity.system.balancer.LoadBalancerSystem;
import com.omega.amazehing.game.entity.system.input.GameInputSystem;
import com.omega.amazehing.game.entity.system.input.GameMenuInputSystem;
import com.omega.amazehing.game.entity.system.paging.PagingSystem;
import com.omega.amazehing.game.entity.system.render.AnimationSystem;
import com.omega.amazehing.game.entity.system.render.CameraSystem;
import com.omega.amazehing.game.entity.system.render.RenderSystem;
import com.omega.amazehing.game.entity.system.render.ViewportSystem;
import com.omega.amazehing.game.level.LevelSetting;
import com.omega.amazehing.game.level.load.AnimationLoader;
import com.omega.amazehing.game.level.load.RenderContextCallable;
import com.omega.amazehing.game.level.load.ThreadCallable;
import com.omega.amazehing.handler.PlayerHandler;
import com.omega.amazehing.render.ParticleManager;
import com.omega.amazehing.render.RegionManager;
import com.omega.amazehing.util.Utils;

public class LoadingScreen extends BaseScreen {

    private static final Logger logger = LoggerFactory.getLogger(LoadingScreen.class);

    private com.badlogic.gdx.scenes.scene2d.ui.Stack progressStack;
    private ProgressBar progressBar;
    private Label progressValue;
    private Label progressTextValue;

    private Stack<Callable<Boolean>> tasks;
    private Callable<Boolean> lastTask;
    private Future<Boolean> loadFuture;
    private boolean canNext;

    private GameScreen gameScreen;
    private ExecutorService executor;
    private GameCore core;

    private InternalFileHandleResolver resolver;
    private AssetManager assetManager;
    private String saveName;

    private AssetDescriptor<TextureAtlas> levelAtlas;
    private AssetDescriptor<TextureAtlas> itemAtlas;
    private AssetDescriptor<TextureAtlas> skillAtlas;

    private int taskCount;
    private int currentTask;
    private String progressLabel;

    public LoadingScreen(ScreenManager screenManager, GameCore core) {
	super(screenManager, 99);

	this.core = core;

	layout.setSkin(skin);
	layout.setBackground("backgrounds/grey");

	progressStack = new com.badlogic.gdx.scenes.scene2d.ui.Stack();
	progressBar = new ProgressBar(skin);
	progressValue = new Label("", skin);

	progressStack.add(progressBar);
	progressStack.add(progressValue);

	progressTextValue = new Label("", skin);

	layout.add(progressStack).colspan(2).fillX().row();
	layout.add(progressTextValue).minWidth(250f).padLeft(5f).left();

	tasks = new Stack<Callable<Boolean>>();
	executor = Executors.newSingleThreadExecutor(new NamedThreadFactory("Loading Thread"));

	resolver = new InternalFileHandleResolver();
	assetManager = new AssetManager(resolver);
	assetManager.setLoader(Animation.class, new AnimationLoader(resolver));
	assetManager.setLoader(FreeTypeFontGenerator.class,
		new FreeTypeFontGeneratorLoader(resolver));
	assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
	// assetManager.getLogger().setLevel(Application.LOG_DEBUG);
    }

    public void generate(LevelSetting setting) {
	saveName = setting.getName();

	tasks.add(createGame());
	tasks.add(prepareAssets());
	tasks.add(loadAssets());
	tasks.add(loadCache());
	tasks.add(loadFromDatabase());
	tasks.add(loadEntityWorld());
	tasks.add(createPhysicsWorld());
	tasks.add(initializeFactories());
	tasks.add(initializeSystems());
	tasks.add(initializeRenderSystems());
	tasks.add(switchGame());
	tasks.add(generateLevel(setting));
	tasks.add(save());
	tasks.add(createInputs());
    }

    public void loadSave(String name) {
	this.saveName = name;

	tasks.add(createGame());
	tasks.add(prepareAssets());
	tasks.add(loadAssets());
	tasks.add(loadCache());
	tasks.add(loadFromDatabase());
	tasks.add(loadEntityWorld());
	tasks.add(createPhysicsWorld());
	tasks.add(initializeFactories());
	tasks.add(initializeSystems());
	tasks.add(initializeRenderSystems());
	tasks.add(switchGame());
	tasks.add(load());
	tasks.add(createInputs());
    }

    public void loadSave(FileHandle saveFile) {
	loadSave(saveFile.name());
    }

    private ThreadCallable prepareAssets() {
	taskCount += 7;
	return new ThreadCallable() {

	    @SuppressWarnings("unchecked")
	    @Override
	    public Boolean call() throws Exception {
		currentTask++;

		gameScreen.setAssetManager(assetManager);

		LoadedCallback _loadCallback = new LoadedCallback() {

		    @SuppressWarnings("rawtypes")
		    @Override
		    public void finishedLoading(AssetManager assetManager, String fileName,
			    Class type) {
			currentTask++;
		    }
		};

		levelAtlas = Assets.Atlases.LEVEL;
		levelAtlas.params.loadedCallback = _loadCallback;

		itemAtlas = Assets.Atlases.ITEMS;
		itemAtlas.params.loadedCallback = _loadCallback;

		skillAtlas = Assets.Atlases.SKILLS;
		skillAtlas.params.loadedCallback = _loadCallback;

		AssetDescriptor<BitmapFont> _arialFont = Assets.Fonts.ARIAL;
		_arialFont.params.loadedCallback = _loadCallback;
		assetManager.load(Assets.Fonts.ARIAL);

		assetManager.load(levelAtlas);
		assetManager.load(itemAtlas);
		assetManager.load(skillAtlas);

		// Animations
		AssetDescriptor<Animation> _blueSmoke = Assets.Animations.BLUE_SMOKE;
		_blueSmoke.params.loadedCallback = _loadCallback;
		assetManager.load(_blueSmoke);

		AssetDescriptor<Animation> _breach = Assets.Animations.BREACH;
		_blueSmoke.params.loadedCallback = _loadCallback;
		assetManager.load(_breach);

		AssetDescriptor<Animation> _player = Assets.Animations.Player.STAND;
		_player.params.loadedCallback = _loadCallback;
		assetManager.load(_player);

		// Particles
		LoadedCallback _particleCallback = new LoadedCallback() {

		    @SuppressWarnings("rawtypes")
		    @Override
		    public void finishedLoading(AssetManager assetManager, String fileName,
			    Class type) {
			currentTask++;
			ParticleEffect _effect = assetManager.get(fileName, ParticleEffect.class);
			_effect.scaleEffect(Constants.Level.WORLD_SCALE_FACTOR);

			ParticleManager.getInstance().addParticleEffect(fileName, _effect, 5, 10);
		    }
		};
		ParticleEffectParameter _parameter = new ParticleEffectParameter();
		_parameter.loadedCallback = _particleCallback;

		AssetDescriptor<ParticleEffect> _ringFire = Assets.Particles.RING_FIRE;
		_ringFire.params.loadedCallback = _particleCallback;
		assetManager.load(_ringFire);

		return true;
	    }
	};
    }

    private RenderContextCallable loadAssets() {
	return new RenderContextCallable() {

	    @Override
	    public Boolean call() throws Exception {
		progressLabel = core.getI18n().get("loading.task.assets");

		return assetManager.update();
	    }
	};
    }

    private ThreadCallable loadCache() {
	return new ThreadCallable() {

	    @Override
	    public Boolean call() throws Exception {
		new RegionManager(assetManager);

		return true;
	    }
	};
    }

    private ThreadCallable loadFromDatabase() {
	taskCount++;
	return new ThreadCallable() {

	    @Override
	    public Boolean call() throws Exception {
		currentTask++;
		progressLabel = core.getI18n().get("loading.task.initialize.database");

		Database _staticDatabase = DatabaseFactory.getNewDatabase(
			new SQLiteContext<Application>(Gdx.app), true,
			DatabaseConstants.COMMON_DATA_DB_PATH,
			DatabaseConstants.COMMON_DATA_DB_VERSION, null, null);
		_staticDatabase.setupDatabase();
		_staticDatabase.openOrCreateDatabase();
		gameScreen.setStaticDatabase(_staticDatabase);

		return true;
	    }
	};
    }

    private ThreadCallable loadEntityWorld() {
	taskCount++;
	return new ThreadCallable() {

	    @Override
	    public Boolean call() throws Exception {
		currentTask++;
		progressLabel = core.getI18n().get("loading.task.world");

		EntityEngine _engine = new EntityEngine();
		gameScreen.setEntityEngine(_engine);

		// screenManager.registerScreen(new EntityViewerScreen(screenManager, _engine), false);

		return true;
	    }
	};
    }

    private ThreadCallable createPhysicsWorld() {
	taskCount++;
	return new ThreadCallable() {

	    @Override
	    public Boolean call() throws Exception {
		EntityEngine _engine = gameScreen.getEntityEngine();
		_engine.addSystem(new PhysicsSystem());

		return true;
	    }
	};
    }

    private ThreadCallable initializeFactories() {
	taskCount++;
	return new ThreadCallable() {

	    @Override
	    public Boolean call() throws Exception {
		currentTask++;
		FactoryManager _factoryManager = new FactoryManager(assetManager,
			core.getSettingManager(), core.getI18n(), gameScreen.getEntityEngine(),
			gameScreen.getStaticDatabase());
		gameScreen.setFactoryManager(_factoryManager);

		return true;
	    }
	};
    }

    private ThreadCallable initializeSystems() {
	taskCount += 13;
	return new ThreadCallable() {

	    @Override
	    public Boolean call() throws Exception {
		progressLabel = core.getI18n().get("loading.task.systems");

		FactoryManager _factoryManager = gameScreen.getFactoryManager();
		EntityEngine _engine = gameScreen.getEntityEngine();

		currentTask++;
		_engine.addSystem(new LoadBalancerSystem());

		currentTask++;
		_engine.addSystem(new MovementSystem());

		currentTask++;
		_engine.addSystem(new TriggerSystem());

		currentTask++;
		_engine.addSystem(new AiSystem());

		currentTask++;
		_engine.addSystem(new PathfindingSystem(_factoryManager));

		currentTask++;
		_engine.addSystem(new InteractiveObjectSystem());

		currentTask++;
		_engine.addSystem(new DelaySystem());

		currentTask++;
		_engine.addSystem(new SteeringSystem());

		currentTask++;
		_engine.addSystem(new BehaviorTreeSystem(_factoryManager));

		currentTask++;
		_engine.addSystem(new TweenSystem(_factoryManager));

		currentTask++;
		_engine.addSystem(new HealthSystem(screenManager, _factoryManager));

		currentTask++;
		_engine.addSystem(new SaveSystem(_factoryManager, saveName));

		currentTask++;
		_engine.addSystem(new EntityNotifierSystem());

		return true;
	    }
	};
    }

    private RenderContextCallable initializeRenderSystems() {
	taskCount++;
	return new RenderContextCallable() {

	    @Override
	    public Boolean call() throws Exception {
		currentTask++;

		FactoryManager _factoryManager = gameScreen.getFactoryManager();
		PlayerFactory _playerFactory = _factoryManager.getFactory(PlayerFactory.class);
		EntityEngine _engine = gameScreen.getEntityEngine();
		PhysicsSystem _physSystem = _engine.getSystem(PhysicsSystem.class);
		World _world = _physSystem.getWorld();
		PlayerHandler _playerHandler = _playerFactory.getPlayerHandler();

		CameraSystem _cameraSystem = new CameraSystem(_playerHandler);
		_engine.addSystem(_cameraSystem);

		Camera _camera = _cameraSystem.getMainCamera();

		ViewportSystem _viewportSystem = new ViewportSystem();
		_engine.addSystem(_viewportSystem);

		Stage _stage = screenManager.getStage();
		Batch _batch = _stage.getBatch();
		RenderSystem _renderSystem = new RenderSystem(_world, _batch);
		_engine.addSystem(_renderSystem);

		AnimationSystem _animationSystem = new AnimationSystem();
		_engine.addSystem(_animationSystem);

		_engine.addSystem(new PagingSystem(_factoryManager, _camera));

		return true;
	    }
	};
    }

    private RenderContextCallable createGame() {
	taskCount++;
	return new RenderContextCallable() {

	    @Override
	    public Boolean call() throws Exception {
		currentTask++;

		gameScreen = screenManager.registerScreen(new GameScreen(screenManager, core),
			false);

		return true;
	    }
	};
    }

    private ThreadCallable createInputs() {
	taskCount++;
	return new ThreadCallable() {

	    @Override
	    public Boolean call() throws Exception {
		currentTask++;
		EntityEngine _engine = gameScreen.getEntityEngine();

		FactoryManager _factoryManager = gameScreen.getFactoryManager();
		_engine.addSystem(new GameInputSystem(screenManager, core.getSettingManager(),
			_factoryManager));
		_engine.addSystem(new GameMenuInputSystem(core.getSettingManager(), screenManager));

		return true;
	    }

	};
    }

    private ThreadCallable generateLevel(final LevelSetting setting) {
	taskCount++;
	return new ThreadCallable() {

	    @Override
	    public Boolean call() throws Exception {
		currentTask++;
		progressLabel = core.getI18n().get("loading.task.generate");

		gameScreen.setLevelSetting(setting);

		GeneratorSystem _generatorSystem = new GeneratorSystem(setting,
			gameScreen.getFactoryManager());
		gameScreen.getEntityEngine().addSystem(_generatorSystem);
		_generatorSystem.generate();

		return true;
	    }
	};
    }

    private ThreadCallable save() {
	taskCount++;
	return new ThreadCallable() {

	    @Override
	    public Boolean call() throws Exception {
		currentTask++;
		progressLabel = "Saving ...";

		EntityEngine _engine = gameScreen.getEntityEngine();
		GeneratorSystem _generatorSystem = _engine.getSystem(GeneratorSystem.class);
		SaveSystem _saveSystem = _engine.getSystem(SaveSystem.class);
		_engine.removeSystem(_generatorSystem);

		_saveSystem.saveAll(_generatorSystem.getGenerator());

		return true;
	    }
	};
    }

    private ThreadCallable load() {
	taskCount++;
	return new ThreadCallable() {

	    @Override
	    public Boolean call() throws Exception {
		currentTask++;
		progressLabel = "Loading ...";

		EntityEngine _engine = gameScreen.getEntityEngine();
		SaveSystem _saveSystem = _engine.getSystem(SaveSystem.class);
		_saveSystem.load();

		return true;
	    }
	};
    }

    private RenderContextCallable switchGame() {
	taskCount++;
	return new RenderContextCallable() {

	    @Override
	    public Boolean call() throws Exception {
		currentTask++;

		progressLabel = core.getI18n().get("loading.task.ui");
		gameScreen.initialize();
		screenManager.showScreen(gameScreen);

		return true;
	    }
	};
    }

    @Override
    public void render(float delta) {
	if (executor.isShutdown()) {
	    return;
	}

	super.render(delta);

	float _progress = (float) currentTask / (float) taskCount;
	progressTextValue.setText(progressLabel);
	progressBar.setPercent(_progress);
	// Gdx.app.debug("Load progress", currentTask + " / " + taskCount);
	try {
	    if (lastTask == null) {
		lastTask = nextTask();
		if (lastTask == null) {
		    screenManager.unregisterScreen(this);
		}
	    }

	    if (lastTask instanceof ThreadCallable) {
		if (loadFuture == null) {
		    loadFuture = executor.submit(lastTask);
		} else if (loadFuture.isDone()) {
		    canNext = loadFuture.get();
		    loadFuture = null;
		}
	    } else if (lastTask instanceof RenderContextCallable) {
		canNext = lastTask.call();
	    }

	    if (canNext) {
		lastTask = null;
		canNext = false;
	    }
	} catch (Exception e) {
	    Utils.reportFatalException(e);
	}
    }

    private Callable<Boolean> nextTask() throws ArrayIndexOutOfBoundsException {
	if (!tasks.empty()) {
	    return tasks.remove(0);
	}

	return null;
    }

    private void terminate() {
	executor.shutdownNow();
	screenManager.unregisterScreen(this);
    }
}