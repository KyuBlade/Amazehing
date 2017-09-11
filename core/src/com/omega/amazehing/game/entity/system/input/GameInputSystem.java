package com.omega.amazehing.game.entity.system.input;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input.Keys;
import com.gdx.extension.screen.ScreenManager;
import com.gdx.extension.ui.input.InputArray;
import com.gdx.extension.ui.input.InputHolder;
import com.gdx.extension.ui.input.InputType;
import com.omega.amazehing.Constants;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.TweenFactory;
import com.omega.amazehing.factory.entity.PlayerFactory;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.movement.MovementDirectionComponent;
import com.omega.amazehing.game.entity.system.InteractiveObjectSystem;
import com.omega.amazehing.game.entity.system.render.CameraSystem;
import com.omega.amazehing.game.input.action.impl.ReloadInputAction;
import com.omega.amazehing.game.input.action.impl.action.MoveBackwardInputAction;
import com.omega.amazehing.game.input.action.impl.action.MoveForwardInputAction;
import com.omega.amazehing.game.input.action.impl.action.MoveLeftInputAction;
import com.omega.amazehing.game.input.action.impl.action.MoveRightInputAction;
import com.omega.amazehing.game.input.action.impl.ui.CharacterInputAction;
import com.omega.amazehing.game.input.action.impl.ui.ConsoleInputAction;
import com.omega.amazehing.game.input.action.impl.ui.DebugInputAction;
import com.omega.amazehing.game.input.action.impl.ui.DebugWindowInputAction;
import com.omega.amazehing.game.input.action.impl.ui.EntityViewerInputAction;
import com.omega.amazehing.game.input.action.impl.ui.InventoryInputAction;
import com.omega.amazehing.game.input.action.impl.ui.OpenGameMenuInputAction;
import com.omega.amazehing.game.input.action.impl.ui.SkillInputAction;
import com.omega.amazehing.handler.PlayerHandler;
import com.omega.amazehing.screen.game.GameScreen;
import com.omega.amazehing.setting.SettingManager;

public class GameInputSystem extends InputSystem {

    private ScreenManager screenManager;
    private PlayerHandler playerHandler;
    private TweenFactory tweenFactory;
    private GameScreen gameScreen;

    private Entity camera;

    private static final ComponentMapper<MovementDirectionComponent> moveDirMapper = ComponentMapperHandler
	    .getMovementDirectionMapper();

