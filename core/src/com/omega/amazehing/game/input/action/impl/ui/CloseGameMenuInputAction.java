package com.omega.amazehing.game.input.action.impl.ui;

import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.system.input.GameInputSystem;
import com.omega.amazehing.game.entity.system.input.GameMenuInputSystem;
import com.omega.amazehing.game.input.action.InputAction;
import com.omega.amazehing.screen.game.GameMenuScreen;

public class CloseGameMenuInputAction implements InputAction {

    private ScreenManager screenManager;
    private GameInputSystem gameInputSystem;
    private GameMenuInputSystem gameMenuInputSystem;

    public CloseGameMenuInputAction(ScreenManager screenManager, EntityEngine engine) {
	this.screenManager = screenManager;

	gameInputSystem = engine.getSystem(GameInputSystem.class);
	gameMenuInputSystem = engine.getSystem(GameMenuInputSystem.class);
    }

    @Override
    public void onDown() {
	gameMenuInputSystem.setProcessing(false);
	gameInputSystem.setProcessing(true);
	screenManager.hideScreen(GameMenuScreen.class);
    }

    @Override
    public void onUp() {
    }
}