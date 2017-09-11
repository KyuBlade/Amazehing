package com.omega.amazehing.game.input.action.impl.ui;

import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.game.input.action.InputAction;
import com.omega.amazehing.screen.game.CharacterScreen;

public class CharacterInputAction implements InputAction {

    private ScreenManager screenManager;
    private CharacterScreen characterScreen;

    public CharacterInputAction(ScreenManager screenManager) {
	this.screenManager = screenManager;
    }

    @Override
    public void onDown() {
	if (characterScreen == null) {
	    characterScreen = screenManager.getScreen(CharacterScreen.class);
	    if (characterScreen == null) {
		return;
	    }
	}

	if (characterScreen.isActive()) {
	    screenManager.hideScreen(CharacterScreen.class);
	} else {
	    screenManager.showScreen(CharacterScreen.class);
	}
    }

    @Override
    public void onUp() {
    }
}