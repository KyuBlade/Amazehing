package com.omega.amazehing.game.entity.component.transform;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class RadiusComponent implements Component, Poolable {

    private float radius;

    public RadiusComponent() {
    }

    public float getRadius() {
	return radius;
    }

    public RadiusComponent setRadius(float radius) {
	this.radius = radius;

	return this;
    }

    @Override
    public void reset() {
	radius = 0f;
    }
}