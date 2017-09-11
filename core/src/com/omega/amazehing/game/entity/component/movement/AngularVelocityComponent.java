package com.omega.amazehing.game.entity.component.movement;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool.Poolable;

public class AngularVelocityComponent implements Component, Poolable {

    /**
     * Angular velocity in degrees
     */
    private float angularVelocity;

    public AngularVelocityComponent() {
    }

    /**
     * @return the angular velocity in degrees
     */
    public float getAngularVelocity() {
	return angularVelocity;
    }

    /**
     * Set the angular velocity from degrees.
     * 
     * @param angularVelocity the angular velocity in degrees
     * @return self component for chaining
     */
    public AngularVelocityComponent setAngularVelocity(float degrees) {
	angularVelocity = degrees;

	return this;
    }

    /**
     * Set the angular velocity from radians.
     * 
     * @param radians the angular velocity in radians
     * @return self component for chaining
     */
    public AngularVelocityComponent fromRadians(float radians) {
	angularVelocity = radians * MathUtils.radiansToDegrees;

	return this;
    }

    /**
     * @return the angular velocity in radians
     */
    public float toRadians() {
	return angularVelocity * MathUtils.degreesToRadians;
    }

    @Override
    public void reset() {
	angularVelocity = 0f;
    }
}