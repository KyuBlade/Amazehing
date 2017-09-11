package com.omega.amazehing.game.entity.component.ai.path;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;

public class SourceComponent implements Component, Poolable {

    private Entity source;

    public SourceComponent() {
    }

    public Entity getSource() {
	return source;
    }

    public SourceComponent setSource(Entity source) {
	this.source = source;

	return this;
    }

    @Override
    public void reset() {
	source = null;
    }
}