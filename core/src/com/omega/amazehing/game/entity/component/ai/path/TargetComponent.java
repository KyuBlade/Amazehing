package com.omega.amazehing.game.entity.component.ai.path;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TargetComponent implements Component, Poolable {

    private Entity target;

    public TargetComponent() {
    }

    public Entity getTarget() {
	return target;
    }

    public TargetComponent setTarget(Entity target) {
	this.target = target;

	return this;
    }

    @Override
    public void reset() {
	target = null;
    }
}