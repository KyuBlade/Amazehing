package com.omega.amazehing.game.entity.component.transform;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool.Poolable;

public class RotationComponent implements Component, Poolable {

    private float rotation;

    public RotationComponent() {
    }

    /**
     * @return the rotation in degrees
     */
    public float getRotation() {
	return rotation;
    }

    /**
     * Set the rotation in degrees.
     * 
     * @param rotation rotation is degrees
     * @return self component for chaining
     */
    public RotationComponent setRotation(float degrees) {
	rotation = degrees;

	return this;
    }

    /**
     * Add degrees to the rotation.
     * 
     * @param degrees rotation to add in degrees
     * @return self component for chaining
     */
    public RotationComponent add(float degrees) {
	rotation += degrees;

	return this;
    }

    /**
     * Set the rotation from radians.
     * 
     * @param radians rotation in radians
     * @return
     */
    public RotationComponent fromRadians(float radians) {
	rotation = radians * MathUtils.radiansToDegrees;

	return this;
    }

    /**
     * @return the rotation in radians
     */
    public float toRadians() {
	return rotation * MathUtils.degreesToRadians;
    }

    @Override
    public void reset() {
	rotation = 0f;
    }
}