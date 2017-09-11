package com.omega.amazehing.game.ai.steering;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;
import com.omega.amazehing.game.ai.steering.debug.Debuggable;

public abstract class AbstractSteeringAgent implements Debuggable, Steerable<Vector2> {

    protected float boundingRadius;
    protected boolean tagged;

    protected float maxLinearSpeed;
    protected float maxLinearAcceleration;
    protected float maxAngularSpeed;
    protected float maxAngularAcceleration;

    protected boolean independentFacing;

    protected SteeringBehavior<Vector2> steeringBehavior;

    public AbstractSteeringAgent() {
    }

    @Override
    public float getBoundingRadius() {
	return boundingRadius;
    }

    @Override
    public boolean isTagged() {
	return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
	this.tagged = tagged;
    }

    public SteeringBehavior<Vector2> getSteeringBehavior() {
	return steeringBehavior;
    }

    public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior) {
	this.steeringBehavior = steeringBehavior;
    }

    @Override
    public float getMaxLinearSpeed() {
	return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
	this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
	return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
	this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
	return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
	this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
	return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
	this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
	return 0.001f;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {
	throw new UnsupportedOperationException();
    }

    public boolean isIndependentFacing() {
	return independentFacing;
    }

    public void setIndependentFacing(boolean independentFacing) {
	this.independentFacing = independentFacing;
    }

    @Override
    public void updateDebug() {
	((Debuggable) steeringBehavior).updateDebug();
    }

    @Override
    public void onRemoved() {
	((Debuggable) steeringBehavior).onRemoved();
    }
}