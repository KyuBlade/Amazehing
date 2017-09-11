package com.omega.amazehing.factory.entity;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.omega.amazehing.factory.Factory;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.game.ai.steering.AbstractSteeringAgent;
import com.omega.amazehing.game.ai.steering.behavior.FollowPathSteeringBehavior;
import com.omega.amazehing.game.ai.steering.behavior.PrioritySteeringBehavior;
import com.omega.amazehing.game.ai.steering.behavior.WanderSteeringBehavior;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.ai.steering.SteeringComponent;

public class SteeringFactory implements Factory {

    private EntityEngine engine;
    private FactoryManager factoryManager;

    private static final ComponentMapper<SteeringComponent> steeringMapper = ComponentMapperHandler
	    .getSteeringMapper();

    public SteeringFactory(EntityEngine engine, FactoryManager factoryManager) {
	this.engine = engine;
	this.factoryManager = factoryManager;
    }

    public Wander<Vector2> createWanderSteering(Entity owner) {
	AbstractSteeringAgent _agent = getAgent(owner);
	WanderSteeringBehavior _wanderBehavior = new WanderSteeringBehavior(engine, factoryManager,
		_agent);
	_wanderBehavior.setFaceEnabled(true).setAlignTolerance(1f).setDecelerationRadius(5)
		.setTimeToTarget(1f).setWanderOffset(1f).setWanderOrientation(0f)
		.setWanderRadius(2f).setWanderRate(0.1f);

	return _wanderBehavior;
    }

    public PrioritySteeringBehavior createPrioritySteering(Entity owner) {
	AbstractSteeringAgent _agent = getAgent(owner);
	PrioritySteeringBehavior _prioritySteering = new PrioritySteeringBehavior(_agent);

	return _prioritySteering;
    }

    public FollowPathSteeringBehavior createFollowPathSteering(Entity owner,
	    Array<Vector2> waypoints) {
	if (waypoints == null || waypoints.size < 2) {
	    return null;
	}

	AbstractSteeringAgent _agent = getAgent(owner);
	FollowPathSteeringBehavior _followPath = new FollowPathSteeringBehavior(engine,
		factoryManager, _agent, waypoints);
	_followPath.setPathOffset(1f);

	return _followPath;
    }

    private AbstractSteeringAgent getAgent(Entity owner) {
	SteeringComponent _steeringComp = steeringMapper.get(owner);
	if (_steeringComp == null) {
	    throw new NullPointerException("The owner don't have a steering component.");
	}

	AbstractSteeringAgent _agent = _steeringComp.getSteeringAgent();
	if (_agent == null) {
	    throw new NullPointerException("The owner don't have a sterring agent.");
	}

	return _agent;
    }
}