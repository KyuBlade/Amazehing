package com.omega.amazehing.game.input.action.impl.ui;

import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.game.input.action.InputAction;
import com.omega.amazehing.screen.DebugScreen;

public class DebugInputAction implements InputAction {

    private ScreenManager screenManager;
    private DebugScreen debugScreen;

    public DebugInputAction(ScreenManager screenManager) {
	this.screenManager = screenManager;
	this.debugScreen = screenManager.getScreen(DebugScreen.class);
    }

    @Override
    public void onDown() {
    }

    @Override
    public void onUp() {
	if (debugScreen.isActive()) {
	    screenManager.hideScreen(debugScreen);
	} else {
	    screenManager.showScreen(debugScreen);
	}
    }
}