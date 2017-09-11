package com.omega.amazehing.game.entity.component.movement;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class MovementReferenceComponent implements Component, Poolable {

    private MovementReference reference;

    public MovementReferenceComponent() {
    }

    public MovementReference getReference() {
	return reference;
    }

    public MovementReferenceComponent setReference(MovementReference reference) {
	this.reference = reference;

	return this;
    }

    @Override
    public void reset() {
	reference = null;
    }

    public enum MovementReference {
	POSITION, BODY;
    }
}