package com.omega.amazehing.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class NameComponent implements Component, Poolable {

    private String name;

    public NameComponent() {
    }

    public String getName() {
	return name;
    }

    public NameComponent setName(String name) {
	this.name = name;

	return this;
    }

    @Override
    public void reset() {
	name = null;
    }
}