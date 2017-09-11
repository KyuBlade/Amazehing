package com.omega.amazehing.game.entity.component.movement;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class MovementTypeComponent implements Component, Poolable {

    private MovementType movementType;

    public MovementTypeComponent() {
    }

    public MovementType getMovementType() {
	return movementType;
    }

    public MovementTypeComponent setMovementType(MovementType movementType) {
	this.movementType = movementType;

	return this;
    }

    public enum MovementType {
	ABSOLUTE("absolute"), RELATIVE("relative");

	private String name;

	private MovementType(String name) {
	    this.name = name;
	}

	public static MovementType getByName(String name) {
	    for (int i = 0; i < values().length; i++) {
		MovementType _type = values()[i];
		if (_type.name.equals(name)) {
		    return _type;
		}
	    }

	    return null;
	}
    }

    @Override
    public void reset() {
	movementType = null;
    }
}