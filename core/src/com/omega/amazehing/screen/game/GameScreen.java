package com.omega.amazehing.screen.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.gdx.extension.screen.BaseScreen;
import com.gdx.extension.screen.ScreenManager;
import com.mensa.database.sqlite.core.Database;
import com.mensa.database.sqlite.core.SQLiteException;
import com.omega.amazehing.Constants;
import com.omega.amazehing.GameCore;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.system.render.ViewportSystem;
import com.omega.amazehing.game.level.LevelSetting;
import com.omega.amazehing.game.level.generator.GenerationAlgorithm;
import com.omega.amazehing.screen.ConsoleScreen;
import com.omega.amazehing.ui.DebugWindow;

public class GameScreen extends BaseScreen {

    private AssetManager assetManager;
    private Database staticDatabase;
    private FactoryManager factoryManager;
    private ViewportSystem viewportSystem;
    private LevelSetting levelSetting;

    private GameCore core;

    private DebugWindow debugWin;

    private EntityEngine entityEngine;

    public GameScreen(ScreenManager screenManager, GameCore core) {
	super(screenManager, 2);

	this.core = core;

	debugWin = new DebugWindow(skin, core.getI18n());
	debugWin.setVisible(false);
	layout.addActor(debugWin);

    }

    public void initialize() throws Exception {
	layout.setName("GameScreenLayout");

	viewportSystem = entityEngine.getSystem(ViewportSystem.class);

	DragAndDrop _dragnDrop = new DragAndDrop();
	_dragnDrop.setDragTime(75);

	screenManager.registerScreen(new GameMenuScreen(screenManager, core, entityEngine), false);
	screenManager.registerScreen(new ConsoleScreen(screenManager, factoryManager), false);
	screenManager.registerScreen(new GameInterfaceScreen(screenManager, core.getI18n(),
		entityEngine, factoryManager, _dragnDrop));
    }

    @Override
    public void render(float delta) {
	entityEngine.update(delta);
    }

    public void reload() {
	screenManager.unregisterScreen(GameScreen.class);
	LoadingScreen _loadScreen = screenManager.registerScreen(new LoadingScreen(screenManager,
		core));
	_loadScreen.generate(new LevelSetting("Test", GenerationAlgorithm.RECURSIVE_BACKTRACKER,
		6811638682L /* 67684547L */, 10, 10, Constants.Generator.CELL_SIZE));
    }

    @Override
    public void resize(int width, int height) {
	super.resize(width, height);

	if (viewportSystem != null) {
	    viewportSystem.resize(width, height);
	}
    }

    @Override
    public void dispose() {
	if (entityEngine != null) {
	    entityEngine.dispose();
	}

	screenManager.unregisterScreen(GameInterfaceScreen.class);
	screenManager.unregisterScreen(GameMenuScreen.class);
	screenManager.unregisterScreen(EntityViewerScreen.class);
	screenManager.unregisterScreen(ConsoleScreen.class);

	if (staticDatabase != null) {
	    try {
		staticDatabase.closeDatabase();
	    } catch (SQLiteException e) {
	    }
	}

	if (factoryManager != null) {
	    factoryManager.dispose();
	}
	if (assetManager != null) {
	    assetManager.dispose();
	}
    }

    public Database getStaticDatabase() {
	return staticDatabase;
    }

    protected void setStaticDatabase(Database staticDatabase) {
	this.staticDatabase = staticDatabase;
    }

    public EntityEngine getEntityEngine() {
	return entityEngine;
    }

    public void setEntityEngine(EntityEngine entityEngine) {
	this.entityEngine = entityEngine;
    }

    protected AssetManager getAssetManager() {
	return assetManager;
    }

    protected void setAssetManager(AssetManager assetManager) {
	this.assetManager = assetManager;
    }

    protected void setFactoryManager(FactoryManager factoryManager) {
	this.factoryManager = factoryManager;
    }

    public FactoryManager getFactoryManager() {
	return factoryManager;
    }

    public LevelSetting getLevelSetting() {
	return levelSetting;
    }

    protected void setLevelSetting(LevelSetting levelSetting) {
	this.levelSetting = levelSetting;
    }

    public DebugWindow getDebugWin() {
	return debugWin;
    }
}