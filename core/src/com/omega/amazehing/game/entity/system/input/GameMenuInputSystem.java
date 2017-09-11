package com.omega.amazehing.game.entity.system.input;

import com.badlogic.ashley.core.Engine;
import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.Constants;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.input.action.impl.ui.CloseGameMenuInputAction;
import com.omega.amazehing.setting.SettingManager;

public class GameMenuInputSystem extends InputSystem {

    private ScreenManager screenManager;

    public GameMenuInputSystem(SettingManager settingManager, ScreenManager screenManager) {
	super(settingManager, screenManager.getInputProcessor());

	this.screenManager = screenManager;
    }

    @Override
    public void addedToEngine(Engine engine) {
	this.engine = (EntityEngine) engine;

	controlMapping.put(Constants.Settings.Controls.Mapping.TAG_GAME_MENU,
		new CloseGameMenuInputAction(screenManager, this.engine));
    }
}