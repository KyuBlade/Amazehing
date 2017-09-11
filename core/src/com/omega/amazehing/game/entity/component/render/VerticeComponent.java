package com.omega.amazehing.game.entity.component.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class VerticeComponent implements Component, Poolable {

    private float[] vertices;

    public VerticeComponent() {
    }

    public float[] getVertices() {
	return vertices;
    }

    public VerticeComponent setVertices(float[] vertices) {
	this.vertices = vertices;

	return this;
    }

    @Override
    public void reset() {
	vertices = null;
    }
}