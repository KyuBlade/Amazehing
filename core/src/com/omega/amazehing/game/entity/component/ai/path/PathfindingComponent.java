package com.omega.amazehing.game.entity.component.ai.path;

import java.util.concurrent.Future;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.omega.amazehing.game.ai.pathfinding.ConnectionGraphPath;

public class PathfindingComponent implements Component, Poolable {

    private Future<ConnectionGraphPath> future;

    public PathfindingComponent() {
    }

    public Future<ConnectionGraphPath> getFuture() {
	return future;
    }

    public PathfindingComponent setFuture(Future<ConnectionGraphPath> future) {
	this.future = future;

	return this;
    }

    @Override
    public void reset() {
	future = null;
    }
}