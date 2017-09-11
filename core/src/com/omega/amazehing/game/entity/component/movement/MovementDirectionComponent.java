package com.omega.amazehing.game.entity.component.movement;

import java.util.EnumSet;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.omega.amazehing.game.level.generator.cell.Direction;

public class MovementDirectionComponent implements Component, Poolable {

    private EnumSet<Direction> directions = EnumSet.noneOf(Direction.class);

    public MovementDirectionComponent() {
    }

    public MovementDirectionComponent addDirection(Direction dir) {
	directions.add(dir);

	return this;
    }

    public MovementDirectionComponent removeDirection(Direction dir) {
	directions.remove(dir);

	return this;
    }

    public MovementDirectionComponent resetDirections() {
	directions.clear();

	return this;
    }

    public EnumSet<Direction> getDirections() {
	return directions;
    }

    public void reset() {
	directions.clear();
    }
}