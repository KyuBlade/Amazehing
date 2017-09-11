package com.omega.amazehing.game.entity.component.ai.steering;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.omega.amazehing.game.ai.steering.AbstractSteeringAgent;

public class SteeringComponent implements Component, Poolable {

    private AbstractSteeringAgent steeringAgent;

    public SteeringComponent() {
    }

    public AbstractSteeringAgent getSteeringAgent() {
	return steeringAgent;
    }

    public SteeringComponent setSteeringAgent(AbstractSteeringAgent steeringAgent) {
	this.steeringAgent = steeringAgent;

	return this;
    }

    @Override
    public void reset() {
	steeringAgent = null;
    }
}