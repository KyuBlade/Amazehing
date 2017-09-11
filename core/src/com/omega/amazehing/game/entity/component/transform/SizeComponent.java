package com.omega.amazehing.game.entity.component.transform;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class SizeComponent implements Component, Poolable {

    private float width;
    private float height;

    public SizeComponent() {
    }

    public float getWidth() {
	return width;
    }

    public SizeComponent setWidth(float width) {
	this.width = width;

	return this;
    }

    public float getHeight() {
	return height;
    }

    public SizeComponent setHeight(float height) {
	this.height = height;

	return this;
    }

    @Override
    public void reset() {
	width = 0f;
	height = 0f;
    }
}