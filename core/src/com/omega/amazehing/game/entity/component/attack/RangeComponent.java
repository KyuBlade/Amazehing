package com.omega.amazehing.game.entity.component.attack;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class RangeComponent implements Component, Poolable {

    private float range;

    public RangeComponent() {
    }

    public float getRange() {
	return range;
    }

    public RangeComponent setRange(float range) {
	this.range = range;

	return this;
    }

    @Override
    public void reset() {
	range = 0f;
    }
}