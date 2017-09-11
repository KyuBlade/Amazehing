package com.omega.amazehing.game.entity.component.transform;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class PositionComponent implements Component, Poolable {

    private Vector2 position;

    public PositionComponent() {
	position = new Vector2();
    }

    public PositionComponent setPosition(Vector2 position) {
	this.position.set(position);

	return this;
    }

    public PositionComponent setX(float x) {
	position.x = x;

	return this;
    }

    public PositionComponent setY(float y) {
	position.y = y;

	return this;
    }

    public Vector2 getPosition() {
	return position;
    }

    @Override
    public void reset() {
	position.setZero();
    }
}