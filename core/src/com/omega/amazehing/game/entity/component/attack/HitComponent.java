package com.omega.amazehing.game.entity.component.attack;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;

public class HitComponent implements Component, Poolable {

    private Entity target;

    public HitComponent(Entity target) {
	this.target = target;
    }

    public Entity getTarget() {
	return target;
    }

    @Override
    public void reset() {
	target = null;
    }
}