package com.omega.amazehing.game.ai.steering;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.omega.amazehing.util.SteeringUtils;

public class PhysicsSteeringAgent extends AbstractSteeringAgent {

    private Body body;

    public PhysicsSteeringAgent(Body body) {
	this.body = body;
    }

    @Override
    public Vector2 getPosition() {
	return body.getPosition();
    }

    @Override
    public float getOrientation() {
	return body.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {
	body.setTransform(getPosition(), orientation);
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

    @Override
    public Vector2 getLinearVelocity() {
	return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
	return body.getAngularVelocity();
    }
}