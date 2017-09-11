package com.omega.amazehing.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class SavableComponent implements Component, Poolable {

    public static enum SaveType {
	STATIC, DYNAMIC;
    }

    private SaveType type;

    public SavableComponent() {
    }

    public SaveType getType() {
	return type;
    }

    public SavableComponent setType(SaveType type) {
	this.type = type;

	return this;
    }

    @Override
    public void reset() {
	type = null;
    }
}