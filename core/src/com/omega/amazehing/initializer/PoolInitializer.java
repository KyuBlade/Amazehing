package com.omega.amazehing.initializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class PoolInitializer {

    static {
	Pools.set(Vector2.class, new Pool<Vector2>(10, 50) {

	    @Override
	    protected Vector2 newObject() {
		return new Vector2();
	    }

	    @Override
	    public void free(Vector2 object) {
		super.free(object);

		object.setZero();
	    }
	});
    }
}