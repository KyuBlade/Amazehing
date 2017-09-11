package com.omega.amazehing.game.level.generator.cell.neighbour;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Pools;

public class NeighbourCellArray extends Array<NeighbourCell> implements Poolable {

    public NeighbourCellArray() {
	super(4);
    }

    @Override
    public void reset() {
	Pools.freeAll(this);
	clear();
    }
}