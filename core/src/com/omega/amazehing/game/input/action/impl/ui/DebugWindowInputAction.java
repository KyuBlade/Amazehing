package com.omega.amazehing.game.input.action.impl.ui;

import com.omega.amazehing.game.input.action.InputAction;
import com.omega.amazehing.ui.DebugWindow;

public class DebugWindowInputAction implements InputAction {

    private DebugWindow debugWindow;

    public DebugWindowInputAction(DebugWindow debugWindow) {
	this.debugWindow = debugWindow;
    }

    @Override
    public void onDown() {
	if (debugWindow.isVisible()) {
	    debugWindow.setVisible(false);
	} else {
	    debugWindow.setVisible(true);
	}
    }

    @Override
    public void onUp() {
    }
}