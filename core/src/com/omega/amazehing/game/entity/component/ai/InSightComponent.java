package com.omega.amazehing.game.entity.component.ai;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class InSightComponent implements Component, Poolable {

    private boolean inSight;

    public InSightComponent() {
    }

    public boolean isInSight() {
	return inSight;
    }

    public InSightComponent setInSight(boolean inSight) {
	this.inSight = inSight;

	return this;
    }

    @Override
    public void reset() {
	inSight = false;
    }
}