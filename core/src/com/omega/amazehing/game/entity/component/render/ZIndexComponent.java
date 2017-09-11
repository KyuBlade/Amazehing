package com.omega.amazehing.game.entity.component.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ZIndexComponent implements Component, Poolable {

    private int zindex;

    public ZIndexComponent() {
    }

    public int getZindex() {
	return zindex;
    }

    public ZIndexComponent setZindex(int zindex) {
	this.zindex = zindex;

	return this;
    }

    @Override
    public void reset() {
	zindex = 0;
    }
}