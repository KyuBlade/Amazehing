package com.omega.amazehing.game.level.generator;

import com.badlogic.gdx.utils.Pool;
import com.omega.amazehing.game.level.generator.cell.neighbour.NeighbourCell;
import com.omega.amazehing.game.level.generator.cell.neighbour.NeighbourCellArray;
import com.omega.amazehing.game.level.generator.tile.NeighbourTile;
import com.omega.amazehing.game.level.generator.tile.NeighbourTileArray;

public class GeneratorPools {

    protected Pool<NeighbourCell> neighbourCellPool;
    protected Pool<NeighbourTile> neighbourTilePool;
    protected Pool<NeighbourCellArray> neighbourCellArrayPool;
    protected Pool<NeighbourTileArray> neighbourTileArrayPool;

    public GeneratorPools() {
	neighbourCellPool = new Pool<NeighbourCell>(5, 25) {

	    @Override
	    protected NeighbourCell newObject() {
		return new NeighbourCell();
	    }
	};
	neighbourTilePool = new Pool<NeighbourTile>(5, 25) {

	    @Override
	    protected NeighbourTile newObject() {
		return new NeighbourTile();
	    }
	};
	neighbourCellArrayPool = new Pool<NeighbourCellArray>(1, 10) {

	    @Override
	    protected NeighbourCellArray newObject() {
		return new NeighbourCellArray();
	    }
	};
	neighbourTileArrayPool = new Pool<NeighbourTileArray>(1, 10) {

	    @Override
	    protected NeighbourTileArray newObject() {
		return new NeighbourTileArray();
	    }
	};
    }
}