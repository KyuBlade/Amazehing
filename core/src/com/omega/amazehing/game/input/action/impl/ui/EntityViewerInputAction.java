package com.omega.amazehing.game.input.action.impl.ui;

import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.game.input.action.InputAction;
import com.omega.amazehing.screen.game.EntityViewerScreen;

public class EntityViewerInputAction implements InputAction {

    private ScreenManager screenManager;
    private EntityViewerScreen entityViewerScreen;

    public EntityViewerInputAction(ScreenManager screenManager) {
	this.screenManager = screenManager;
	entityViewerScreen = screenManager.getScreen(EntityViewerScreen.class);
    }

    @Override
    public void onDown() {
	if (entityViewerScreen.isActive()) {
	    screenManager.hideScreen(EntityViewerScreen.class);
	} else {
	    screenManager.showScreen(EntityViewerScreen.class);
	}
    }

    @Override
    public void onUp() {
    }
}