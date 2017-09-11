package com.omega.amazehing.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

public class EffectListComponent implements Component, Poolable {

    private Array<Entity> effects;

    public EffectListComponent() {
    }

    public Array<Entity> getEffects() {
	return effects;
    }

    public EffectListComponent add(Entity effect) {
	if (effects == null) {
	    effects = new Array<Entity>();
	}

	effects.add(effect);

	return this;
    }

    public EffectListComponent addAll(Entity[] effects) {
	if (this.effects == null) {
	    this.effects = new Array<Entity>();
	}

	this.effects.addAll(effects);

	return this;
    }

    @Override
    public void reset() {
	effects = null;
    }
}