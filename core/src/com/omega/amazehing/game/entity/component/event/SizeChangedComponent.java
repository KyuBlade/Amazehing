package com.omega.amazehing.game.entity.component.event;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class SizeChangedComponent implements Component, Poolable {

    private boolean hasChanged;

    public SizeChangedComponent() {
    }

    public boolean hasChanged() {
	return hasChanged;
    }

    public SizeChangedComponent setHasChanged(boolean hasChanged) {
	this.hasChanged = hasChanged;

	return this;
    }

    @Override
    public void reset() {
	hasChanged = false;
    }
}