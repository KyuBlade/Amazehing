package com.omega.amazehing.game.ai.steering.behavior;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.Path;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath.LinePathParam;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.game.ai.steering.debug.Debuggable;
import com.omega.amazehing.game.ai.steering.debug.FollowPathSteeringBehaviorDebug;
import com.omega.amazehing.game.entity.EntityEngine;

public class FollowPathSteeringBehavior extends FollowPath<Vector2, LinePathParam> implements
	Debuggable {

    private FollowPathSteeringBehaviorDebug debug;

    public FollowPathSteeringBehavior(EntityEngine engine, FactoryManager factoryManager,
	    Steerable<Vector2> owner, Array<Vector2> waypoints) {
	super(owner, new LinePath<Vector2>(waypoints, true));

	debug = new FollowPathSteeringBehaviorDebug(engine, factoryManager, this);
    }

    @Override
    public FollowPath<Vector2, LinePathParam> setPath(Path<Vector2, LinePathParam> path) {
	super.setPath(path);

	debug.setPath((LinePath<Vector2>) path);

	return this;
    }

    @Override
    public void updateDebug() {
	debug.update();
    }

    @Override
    public void onRemoved() {
	debug.onRemoved();
    }
}