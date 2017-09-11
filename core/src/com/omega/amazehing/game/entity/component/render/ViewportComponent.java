package com.omega.amazehing.game.entity.component.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ViewportComponent implements Component, Poolable {

    private Viewport viewport;

    public ViewportComponent() {
    }

    public Viewport getViewport() {
	return viewport;
    }

    public ViewportComponent setViewport(Viewport viewport) {
	this.viewport = viewport;

	return this;
    }

    @Override
    public void reset() {
	viewport = null;
    }
}