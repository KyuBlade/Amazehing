package com.omega.amazehing.game.level.generator.cell.neighbour;

import com.badlogic.gdx.utils.Pool.Poolable;
import com.omega.amazehing.game.level.generator.cell.Direction;

public class Neighbour<T> implements Poolable {

    private T neighbour;
    private Direction direction;

    public Neighbour() {
    }

    public T getNeighbour() {
	return neighbour;
    }

    public Direction getDirection() {
	return direction;
    }

    public void setNeighbour(T neighbour) {
	this.neighbour = neighbour;
    }

    public void setDirection(Direction direction) {
	this.direction = direction;
    }

    @Override
    public void reset() {
	neighbour = null;
	direction = null;
    }
}