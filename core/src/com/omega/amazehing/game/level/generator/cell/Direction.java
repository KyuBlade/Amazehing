package com.omega.amazehing.game.level.generator.cell;

public enum Direction {

    N, E, S, W;

    public Direction opposite() {
	return Direction.values()[this.ordinal() ^ 2];
    }

}
