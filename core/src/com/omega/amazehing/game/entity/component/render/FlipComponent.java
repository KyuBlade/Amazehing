package com.omega.amazehing.game.entity.component.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class FlipComponent implements Component, Poolable {

    private boolean flipX;
    private boolean flipY;

    public FlipComponent() {
    }

    public boolean isFlipX() {
	return flipX;
    }

    public FlipComponent setFlipX(boolean flipX) {
	this.flipX = flipX;

	return this;
    }

    public boolean isFlipY() {
	return flipY;
    }

    public FlipComponent setFlipY(boolean flipY) {
	this.flipY = flipY;

	return this;
    }

    @Override
    public void reset() {
	flipX = false;
	flipY = false;
    }
}