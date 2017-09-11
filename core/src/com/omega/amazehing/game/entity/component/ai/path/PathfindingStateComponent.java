package com.omega.amazehing.game.entity.component.ai.path;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class PathfindingStateComponent implements Component, Poolable {

    private boolean isDone;

    public PathfindingStateComponent() {
    }

    public boolean isDone() {
	return isDone;
    }

    public PathfindingStateComponent setDone(boolean isDone) {
	this.isDone = isDone;

	return this;
    }

    public void reset() {
	isDone = false;
    }
}