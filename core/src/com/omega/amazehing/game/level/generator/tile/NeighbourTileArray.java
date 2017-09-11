package com.omega.amazehing.game.level.generator.tile;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Pool.Poolable;

public class NeighbourTileArray extends Array<NeighbourTile> implements Poolable {

    public NeighbourTileArray() {
	super(4);
    }

    public void reset() {
	Pools.freeAll(this);
	clear();
    }
}