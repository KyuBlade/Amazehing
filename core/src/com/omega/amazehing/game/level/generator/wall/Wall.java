package com.omega.amazehing.game.level.generator.wall;

import com.badlogic.gdx.math.Rectangle;

@SuppressWarnings("serial")
public class Wall extends Rectangle {

    private float rotation;

    public Wall() {
    }

    public Wall(float x, float y, float w, float h, float rotation) {
	super(x, y, w, h);

	this.rotation = rotation;
    }

    public float getRotation() {
	return rotation;
    }
}