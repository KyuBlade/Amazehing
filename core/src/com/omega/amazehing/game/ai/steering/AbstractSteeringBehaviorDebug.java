package com.omega.amazehing.game.ai.steering;

import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.entity.RenderFactory;
import com.omega.amazehing.game.entity.EntityEngine;

public abstract class AbstractSteeringBehaviorDebug<T extends SteeringBehavior<Vector2>> {

    protected EntityEngine engine;
    protected RenderFactory renderFactory;
    protected T steeringBehavior;

    public AbstractSteeringBehaviorDebug(EntityEngine engine, FactoryManager factoryManager,
	    T steeringBehavior) {
	this.engine = engine;
	if (factoryManager != null) {
	    this.renderFactory = factoryManager.getFactory(RenderFactory.class);
	}
	this.steeringBehavior = steeringBehavior;
    }

    public abstract void update();

    public abstract void onRemoved();
}