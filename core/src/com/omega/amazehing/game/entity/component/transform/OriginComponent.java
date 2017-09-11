package com.omega.amazehing.game.entity.component.transform;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class OriginComponent implements Component, Poolable {

    private Vector2 origin = new Vector2();

    public OriginComponent() {
    }

    public Vector2 getOrigin() {
	return origin;
    }

    public OriginComponent setOrigin(Vector2 origin) {
	this.origin.set(origin);

	return this;
    }

    public OriginComponent setX(float x) {
	origin.x = x;

	return this;
    }

    public OriginComponent setY(float y) {
	origin.y = y;

	return this;
    }

    @Override
    public void reset() {
	origin.setZero();
    }
}