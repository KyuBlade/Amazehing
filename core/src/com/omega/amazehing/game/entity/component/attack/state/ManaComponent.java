package com.omega.amazehing.game.entity.component.attack.state;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ManaComponent implements Component, Poolable {

    private int max;
    private int current;
    private int regen;

    public ManaComponent() {
    }

    public int getMax() {
	return max;
    }

    public ManaComponent setMax(int max) {
	this.max = max;

	return this;
    }

    public int getCurrent() {
	return current;
    }

    public ManaComponent setCurrent(int current) {
	this.current = current;

	return this;
    }

    public void add(int amount) {
	current += amount;
    }

    public void remove(int amount) {
	current -= amount;
    }

    public int getRegen() {
	return regen;
    }

    public ManaComponent setRegen(int regen) {
	this.regen = regen;

	return this;
    }

    @Override
    public void reset() {
	max = 0;
	current = 0;
	regen = 0;
    }
}