    public GameInputSystem(ScreenManager screenManager, SettingManager settingManager,
	    FactoryManager factoryManager) {
	super(settingManager, screenManager.getInputProcessor());

	this.screenManager = screenManager;
	this.settingManager = settingManager;

	PlayerFactory _playerFactory = factoryManager.getFactory(PlayerFactory.class);
	playerHandler = _playerFactory.getPlayerHandler();

	tweenFactory = factoryManager.getFactory(TweenFactory.class);

	gameScreen = screenManager.getScreen(GameScreen.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
	super.addedToEngine(engine);

	initControls();
    }

    private void initControls() {
	controlMapping.put(Constants.Settings.Controls.Mapping.TAG_FORWARD,
		new MoveForwardInputAction(playerHandler));
	controlMapping.put(Constants.Settings.Controls.Mapping.TAG_BACKWARD,
		new MoveBackwardInputAction(playerHandler));
	controlMapping.put(Constants.Settings.Controls.Mapping.TAG_LEFT, new MoveLeftInputAction(
		playerHandler));
	controlMapping.put(Constants.Settings.Controls.Mapping.TAG_RIGHT, new MoveRightInputAction(
		playerHandler));
	controlMapping.put(Constants.Settings.Controls.Mapping.TAG_INVENTORY,
		new InventoryInputAction(screenManager));
	controlMapping.put(Constants.Settings.Controls.Mapping.TAG_CHARACTER,
		new CharacterInputAction(screenManager));
	controlMapping.put(Constants.Settings.Controls.Mapping.TAG_SKILLS, new SkillInputAction(
		screenManager));

	settingManager.putInputArray(Constants.Settings.Controls.Mapping.TAG_GAME_MENU,
		new InputArray(new InputHolder(InputType.Keyboard, Keys.ESCAPE)));
	settingManager.putInputArray(Constants.Settings.Controls.Mapping.TAG_DISPLAY_CONSOLE,
		new InputArray(new InputHolder(InputType.Keyboard, Keys.F1)));
	settingManager.putInputArray(Constants.Settings.Controls.Mapping.TAG_DISPLAY_DEBUG,
		new InputArray(new InputHolder(InputType.Keyboard, Keys.F2)));
	settingManager.putInputArray(Constants.Settings.Controls.Mapping.TAG_ENTITY_VIEWER,
		new InputArray(new InputHolder(InputType.Keyboard, Keys.F3)));
	settingManager.putInputArray(Constants.Settings.Controls.Mapping.TAG_DISPLAY_DEBUG_SCREEN,
		new InputArray(new InputHolder(InputType.Keyboard, Keys.F4)));
	settingManager.putInputArray(Constants.Settings.Controls.Mapping.TAG_RELOAD,
		new InputArray(new InputHolder(InputType.Keyboard, Keys.F5)));

	controlMapping.put(Constants.Settings.Controls.Mapping.TAG_DISPLAY_CONSOLE,
		new ConsoleInputAction(screenManager));
	controlMapping.put(Constants.Settings.Controls.Mapping.TAG_GAME_MENU,
		new OpenGameMenuInputAction(screenManager, engine));
	controlMapping.put(Constants.Settings.Controls.Mapping.TAG_DISPLAY_DEBUG,
		new DebugWindowInputAction(gameScreen.getDebugWin()));
	controlMapping.put(Constants.Settings.Controls.Mapping.TAG_DISPLAY_DEBUG_SCREEN,
		new DebugInputAction(screenManager));
	controlMapping.put(Constants.Settings.Controls.Mapping.TAG_ENTITY_VIEWER,
		new EntityViewerInputAction(screenManager));
	controlMapping.put(Constants.Settings.Controls.Mapping.TAG_RELOAD, new ReloadInputAction(
		gameScreen));

	// screenManager.getStage().addListener(new InputListener() {
	//
	// @Override
	// public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	// InteractiveObjectSystem _interObjSys = engine
	// .getSystem(InteractiveObjectSystem.class);
	// if (_interObjSys != null) {
	// _interObjSys.setUpdate(false);
	// }
	// }
	//
	// @Override
	// public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	// InteractiveObjectSystem _interObjSys = engine
	// .getSystem(InteractiveObjectSystem.class);
	// if (_interObjSys != null) {
	// _interObjSys.setUpdate(true);
	// }
	// }
	// });
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	boolean _processed = super.touchDown(screenX, screenY, pointer, button);

	if (!_processed) {
	    InteractiveObjectSystem _interObjSys = engine.getSystem(InteractiveObjectSystem.class);
	    if (_interObjSys != null) {
		_processed = _interObjSys.fire(button, true);
	    }
	}

	return _processed;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
	boolean _processed = super.touchUp(screenX, screenY, pointer, button);

	if (!_processed) {
	    InteractiveObjectSystem _interObjSys = engine.getSystem(InteractiveObjectSystem.class);
	    if (_interObjSys != null) {
		_processed = _interObjSys.fire(button, false);
	    }
	}

	return _processed;
    }

    @Override
    public boolean scrolled(int amount) {
	if (camera == null) {
	    CameraSystem _cameraSystem = engine.getSystem(CameraSystem.class);
	    camera = _cameraSystem.getMainCameraEntity();
	}

	tweenFactory.createCameraZoom(camera, amount);

	return true;
    }

    @Override
    public void setProcessing(boolean processing) {
	super.setProcessing(processing);

	if (!processing) {
	    Entity _player = playerHandler.getPlayer();
	    if (_player != null) {
		moveDirMapper.get(_player).resetDirections();
	    }
	    engine.getSystem(InteractiveObjectSystem.class).reset();
	}
    }
}