package com.omega.amazehing.handler;

import com.badlogic.ashley.core.Entity;

public class PlayerHandler {

    private Entity player;

    public PlayerHandler() {
    }

    public Entity getPlayer() {
	return player;
    }

    public void setPlayer(Entity player) {
	this.player = player;
    }
}