package com.omega.amazehing.game.entity.component.skill;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class PassiveComponent implements Component, Poolable {

    private boolean isPassive;

    public PassiveComponent() {
    }

    public boolean isPassive() {
	return isPassive;
    }

    public PassiveComponent setPassive(boolean isPassive) {
	this.isPassive = isPassive;

	return this;
    }

    public void reset() {
	isPassive = false;
    }
}