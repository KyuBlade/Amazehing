package com.omega.amazehing.game.entity.component.trigger;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TriggerActivationComponent implements Component, Poolable {

    private Entity target;
    private boolean isActive;

    public TriggerActivationComponent() {
    }

    public TriggerActivationComponent setActive(boolean isActive) {
	this.isActive = isActive;

	return this;
    }

    public boolean isActive() {
	return isActive;
    }

    public TriggerActivationComponent setTarget(Entity target) {
	this.target = target;

	return this;
    }

    public Entity getTarget() {
	return target;
    }

    @Override
    public void reset() {
	target = null;
	isActive = false;
    }
}