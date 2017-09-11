package com.omega.amazehing.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class DelayComponent implements Component, Poolable {

    private float delay;
    private float accumulator;
    private boolean isContinuous;
    private Runnable runnable;

    public DelayComponent() {
    }

    public float getDelay() {
	return delay;
    }

    public DelayComponent setDelay(float delay) {
	this.delay = delay;

	return this;
    }

    public float getAccumulator() {
	return accumulator;
    }

    public void addAccumulator(float delta) {
	accumulator += delta;
    }

    public void setAccumulator(float accumulator) {
	this.accumulator = accumulator;
    }

    public boolean isContinuous() {
	return isContinuous;
    }

    public DelayComponent setContinuous(boolean isContinuous) {
	this.isContinuous = isContinuous;

	return this;
    }

    public Runnable getRunnable() {
	return runnable;
    }

    public DelayComponent setRunnable(Runnable runnable) {
	this.runnable = runnable;

	return this;
    }

    @Override
    public void reset() {
	delay = 0f;
	accumulator = 0f;
	isContinuous = false;
	runnable = null;
    }
}