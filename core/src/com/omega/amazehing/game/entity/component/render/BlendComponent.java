package com.omega.amazehing.game.entity.component.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class BlendComponent implements Component, Poolable {

    private boolean isBlending;

    public BlendComponent() {
    }

    public boolean isBlending() {
	return isBlending;
    }

    public BlendComponent setBlending(boolean isBlending) {
	this.isBlending = isBlending;

	return this;
    }

    @Override
    public void reset() {
	isBlending = false;
    }
}