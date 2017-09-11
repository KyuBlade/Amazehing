package com.omega.amazehing.game.level.generator.tile;

public enum TileType {
    WALL(0), FLOOR(1);

    private static final TileType[] lookup = new TileType[2];

    static {
	for (TileType type : values()) {
	    lookup[type.id] = type;
	}
    }

    private int id;

    private TileType(int id) {
	this.id = id;
    }

    public int getId() {
	return id;
    }

    public static TileType getFor(int id) {
	return lookup[id];
    }
}