package com.omega.amazehing.game.entity.component.ai.path;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.omega.amazehing.game.ai.pathfinding.ConnectionGraphPath;

public class PathComponent implements Component, Poolable {

    private ConnectionGraphPath path;

    public PathComponent() {
    }

    public ConnectionGraphPath getPath() {
	return path;
    }

    public PathComponent setPath(ConnectionGraphPath path) {
	this.path = path;

	return this;
    }

    @Override
    public void reset() {
	path = null;
    }
}