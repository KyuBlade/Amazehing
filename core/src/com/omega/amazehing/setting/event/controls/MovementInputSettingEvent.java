package com.omega.amazehing.setting.event.controls;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.entity.PlayerFactory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.movement.MovementTypeComponent;
import com.omega.amazehing.game.entity.component.movement.MovementTypeComponent.MovementType;
import com.omega.amazehing.handler.PlayerHandler;
import com.omega.amazehing.screen.game.GameScreen;
import com.omega.amazehing.setting.SettingEvent;

public class MovementInputSettingEvent implements SettingEvent<String> {

    private ScreenManager screenManager;

    private ImageTextButton absoluteButton;
    private ImageTextButton relativeButton;

    public MovementInputSettingEvent(ScreenManager screenManager, ImageTextButton absoluteButton,
	    ImageTextButton relativeButton) {
	this.screenManager = screenManager;

	this.absoluteButton = absoluteButton;
	this.relativeButton = relativeButton;
    }

    @Override
    public void apply(String key, String value) {
	GameScreen _gameScreen = screenManager.getScreen(GameScreen.class);
	if (_gameScreen == null) {
	    return;
	}

	EntityEngine _engine = _gameScreen.getEntityEngine();
	FactoryManager _factoryManager = _gameScreen.getFactoryManager();
	PlayerFactory _playerFactory = _factoryManager.getFactory(PlayerFactory.class);
	PlayerHandler _playerHandler = _playerFactory.getPlayerHandler();

	Entity _player = _playerHandler.getPlayer();
	if (_player == null) {
	    return;
	}

	if (value.equals("absolute")) {
	    _player.add(_engine.createComponent(MovementTypeComponent.class).setMovementType(
		    MovementType.ABSOLUTE));
	} else if (value.equals("relative")) {
	    _player.add(_engine.createComponent(MovementTypeComponent.class).setMovementType(
		    MovementType.RELATIVE));
	}
    }

    @Override
    public void reset(String key, String oldValue) {
	if (oldValue.equals("absolute")) {
	    absoluteButton.setChecked(true);
	} else if (oldValue.equals("relative")) {
	    relativeButton.setChecked(true);
	}
    }
}