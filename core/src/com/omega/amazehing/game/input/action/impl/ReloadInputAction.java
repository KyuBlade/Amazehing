package com.omega.amazehing.game.input.action.impl;

import com.omega.amazehing.game.input.action.InputAction;
import com.omega.amazehing.screen.game.GameScreen;

public class ReloadInputAction implements InputAction {

    private GameScreen gameScreen;

    public ReloadInputAction(GameScreen gameScreen) {
	this.gameScreen = gameScreen;
    }

    @Override
    public void onDown() {
    }

    @Override
    public void onUp() {
	gameScreen.reload();
    }
}