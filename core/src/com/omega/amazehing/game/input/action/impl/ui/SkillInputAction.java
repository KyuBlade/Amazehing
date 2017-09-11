package com.omega.amazehing.game.input.action.impl.ui;

import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.game.input.action.InputAction;
import com.omega.amazehing.screen.game.SkillScreen;

public class SkillInputAction implements InputAction {

    private ScreenManager screenManager;
    private SkillScreen skillScreen;

    public SkillInputAction(ScreenManager screenManager) {
	this.screenManager = screenManager;
    }

    @Override
    public void onDown() {
	if (skillScreen == null) {
	    skillScreen = screenManager.getScreen(SkillScreen.class);
	    if (skillScreen == null) {
		return;
	    }
	}

	if (skillScreen.isActive()) {
	    screenManager.hideScreen(SkillScreen.class);
	} else {
	    screenManager.showScreen(SkillScreen.class);
	}
    }

    @Override
    public void onUp() {
    }
}