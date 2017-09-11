package com.omega.amazehing.game.input.action.impl.ui;

import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.game.input.action.InputAction;
import com.omega.amazehing.screen.ConsoleScreen;

public class ConsoleInputAction implements InputAction {

    private ScreenManager screenManager;
    private ConsoleScreen consoleScreen;

    public ConsoleInputAction(ScreenManager screenManager) {
	this.screenManager = screenManager;
	consoleScreen = screenManager.getScreen(ConsoleScreen.class);
    }

    @Override
    public void onDown() {
	if (consoleScreen.isActive()) {
	    screenManager.hideScreen(consoleScreen);
	} else {
	    screenManager.showScreen(consoleScreen);
	}
    }

    @Override
    public void onUp() {
    }
}