package com.omega.amazehing.game.ai.steering;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.movement.AngularVelocityComponent;
import com.omega.amazehing.game.entity.component.movement.VelocityComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.entity.component.transform.RotationComponent;
import com.omega.amazehing.util.SteeringUtils;

public class EntitySteeringAgent extends AbstractSteeringAgent {

    private Entity entity;

    private static final ComponentMapper<PositionComponent> positionMapper = ComponentMapperHandler
	    .getPositionMapper();
    private static final ComponentMapper<VelocityComponent> velocityMapper = ComponentMapperHandler
	    .getVelocityMapper();
    private static final ComponentMapper<AngularVelocityComponent> angularVelocityMapper = ComponentMapperHandler
	    .getAngularVelocityMapper();
    private static final ComponentMapper<RotationComponent> rotationMapper = ComponentMapperHandler
	    .getRotationMapper();

    public EntitySteeringAgent(Entity entity) {
	this.entity = entity;
    }

    @Override
    public Vector2 getLinearVelocity() {
	VelocityComponent _veloComp = velocityMapper.get(entity);
	if (_veloComp == null) {
	    throw new NullPointerException("The entity don't have a velocity component.");
	}

	return _veloComp.getVelocity();
    }

    @Override
    public float getAngularVelocity() {
	AngularVelocityComponent _angVeloComp = angularVelocityMapper.get(entity);
	if (_angVeloComp == null) {
	    throw new NullPointerException("The entity don't have an angular velocity component.");
	}

	return _angVeloComp.getAngularVelocity();
    }

    @Override
    public Vector2 getPosition() {
	return positionMapper.get(entity).getPosition();
    }

    @Override
    public float getOrientation() {
	RotationComponent _rotComp = rotationMapper.get(entity);
	if (_rotComp == null) {
	    throw new NullPointerException("The entity don't have a rotation component.");
	}

	return _rotComp.toRadians();
    }

    @Override
    public void setOrientation(float orientation) {
	RotationComponent _rotComp = rotationMapper.get(entity);
	if (_rotComp == null) {
	    throw new NullPointerException("The entity don't have a rotation component.");
	}

	_rotComp.fromRadians(orientation);
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
	return SteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
	return SteeringUtils.angleToVector(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
	return new SteeringLocation();
    }
}