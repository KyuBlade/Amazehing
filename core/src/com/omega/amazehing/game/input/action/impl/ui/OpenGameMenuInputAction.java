package com.omega.amazehing.game.input.action.impl.ui;

import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.system.input.GameInputSystem;
import com.omega.amazehing.game.entity.system.input.GameMenuInputSystem;
import com.omega.amazehing.game.input.action.InputAction;
import com.omega.amazehing.screen.game.GameMenuScreen;

public class OpenGameMenuInputAction implements InputAction {

    private ScreenManager screenManager;
    private EntityEngine engine;
    private GameInputSystem gameInputSystem;
    private GameMenuInputSystem gameMenuInputSystem;

    public OpenGameMenuInputAction(ScreenManager screenManager, EntityEngine engine) {
	this.screenManager = screenManager;
	this.engine = engine;
    }

    @Override
    public void onDown() {
	gameInputSystem = engine.getSystem(GameInputSystem.class);
	gameMenuInputSystem = engine.getSystem(GameMenuInputSystem.class);

	gameInputSystem.setProcessing(false);
	gameMenuInputSystem.setProcessing(true);
	screenManager.showScreen(GameMenuScreen.class);
    }

    @Override
    public void onUp() {
    }
}