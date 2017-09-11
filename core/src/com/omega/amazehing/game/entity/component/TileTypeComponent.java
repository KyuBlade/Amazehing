package com.omega.amazehing.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.omega.amazehing.game.level.generator.tile.TileType;

public class TileTypeComponent implements Component, Poolable {

    private TileType type;

    public TileTypeComponent() {
    }

    public TileType getType() {
	return type;
    }

    public TileTypeComponent setType(TileType type) {
	this.type = type;

	return this;
    }

    @Override
    public void reset() {
	type = null;
    }
}