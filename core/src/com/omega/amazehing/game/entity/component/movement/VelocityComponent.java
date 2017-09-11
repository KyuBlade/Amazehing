package com.omega.amazehing.game.entity.component.movement;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class VelocityComponent implements Component, Poolable {

    private Vector2 velocity = new Vector2();

    public VelocityComponent() {
    }

    public VelocityComponent setX(float x) {
	velocity.x = x;

	return this;
    }

    public VelocityComponent setY(float y) {
	velocity.y = y;

	return this;
    }

    public VelocityComponent setVelocity(Vector2 velocity) {
	this.velocity.set(velocity);

	return this;
    }

    public Vector2 getVelocity() {
	return velocity;
    }

    @Override
    public void reset() {
	velocity.setZero();
    }
}