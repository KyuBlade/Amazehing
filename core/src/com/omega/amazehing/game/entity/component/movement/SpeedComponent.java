package com.omega.amazehing.game.entity.component.movement;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class SpeedComponent implements Component, Poolable {

    private float speed;

    public SpeedComponent() {
    }

    public SpeedComponent setSpeed(float speed) {
	this.speed = speed;

	return this;
    }

    public float getSpeed() {
	return speed;
    }

    @Override
    public void reset() {
	speed = 0f;
    }
}