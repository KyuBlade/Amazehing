package com.omega.amazehing.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ViewerNameComponent implements Component, Poolable {

    private String name;

    public ViewerNameComponent() {
    }

    public String getName() {
	return name;
    }

    public ViewerNameComponent setName(String name) {
	this.name = name;

	return this;
    }

    @Override
    public void reset() {
	name = null;
    }
}