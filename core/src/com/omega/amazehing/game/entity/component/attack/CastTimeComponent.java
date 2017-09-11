package com.omega.amazehing.game.entity.component.attack;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CastTimeComponent implements Component, Poolable {

    private float castTime;

    public CastTimeComponent() {
    }

    public float getCastTime() {
	return castTime;
    }

    public CastTimeComponent setCastTime(float castTime) {
	this.castTime = castTime;

	return this;
    }

    @Override
    public void reset() {
	castTime = 0;
    }
}