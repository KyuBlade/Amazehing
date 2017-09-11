package com.omega.amazehing.game.entity.system.ai;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;
import com.omega.amazehing.Constants;
import com.omega.amazehing.game.ai.steering.AbstractSteeringAgent;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.DebugComponent.DebugType;
import com.omega.amazehing.game.entity.component.ai.steering.SteeringComponent;
import com.omega.amazehing.game.entity.component.movement.AngularVelocityComponent;
import com.omega.amazehing.game.entity.component.movement.VelocityComponent;
import com.omega.amazehing.util.DebugManager;

public class SteeringSystem extends IteratingSystem {

    private EntityEngine engine;
    private SteeringAcceleration<Vector2> steeringAcceleration;
    private DebugManager debugManager;

    private static final ComponentMapper<SteeringComponent> steeringMapper = ComponentMapperHandler
	    .getSteeringMapper();

    @SuppressWarnings("unchecked")
    public SteeringSystem() {
	super(Family.all(SteeringComponent.class).get(),
		Constants.Game.System.STEERING_SYSTEM_PRIORITY);

	steeringAcceleration = new SteeringAcceleration<Vector2>(new Vector2());
	debugManager = DebugManager.getInstance();
    }

    @Override
    public void addedToEngine(Engine engine) {
	super.addedToEngine(engine);

	this.engine = (EntityEngine) engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
	SteeringComponent _steeringComp = steeringMapper.get(entity);
	AbstractSteeringAgent _steeringAgent = _steeringComp.getSteeringAgent();
	SteeringBehavior<Vector2> _steeringBehavior = _steeringAgent.getSteeringBehavior();
	if (_steeringBehavior == null) {
	    return;
	}
	_steeringBehavior.calculateSteering(steeringAcceleration);

	Vector2 _linearVelocity = steeringAcceleration.linear;
	float _angularVelocity = steeringAcceleration.angular;
	float _threshold = _steeringAgent.getZeroLinearSpeedThreshold();

	if (!_linearVelocity.isZero(_threshold)) {
	    entity.add(engine.createComponent(VelocityComponent.class).setVelocity(_linearVelocity));
	} else {
	    entity.add(engine.createComponent(VelocityComponent.class).setVelocity(Vector2.Zero));
	}

	if (_steeringAgent.isIndependentFacing()) {
	    if (steeringAcceleration.angular != 0f) {
		entity.add(engine.createComponent(AngularVelocityComponent.class)
			.setAngularVelocity(steeringAcceleration.angular));
	    } else {
		entity.add(engine.createComponent(AngularVelocityComponent.class)
			.setAngularVelocity(0f));
	    }
	} else {
	    Vector2 _velocity = _steeringAgent.getLinearVelocity();
	    if (!_velocity.isZero(_steeringAgent.getZeroLinearSpeedThreshold())) {
		float _newOrientation = _steeringAgent.vectorToAngle(_velocity);
		entity.add(engine.createComponent(AngularVelocityComponent.class)
			.setAngularVelocity(_newOrientation - _steeringAgent.getAngularVelocity()));
	    }
	}

	if (debugManager.get(DebugType.AI)) {
	    _steeringAgent.updateDebug();
	}
    }
}