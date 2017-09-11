package com.omega.amazehing.game.entity.component.movement;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ImpulseComponent implements Component, Poolable {

    private Vector2 impulse;

    public ImpulseComponent() {
	impulse = new Vector2();
    }

    public Vector2 getImpulse() {
	return impulse;
    }

    public ImpulseComponent setImpulse(Vector2 impulse) {
	this.impulse.set(impulse);

	return this;
    }

    @Override
    public void reset() {
	impulse.setZero();
    }
}