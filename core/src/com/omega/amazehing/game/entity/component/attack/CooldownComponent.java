package com.omega.amazehing.game.entity.component.attack;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CooldownComponent implements Component, Poolable {

    private float delay;
    private float accumulator;

    public CooldownComponent() {
    }

    public float getDelay() {
	return delay;
    }

    public CooldownComponent setDelay(float delay) {
	this.delay = delay;

	return this;
    }

    public float getAccumulator() {
	return accumulator;
    }

    public CooldownComponent addAccumulator(float accumulator) {
	this.accumulator += accumulator;

	return this;
    }

    public void resetAccumulator() {
	accumulator = 0f;
    }

    @Override
    public void reset() {
	delay = 0f;
	accumulator = 0f;
    }
}