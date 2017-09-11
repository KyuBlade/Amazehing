package com.omega.amazehing.game.entity.component.node;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ParentNodeComponent implements Component, Poolable {

    private Entity parent;

    public ParentNodeComponent() {
    }

    public Entity getParent() {
	return parent;
    }

    public ParentNodeComponent setParent(Entity parent) {
	this.parent = parent;

	return this;
    }

    @Override
    public void reset() {
	parent = null;
    }
}