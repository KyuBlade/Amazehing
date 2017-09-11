package com.omega.amazehing.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class IdentityComponent implements Component, Poolable {

    private long identity;

    public IdentityComponent() {
    }

    public long getIdentity() {
	return identity;
    }

    public IdentityComponent setIdentity(long identity) {
	this.identity = identity;

	return this;
    }

    @Override
    public void reset() {
	identity = 0L;
    }

}
