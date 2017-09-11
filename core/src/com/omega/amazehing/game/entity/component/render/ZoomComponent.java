package com.omega.amazehing.game.entity.component.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ZoomComponent implements Component, Poolable {

    private float zoom = 1f;
    private float minZoom = 1f;
    private float maxZoom = 1f;

    public ZoomComponent() {
    }

    public float getZoom() {
	return zoom;
    }

    public ZoomComponent setZoom(float zoom) {
	this.zoom = zoom;

	return this;
    }

    public float getMinZoom() {
	return minZoom;
    }

    public ZoomComponent setMinZoom(float minZoom) {
	this.minZoom = minZoom;

	return this;
    }

    public float getMaxZoom() {
	return maxZoom;
    }

    public ZoomComponent setMaxZoom(float maxZoom) {
	this.maxZoom = maxZoom;

	return this;
    }

    @Override
    public void reset() {
	zoom = 1f;
	minZoom = 1f;
	maxZoom = 1f;
    }
}