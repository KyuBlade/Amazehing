package com.omega.amazehing.game.entity.component.paging;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class PagedComponent implements Component, Poolable {

    private boolean dynamic;

    public PagedComponent() {
    }

    public boolean isDynamic() {
	return dynamic;
    }

    public PagedComponent setDynamic(boolean dynamic) {
	this.dynamic = dynamic;

	return this;
    }

    @Override
    public void reset() {
	dynamic = false;
    }
}