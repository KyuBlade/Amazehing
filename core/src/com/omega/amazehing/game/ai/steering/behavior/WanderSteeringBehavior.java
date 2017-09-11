package com.omega.amazehing.game.ai.steering.behavior;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.Vector2;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.game.ai.steering.debug.Debuggable;
import com.omega.amazehing.game.ai.steering.debug.WanderSteeringBehaviorDebug;
import com.omega.amazehing.game.entity.EntityEngine;

public class WanderSteeringBehavior extends Wander<Vector2> implements Debuggable {

    private WanderSteeringBehaviorDebug debug;

    public WanderSteeringBehavior(EntityEngine engine, FactoryManager factoryManager,
	    Steerable<Vector2> owner) {
	super(owner);

	debug = new WanderSteeringBehaviorDebug(engine, factoryManager, this);
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