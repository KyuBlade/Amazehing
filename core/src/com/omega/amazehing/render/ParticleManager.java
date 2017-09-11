package com.omega.amazehing.render;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Disposable;

public class ParticleManager implements Disposable {

    private Map<String, ParticleEffectPool> pools;

    private static ParticleManager instance;

    public ParticleManager() {
	pools = new HashMap<String, ParticleEffectPool>();
    }

    public static ParticleManager getInstance() {
	if (instance == null) {
	    instance = new ParticleManager();
	}

	return instance;
    }

    public void addParticleEffect(String name, ParticleEffect effect, int init, int max) {
	pools.put(name, new ParticleEffectPool(effect, init, max));
    }

    public PooledEffect getParticle(String particleName) {
	ParticleEffectPool _pool = pools.get(particleName);
	PooledEffect _pooledEffect = _pool.obtain();

	return _pooledEffect;
    }

    @Override
    public void dispose() {
	instance = null;
    }
}