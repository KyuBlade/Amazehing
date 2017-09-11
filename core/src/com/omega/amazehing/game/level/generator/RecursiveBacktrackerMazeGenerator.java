package com.omega.amazehing.game.level.generator;

import java.util.Stack;

import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.level.LevelSetting;
import com.omega.amazehing.game.level.generator.cell.Direction;
import com.omega.amazehing.game.level.generator.cell.MazeCell;
import com.omega.amazehing.game.level.generator.cell.RoomCell;
import com.omega.amazehing.game.level.generator.cell.neighbour.NeighbourCell;
import com.omega.amazehing.game.level.generator.cell.neighbour.NeighbourCellArray;

public class RecursiveBacktrackerMazeGenerator extends AbstractMazeGenerator {

    private Stack<MazeCell> unvisited;
    private Stack<MazeCell> stack;
    private MazeCell currentCell;

    public RecursiveBacktrackerMazeGenerator(EntityEngine engine, FactoryManager factoryManager,
	    LevelSetting setting) throws Exception {
	super(engine, factoryManager, setting);
    }

    @Override
    public void generatePath() throws Exception {
	unvisited = new Stack<MazeCell>();
	for (int i = 0; i < mazeCells.length; i++) {
	    for (int j = 0; j < mazeCells[i].length; j++) {
		MazeCell _cell = mazeCells[i][j];
		if (_cell instanceof RoomCell)
		    continue;

		unvisited.add(_cell);
	    }
	}

	stack = new Stack<MazeCell>();

	currentCell = start;
	setVisited(currentCell);

	while (!unvisited.isEmpty()) {
	    NeighbourCell _randNeighbour = getRandomUnvisitedNeighbour(currentCell);
	    if (_randNeighbour != null) {
		stack.push(_randNeighbour.getNeighbour());

		currentCell = carve(currentCell, _randNeighbour);

		// currentCell = _randNeighbour.getNeighbour();
		setVisited(currentCell);
		generatorPools.neighbourCellPool.free(_randNeighbour);
	    } else if (!stack.isEmpty()) {
		currentCell = stack.pop();
	    } else {
		currentCell = unvisited.pop();
	    }
	}
    }

    private boolean setVisited(MazeCell cell) {
	return unvisited.remove(cell);
    }

    private NeighbourCellArray getUnvisitedNeighbours(int x, int y) {
	NeighbourCellArray _unvisitedNeighbours = generatorPools.neighbourCellArrayPool.obtain();

	NeighbourCell _northCell = getNeighbourCellAt(x, y, Direction.N);
	if (_northCell != null && unvisited.contains(_northCell.getNeighbour()))
	    _unvisitedNeighbours.add(_northCell);

	NeighbourCell _southCell = getNeighbourCellAt(x, y, Direction.S);
	if (_southCell != null && unvisited.contains(_southCell.getNeighbour()))
	    _unvisitedNeighbours.add(_southCell);

	NeighbourCell _eastCell = getNeighbourCellAt(x, y, Direction.E);
	if (_eastCell != null && unvisited.contains(_eastCell.getNeighbour()))
	    _unvisitedNeighbours.add(_eastCell);

	NeighbourCell _westCell = getNeighbourCellAt(x, y, Direction.W);
	if (_westCell != null && unvisited.contains(_westCell.getNeighbour()))
	    _unvisitedNeighbours.add(_westCell);

	return _unvisitedNeighbours;
    }

    private NeighbourCell getRandomUnvisitedNeighbour(MazeCell refCell) {
	NeighbourCellArray _neighbours = getUnvisitedNeighbours(refCell.getX(), refCell.getY());

	NeighbourCell _neighbour = null;
	while (_neighbour == null && _neighbours.size > 0) {
	    _neighbour = _neighbours.removeIndex(rand.nextInt(_neighbours.size)); // Choose random neighbor
	}
	generatorPools.neighbourCellArrayPool.free(_neighbours);

	return _neighbour;
    }

    @Override
    protected void carveRoomEntrance(MazeCell entrance) throws Exception {
	NeighbourCell _roomNeighbour = getRoomNeighbour(entrance);
	setVisited(entrance);

	carve(entrance, _roomNeighbour);
	generatorPools.neighbourCellPool.free(_roomNeighbour);
    }

    @Override
    protected MazeCell carveRoomExit(MazeCell exit) throws Exception {
	NeighbourCell _roomNeighbour = getRoomNeighbour(exit);

	NeighbourCell _exitNeighbour = generatorPools.neighbourCellPool.obtain();
	_exitNeighbour.setNeighbour(exit);
	_exitNeighbour.setDirection(_roomNeighbour.getDirection().opposite());

	carve(_roomNeighbour.getNeighbour(), _exitNeighbour);
	generatorPools.neighbourCellPool.free(_roomNeighbour);
	generatorPools.neighbourCellPool.free(_exitNeighbour);

	return exit;
    }
}