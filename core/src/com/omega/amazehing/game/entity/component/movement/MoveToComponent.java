package com.omega.amazehing.game.entity.component.movement;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class MoveToComponent implements Component, Poolable {

    private Vector2 position = new Vector2();

    public MoveToComponent() {
    }

    public Vector2 getPosition() {
	return position;
    }

    public MoveToComponent setPosition(Vector2 position) {
	this.position.set(position);

	return this;
    }

    @Override
    public void reset() {
	position.setZero();
    }
}