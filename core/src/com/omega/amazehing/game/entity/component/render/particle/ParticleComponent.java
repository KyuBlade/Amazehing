package com.omega.amazehing.game.entity.component.render.particle;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ParticleComponent implements Component, Poolable {

    private PooledEffect effect;

    public ParticleComponent() {
    }

    public PooledEffect getEffect() {
	return effect;
    }

    public ParticleComponent setEffect(PooledEffect effect) {
	this.effect = effect;

	return this;
    }

    @Override
    public void reset() {
	effect = null;
    }
}