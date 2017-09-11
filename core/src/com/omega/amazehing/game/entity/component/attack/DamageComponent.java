package com.omega.amazehing.game.entity.component.attack;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class DamageComponent implements Component, Poolable {

    private int minDamage;
    private int maxDamage;

    public DamageComponent() {
    }

    public int getMinDamage() {
	return minDamage;
    }

    public DamageComponent setMinDamage(int minDamage) {
	this.minDamage = minDamage;

	return this;
    }

    public int getMaxDamage() {
	return maxDamage;
    }

    public DamageComponent setMaxDamage(int maxDamage) {
	this.maxDamage = maxDamage;

	return this;
    }

    @Override
    public void reset() {
	minDamage = 0;
	maxDamage = 0;
    }
}