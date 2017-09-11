package com.omega.amazehing.game.entity.component.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ColorComponent implements Component, Poolable {

    private Color color;

    public ColorComponent() {
    }

    public Color getColor() {
	return color;
    }

    public ColorComponent setColor(Color color) {
	this.color = color;

	return this;
    }

    @Override
    public void reset() {
	color = null;
    }
}