package com.omega.amazehing.game.entity.component.ai.path;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class LastPathfindingComponent implements Component, Poolable {

    public long last;

    public LastPathfindingComponent() {
    }

    public long getLast() {
	return last;
    }

    public LastPathfindingComponent setLast(long last) {
	this.last = last;

	return this;
    }

    @Override
    public void reset() {
	last = 0L;
    }
}