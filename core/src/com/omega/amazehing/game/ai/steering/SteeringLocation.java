package com.omega.amazehing.game.ai.steering;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.omega.amazehing.util.SteeringUtils;

public class SteeringLocation implements Location<Vector2> {

    private Vector2 position;
    private float orientation;

    public SteeringLocation() {
    }

    @Override
    public Vector2 getPosition() {
	return position;
    }

    @Override
    public float getOrientation() {
	return orientation;
    }

    @Override
    public void setOrientation(float orientation) {
	this.orientation = orientation;
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