package com.omega.amazehing.game.input.action.impl.ui;

import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.game.input.action.InputAction;
import com.omega.amazehing.screen.game.InventoryScreen;

public class InventoryInputAction implements InputAction {

    private ScreenManager screenManager;
    private InventoryScreen inventoryScreen;

    public InventoryInputAction(ScreenManager screenManager) {
	this.screenManager = screenManager;
    }

    @Override
    public void onDown() {
	if (inventoryScreen == null) {
	    inventoryScreen = screenManager.getScreen(InventoryScreen.class);
	    if (inventoryScreen == null) {
		return;
	    }
	}

	if (inventoryScreen.isActive()) {
	    screenManager.hideScreen(InventoryScreen.class);
	} else {
	    screenManager.showScreen(InventoryScreen.class);
	}
    }

    @Override
    public void onUp() {
    }
}