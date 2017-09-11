package com.omega.amazehing.game.entity.component.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ShapeRenderComponent implements Component, Poolable {

    public enum Shape {
	RECTANGLE, CIRCLE, LINE;
    }

    private Shape shape;
    private boolean isFilled;

    public ShapeRenderComponent() {
    }

    public Shape getShape() {
	return shape;
    }

    public ShapeRenderComponent setShape(Shape shape) {
	this.shape = shape;

	return this;
    }

    public boolean isFilled() {
	return isFilled;
    }

    public ShapeRenderComponent setFilled(boolean isFilled) {
	this.isFilled = isFilled;

	return this;
    }

    @Override
    public void reset() {
	shape = null;
	isFilled = false;
    }
}