package com.omega.amazehing.game.level.generator;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.omega.amazehing.Constants;
import com.omega.amazehing.factory.BodyFactory;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.entity.InteractiveObjectFactory;
import com.omega.amazehing.factory.entity.LevelFactory;
import com.omega.amazehing.factory.entity.MonsterFactory;
import com.omega.amazehing.factory.entity.PlayerFactory;
import com.omega.amazehing.factory.entity.RenderFactory;
import com.omega.amazehing.factory.entity.TriggerFactory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.TileTypeComponent;
import com.omega.amazehing.game.entity.component.render.TextureComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.entity.system.ai.PathfindingSystem;
import com.omega.amazehing.game.level.LevelSetting;
import com.omega.amazehing.game.level.generator.cell.Direction;
import com.omega.amazehing.game.level.generator.cell.EntranceCell;
import com.omega.amazehing.game.level.generator.cell.MazeCell;
import com.omega.amazehing.game.level.generator.cell.RoomCell;
import com.omega.amazehing.game.level.generator.cell.RoomDoor;
import com.omega.amazehing.game.level.generator.cell.neighbour.NeighbourCell;
import com.omega.amazehing.game.level.generator.cell.neighbour.NeighbourCellArray;
import com.omega.amazehing.game.level.generator.entity.Room;
import com.omega.amazehing.game.level.generator.tile.NeighbourTile;
import com.omega.amazehing.game.level.generator.tile.TileType;
import com.omega.amazehing.game.level.generator.wall.Wall;
import com.omega.amazehing.util.Utils;

public abstract class AbstractMazeGenerator {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractMazeGenerator.class);

    protected EntityEngine engine;
    protected PathfindingSystem pathfindingSystem;

    protected BodyFactory bodyFactory;
    protected TriggerFactory triggerFactory;
    protected RenderFactory renderFactory;
    protected PlayerFactory playerFactory;
    protected MonsterFactory monsterFactory;
    protected InteractiveObjectFactory interactiveObjectFactory;
    protected LevelFactory levelFactory;

    protected LevelSetting setting;
    protected Random rand;

    protected int roomCount;

    protected int startWidth;
    protected static final int startHeight = 30;

    protected static final int endWidth = 10;
    protected static final int endHeight = 10;

    protected MazeCell start;
    protected MazeCell end;

    protected MazeCell[][] mazeCells;
    protected Entity[][] tiles;

    protected Array<Room> rooms;
    protected Array<Wall> walls;
    protected Array<Entity> monsters;

    protected GeneratorPools generatorPools;
    protected Pool<Vector2> vectorPool;

    protected static final ComponentMapper<TileTypeComponent> typeMapper = ComponentMapperHandler
	    .getTileTypeMapper();
    protected static final ComponentMapper<TextureComponent> textureMapper = ComponentMapperHandler
	    .getTextureMapper();
    protected static final ComponentMapper<PositionComponent> positionMapper = ComponentMapperHandler
	    .getPositionMapper();

    // TODO : Temporary
    private static final int FLOOR_CONCRETE = 1;
    private static final int FLOOR_GRASS = 2;
    private static final int WALL_STONE = 4;

    public AbstractMazeGenerator(EntityEngine engine, FactoryManager factoryManager,
	    LevelSetting setting) throws Exception {
	this.engine = engine;
	this.setting = setting;

	pathfindingSystem = engine.getSystem(PathfindingSystem.class);

	this.bodyFactory = factoryManager.getFactory(BodyFactory.class);
	this.triggerFactory = factoryManager.getFactory(TriggerFactory.class);
	this.renderFactory = factoryManager.getFactory(RenderFactory.class);
	this.playerFactory = factoryManager.getFactory(PlayerFactory.class);
	this.monsterFactory = factoryManager.getFactory(MonsterFactory.class);
	this.interactiveObjectFactory = factoryManager.getFactory(InteractiveObjectFactory.class);
	this.levelFactory = factoryManager.getFactory(LevelFactory.class);

	initialize();
	generateStart();
	generateRooms();
	populateCells();
	Gdx.app.debug("Generator", "Generating path");
	long _startTime = System.nanoTime();
	generatePath();
	Gdx.app.debug("Generator",
		"Path generation finished : " + ((System.nanoTime() - _startTime) / 100000) + "ms");
	generateEnd();
    }

    protected void initialize() {
	Gdx.app.debug("Generator", "Initializing");
	long _startTime = System.nanoTime();
	int _cellSize = setting.getCellSize();
	int _width = setting.getWidth();
	int _height = setting.getHeight();

	generatorPools = new GeneratorPools();
	vectorPool = Pools.get(Vector2.class);

	mazeCells = new MazeCell[_width][_height];
	tiles = new Entity[_width * _cellSize][_height * _cellSize + endHeight + startHeight];

	rooms = new Array<Room>();
	walls = new Array<Wall>();
	monsters = new Array<Entity>();

	rand = new Random(setting.getSeed());
	Gdx.app.debug("Generator",
		"Initialization finished : " + ((System.nanoTime() - _startTime) / 100000) + "ms");
    }

    protected void generateEnd() throws Exception {
	Gdx.app.debug("Generator", "Generating end");
	long _startTime = System.nanoTime();
	int _width = setting.getWidth();
	int _cellSize = setting.getCellSize();
	IntArray _stack = new IntArray();

	setFloor(FLOOR_GRASS, 0, 0, _width * _cellSize, endHeight);

	for (int i = 1; i < _width - 2; i++) {
	    _stack.add(i);
	}

	int _tmpX = _stack.removeIndex(rand.nextInt(_stack.size));
	int _tmpY = 0;

	setFloor(FLOOR_CONCRETE, _tmpX * _cellSize + 1, endHeight,
		_tmpX * _cellSize + _cellSize - 1, endHeight + 1);
	end = mazeCells[_tmpX][_tmpY];
	walls.removeValue(end.getUpWall(), true);
	end.setUpWall(null);
	Gdx.app.debug("Generator",
		"End generation finished : " + ((System.nanoTime() - _startTime) / 100000) + "ms");
    }

    protected void generateStart() throws Exception {
	Gdx.app.debug("Generator", "Generating start");
	long _startTime = System.nanoTime();
	int _height = setting.getHeight();
	int _width = setting.getWidth();
	int _cellSize = setting.getCellSize();
	IntArray _stack = new IntArray();

	int _arrayStartY = endHeight + _height * _cellSize;
	setFloor(FLOOR_GRASS, 0, _arrayStartY, _width * _cellSize, _arrayStartY + startHeight);

	// Create start
	for (int i = 1; i < mazeCells.length - 2; i++) {
	    MazeCell _cell = mazeCells[i][_height - 1];
	    if (_cell == null)
		_stack.add(i);
	}

	int _tmpX = _stack.removeIndex(rand.nextInt(_stack.size));
	int _tmpY = _height - 1;

	start = new EntranceCell(_tmpX, _tmpY);
	mazeCells[_tmpX][_tmpY] = start;

	NeighbourCell _neighbour = generatorPools.neighbourCellPool.obtain();
	_neighbour.setNeighbour(start);
	_neighbour.setDirection(Direction.N);
	carve(null, _neighbour);
	generatorPools.neighbourCellPool.free(_neighbour);

	// Create rift
	// interactiveObjectFactory.createRift(endWidth * setting.getCellSize() * 0.5f,
	// startHeight * 0.5f + setting.getHeight() * setting.getCellSize() + endHeight);
	//
	// interactiveObjectFactory.createRift(endWidth * setting.getCellSize() * 0.5f - 5f,
	// startHeight * 0.5f + setting.getHeight() * setting.getCellSize() + endHeight);

	// Player Spawn
	playerFactory.createPlayer((start.getX() * _cellSize) + (_cellSize * 0.5f),
		((start.getY() + 1) * _cellSize) + (_cellSize * 0.5f) + endHeight);

	Gdx.app.debug("Generator", "Start generation finished : " + ((System
		.nanoTime() - _startTime) / 100000) + "ms");
    }

    public void generateRooms() {
	Gdx.app.debug("Generator", "Generation rooms");
	long _startTime = System.nanoTime();
	int _roomX = MathUtils.clamp(
		rand.nextInt(setting.getWidth() - Constants.Generator.MAX_ROOM_SIZE), 1,
		setting.getWidth() - 1);
	int _roomY = MathUtils.clamp(
		rand.nextInt(setting.getHeight() - Constants.Generator.MAX_ROOM_SIZE), 1,
		setting.getHeight() - 1);
	int _roomSize = Constants.Generator.MIN_ROOM_SIZE + rand
		.nextInt(Constants.Generator.MAX_ROOM_SIZE - Constants.Generator.MIN_ROOM_SIZE + 1);

	// Create room
	Room _room = new Room(_roomX, _roomY, _roomSize);

	// Create entrance and exit
	RoomDoor _firstDoor = null;
	RoomDoor _secondDoor = null;

	Direction[] _dirs = Direction.values();
	Utils.shuffleArray(rand, _dirs);

	for (int i = 0; i < _dirs.length; i++) {
	    Direction _entranceDir = _dirs[i];
	    _firstDoor = getRoomDoor(_entranceDir, _room);

	    if (_firstDoor != null) {
		try {
		    mazeCells[_firstDoor.getX()][_firstDoor.getY()] = _firstDoor;
		} catch (ArrayIndexOutOfBoundsException e) {
		    continue;
		}

		Direction[] _exitDirs = new Direction[3];
		Direction _opposite = _entranceDir.opposite();
		_exitDirs[0] = _opposite;
		_exitDirs[1] = (_opposite.equals(Direction.S) || _opposite.equals(Direction.N)
			? Direction.W : Direction.N);
		_exitDirs[2] = _exitDirs[1].opposite();
		for (int j = 0; j < _exitDirs.length; j++) {
		    Direction _exitDir = _exitDirs[j];
		    if (_exitDir == _entranceDir)
			continue;

		    _secondDoor = getRoomDoor(_exitDir, _room);
		    if (_secondDoor != null) {
			try {
			    mazeCells[_secondDoor.getX()][_secondDoor.getY()] = _secondDoor;
			} catch (ArrayIndexOutOfBoundsException e) {
			    continue;
			}
			break;
		    }
		}
		break;
	    }
	}

	// Set room doors
	_room.setFirstDoor(_firstDoor);
	_room.setSecondDoor(_secondDoor);

	// Populate room
	for (int y = _roomY; y < _roomSize + _roomY; y++) {
	    for (int x = _roomX; x < _roomSize + _roomX; x++) {
		RoomCell _cell = new RoomCell(_room, x, y);
		mazeCells[x][y] = _cell;
	    }
	}

	// final RoomDoor _finalFirstDoor = _firstDoor;
	// final RoomDoor _finalSecondDoor = _secondDoor;
	// Gdx.app.postRunnable(new Runnable() {
	//
	// @Override
	// public void run() {
	// // Create room debug
	// Entity _roomDebug = renderFactory.createRectangle(ViewerCategory.ROOM_DEBUG,
	// new Color(0f, 0.5f, 0f, 0.2f), _roomX * _cellSize - 0.5f,
	// _roomY * _cellSize - 0.5f + endHeight, _roomSize * _cellSize,
	// _roomSize * _cellSize, true, 0, true);
	// _roomDebug
	// .add(engine.createComponent(DebugComponent.class).setType(DebugType.ROOM));
	//
	// Entity _firstDoorDebug = renderFactory.createRectangle(ViewerCategory.ROOM_DEBUG,
	// new Color(0f, 0f, 0.4f, 0.5f), _finalFirstDoor.getX() * _cellSize - 0.5f,
	// _finalFirstDoor.getY() * _cellSize - 0.5f + endHeight, _cellSize,
	// _cellSize, true, 0, true);
	// _firstDoorDebug.add(engine.createComponent(DebugComponent.class).setType(
	// DebugType.ROOM));
	//
	// Entity _secondDoorDebug = renderFactory.createRectangle(ViewerCategory.ROOM_DEBUG,
	// new Color(0f, 0f, 0.4f, 0.5f), _finalSecondDoor.getX() * _cellSize - 0.5f,
	// _finalSecondDoor.getY() * _cellSize - 0.5f + endHeight, _cellSize,
	// _cellSize, true, 0, true);
	// _secondDoorDebug.add(engine.createComponent(DebugComponent.class).setType(
	// DebugType.ROOM));
	//
	// // Spawn in rooms
	// float _halfSize = _roomSize * _cellSize * 0.5f;
	// try {
	// monsterFactory.createMonster(1, _roomX * _cellSize + _halfSize,
	// _roomY * _cellSize + _halfSize + endHeight);
	// } catch (SQLiteException e) {
	// Gdx.app.error("AbstractMazeGenerator", "Unable to create monster", e);
	// }
	// }
	// });

	rooms.add(_room);
	Gdx.app.debug("Generator", "Rooms generation finished : " + ((System
		.nanoTime() - _startTime) / 100000) + "ms");
    }

    /**
     * Get a random door on the specified side
     * 
     * @param doorSide the room side to get a door
     * @return the door cell
     */
    private RoomDoor getRoomDoor(Direction doorSide, Room room) {
	int _roomX = room.getX();
	int _roomY = room.getY();
	int _roomSize = room.getSize();
	int _doorX = 0;
	int _doorY = 0;
	switch (doorSide) {
	    case N:
		_doorX = _roomX + (1 + rand.nextInt(_roomSize - 2));
		_doorY = _roomY - 1;

		break;

	    case S:
		_doorX = _roomX + (1 + rand.nextInt(_roomSize - 2));
		_doorY = _roomY + _roomSize;

		break;

	    case W:
		_doorX = _roomX - 1;
		_doorY = _roomY + (1 + rand.nextInt(_roomSize - 2));

		break;

	    case E:
		_doorX = _roomX + _roomSize;
		_doorY = _roomY + (1 + rand.nextInt(_roomSize - 2));

		break;

	    default:
		break;
	}

	return new RoomDoor(room, _doorX, _doorY);
    }

    private void populateCells() {
	Gdx.app.debug("Generator", "Starting populate cells");
	long _startTime = System.nanoTime();
	int _width = setting.getWidth();
	int _height = setting.getHeight();

	for (int y = 0; y < _height; y++) {
	    for (int x = 0; x < _width; x++) {
		if (mazeCells[x][y] == null) {
		    mazeCells[x][y] = new MazeCell(x, y);
		}
	    }
	}
	Gdx.app.debug("Generator",
		"Finished pouplate cells : " + (System.nanoTime() - _startTime) / 100000);
    }

    protected abstract void generatePath() throws Exception;

    /**
     * Used to batch walls tile in one physics wall
     * 
     * @param tile the ref tile
     * @param dir the wall direction to check
     * @return the number of walls
     */
    protected int hasNeighbourWall(Entity tile, Direction dir) {
	PositionComponent _posComp = positionMapper.get(tile);
	Vector2 _tilePos = _posComp.getPosition();
	NeighbourTile _neighbour = null;
	int _neighbours = 1;
	switch (dir) {
	    case N:
		_neighbour = getNeighborTileAt((int) _tilePos.x, (int) _tilePos.y, Direction.E);

		break;

	    case E:
		_neighbour = getNeighborTileAt((int) _tilePos.x, (int) _tilePos.y, Direction.S);

		break;

	    case S:
		_neighbour = getNeighborTileAt((int) _tilePos.x, (int) _tilePos.y, Direction.E);

		break;

	    case W:
		_neighbour = getNeighborTileAt((int) _tilePos.x, (int) _tilePos.y, Direction.S);

		break;

	    default:
		throw new IllegalArgumentException("Unhandled wall direction.");
	}

	if (_neighbour == null)
	    return 1;

	Entity _tile = _neighbour.getNeighbour();
	if (_tile == null) {
	    return 1;
	}

	TileTypeComponent _typeComp = typeMapper.get(_tile);
	TileType _type = _typeComp.getType();
	if (_type.equals(TileType.WALL)) {
	    _neighbours = hasNeighbourWall(_tile, dir);
	    _neighbours++;
	}

	return _neighbours;
    }

    protected MazeCell getCellAt(int x, int y) {
	try {
	    return mazeCells[x][y];
	} catch (ArrayIndexOutOfBoundsException e) {
	    return null;
	}
    }

    protected NeighbourCell getNeighbourCellAt(MazeCell cell, Direction dir) {
	return getNeighbourCellAt(cell.getX(), cell.getY(), dir);
    }

    protected NeighbourCell getNeighbourCellAt(int x, int y, Direction dir) {
	int _tmpX = x;
	int _tmpY = y;

	switch (dir) {
	    case N:
		_tmpY -= 1;

		break;

	    case S:
		_tmpY += 1;

		break;

	    case E:
		_tmpX += 1;

		break;

	    case W:
		_tmpX -= 1;

		break;

	    default:
		throw new IllegalArgumentException("The specified direction don't exist.");
	}

	try {
	    MazeCell _cell = mazeCells[_tmpX][_tmpY];
	    if (_cell != null) {
		NeighbourCell _neighbour = generatorPools.neighbourCellPool.obtain();
		_neighbour.setNeighbour(_cell);
		_neighbour.setDirection(dir);

		return _neighbour;
	    }
	} catch (ArrayIndexOutOfBoundsException e) {
	}

	return null;
    }

    public NeighbourCellArray getNeighboursCell(MazeCell cell) {
	return getNeighboursCell(cell.getX(), cell.getY());
    }

    public NeighbourCellArray getNeighboursCell(int x, int y) {
	NeighbourCellArray _neighbours = generatorPools.neighbourCellArrayPool.obtain();

	NeighbourCell _northCell = getNeighbourCellAt(x, y, Direction.N);
	if (_northCell != null)
	    _neighbours.add(_northCell);

	NeighbourCell _southCell = getNeighbourCellAt(x, y, Direction.S);
	if (_southCell != null)
	    _neighbours.add(_southCell);

	NeighbourCell _eastCell = getNeighbourCellAt(x, y, Direction.E);
	if (_eastCell != null)
	    _neighbours.add(_eastCell);

	NeighbourCell _westCell = getNeighbourCellAt(x, y, Direction.W);
	if (_westCell != null)
	    _neighbours.add(_westCell);

	return _neighbours;
    }

    /**
     * Get the neighbor tile at the specified location and direction.<br />
     * <b>The result must be freed as soon as possible.</b>
     * 
     * @param x
     * @param y
     * @param dir the direction of the neighbor from the location
     * @return the neighbor tile or null if none
     */
    protected NeighbourTile getNeighborTileAt(int x, int y, Direction dir) {
	int _tmpX = x;
	int _tmpY = y;

	switch (dir) {
	    case N:
		_tmpY -= 1;

		break;

	    case S:
		_tmpY += 1;

		break;

	    case E:
		_tmpX += 1;

		break;

	    case W:
		_tmpX -= 1;

		break;

	    default:
		throw new IllegalArgumentException("The specified direction don't exist.");
	}

	try {
	    Entity _tile = tiles[_tmpX][_tmpY];
	    if (_tile != null) {
		NeighbourTile _neighbour = generatorPools.neighbourTilePool.obtain();
		_neighbour.setNeighbour(_tile);
		_neighbour.setDirection(dir);

		return _neighbour;
	    }
	} catch (ArrayIndexOutOfBoundsException e) {
	}

	return null;
    }

    protected void carve(NeighbourCell to) throws Exception {
	MazeCell _to = to.getNeighbour();
	NeighbourCell _from = getNeighbourCellAt(_to.getX(), _to.getY(),
		to.getDirection().opposite());

	carve(_from.getNeighbour(), to);
	generatorPools.neighbourCellPool.free(_from);
    }

    protected MazeCell carve(MazeCell from, NeighbourCell to) throws Exception {
	int _cellSize = setting.getCellSize();
	final MazeCell _neighbour = to.getNeighbour();
	final Direction _dir = to.getDirection();
	int _x = to.getNeighbour().getX() * _cellSize;
	int _y = to.getNeighbour().getY() * _cellSize;
	switch (_dir) {
	    case N:
		for (int y = 0; y < _cellSize; y++) {
		    for (int x = 0; x < _cellSize; x++) {
			if (x < 1 || x == _cellSize - 1 || y == 0) {
			    setWall(WALL_STONE, _x + x, _y + y + endHeight);
			} else {
			    setFloor(FLOOR_CONCRETE, _x + x, _y + y + endHeight);
			}
		    }
		}

		if (from != null) {
		    for (int i = 1; i < _cellSize - 1; i++) {
			setFloor(FLOOR_CONCRETE, from.getX() * _cellSize + i,
				from.getY() * _cellSize + endHeight);
		    }
		}

		break;

	    case S:
		for (int y = 0; y < _cellSize; y++) {
		    for (int x = 0; x < _cellSize; x++) {
			if (x == 0 || x == _cellSize - 1 || y == _cellSize - 1) {
			    setWall(WALL_STONE, _x + x, _y + y + endHeight);
			} else {
			    setFloor(FLOOR_CONCRETE, _x + x, _y + y + endHeight);
			}
		    }
		}

		if (from != null) {
		    for (int i = 1; i < _cellSize - 1; i++) {
			setFloor(FLOOR_CONCRETE, from.getX() * _cellSize + i,
				from.getY() * _cellSize + endHeight + _cellSize - 1);
		    }
		}

		break;

	    case E:
		for (int y = 0; y < _cellSize; y++) {
		    for (int x = 0; x < _cellSize; x++) {
			if (x == _cellSize - 1 || y == 0 || y == _cellSize - 1) {
			    setWall(WALL_STONE, _x + x, _y + y + endHeight);
			} else {
			    setFloor(FLOOR_CONCRETE, _x + x, _y + y + endHeight);
			}
		    }
		}

		if (from != null) {
		    for (int i = 1; i < _cellSize - 1; i++) {
			setFloor(FLOOR_CONCRETE, from.getX() * _cellSize + _cellSize - 1,
				from.getY() * _cellSize + i + endHeight);
		    }
		}

		break;

	    case W:
		for (int y = 0; y < _cellSize; y++) {
		    for (int x = 0; x < _cellSize; x++) {
			if (x == 0 || y == 0 || y == _cellSize - 1) {
			    setWall(WALL_STONE, _x + x, _y + y + endHeight);
			} else {
			    setFloor(FLOOR_CONCRETE, _x + x, _y + y + endHeight);
			}
		    }
		}

		if (from != null) {
		    for (int i = 1; i < _cellSize - 1; i++) {
			setFloor(FLOOR_CONCRETE, from.getX() * _cellSize,
				from.getY() * _cellSize + i + endHeight);
		    }
		}

		break;

	    default:
		throw new IllegalArgumentException("Wrong direction specified.");
	}

	if (!(_neighbour instanceof RoomCell)) {
	    if (!_dir.equals(Direction.E)) {
		Wall _leftWall = new Wall(_x, _y - 0.5f + _cellSize * 0.5f + endHeight, 1f,
			_cellSize, 0f);
		walls.add(_leftWall);
		_neighbour.setLeftWall(_leftWall);
	    }
	    if (!_dir.equals(Direction.S) && _neighbour != end) {
		Wall _upWall = new Wall(_x - 0.5f + _cellSize * 0.5f, _y + endHeight, 1f, _cellSize,
			90f);
		walls.add(_upWall);
		_neighbour.setUpWall(_upWall);
	    }
	    if (!_dir.equals(Direction.W)) {
		Wall _rightWall = new Wall(_x + _cellSize - 1f,
			_y - 0.5f + _cellSize * 0.5f + endHeight, 1f, _cellSize, 0f);
		walls.add(_rightWall);
		_neighbour.setRightWall(_rightWall);
	    }
	    if (!_dir.equals(Direction.N)) {
		Wall _downWall = new Wall(_x - 0.5f + _cellSize * 0.5f,
			_y - 1f + _cellSize + endHeight, 1f, _cellSize, 90f);
		walls.add(_downWall);
		_neighbour.setDowWall(_downWall);
	    }

	    // Create corners
	    Wall _leftUpWall = new Wall(_x, _y + endHeight, 1f, 1f, 0f);
	    walls.add(_leftUpWall);
	    Wall _rightUpWall = new Wall(_x + _cellSize - 1f, _y + endHeight, 1f, 1f, 0f);
	    walls.add(_rightUpWall);
	    Wall _rightDownWall = new Wall(_x + _cellSize - 1f, _y + _cellSize - 1f + endHeight, 1f,
		    1f, 0f);
	    walls.add(_rightDownWall);
	    Wall _leftDownWall = new Wall(_x, _y + _cellSize - 1f + endHeight, 1f, 1f, 0f);
	    walls.add(_leftDownWall);
	}

	// Delete the wall
	NeighbourCell _neighCell = getNeighbourCellAt(_neighbour, _dir.opposite());
	if (_neighCell != null) {
	    MazeCell _cell = _neighCell.getNeighbour();
	    switch (_dir) {
		case E:
		    walls.removeValue(_cell.getRightWall(), true);
		    _cell.setRightWall(null);

		    break;
		case N:
		    walls.removeValue(_cell.getUpWall(), true);
		    _cell.setUpWall(null);

		    break;
		case S:
		    walls.removeValue(_cell.getDowWall(), true);
		    _cell.setDowWall(null);

		    break;
		case W:
		    walls.removeValue(_cell.getLeftWall(), true);
		    _cell.setLeftWall(null);

		    break;
		default:
		    throw new IllegalArgumentException("Wrong direction specified.");
	    }
	    generatorPools.neighbourCellPool.free(_neighCell);
	}

	if (_neighbour instanceof RoomDoor && !((RoomDoor) _neighbour).getRoom().isCarved()) {
	    carveRoomEntrance(_neighbour);

	    Room _room = ((RoomDoor) _neighbour).getRoom();
	    carveRoom(_room);
	    _room.setCarved(true);

	    RoomDoor _exitDoor = null;
	    if (_neighbour == _room.getFirstDoor()) {
		_exitDoor = _room.getSecondDoor();
	    } else {
		_exitDoor = _room.getFirstDoor();
	    }

	    return carveRoomExit(_exitDoor);
	}

	return to.getNeighbour();
    }

    protected abstract void carveRoomEntrance(MazeCell door) throws Exception;

    protected void carveRoom(Room room) throws Exception {
	int _cellSize = setting.getCellSize();
	int _roomSize = room.getSize() * _cellSize;
	int _roomX = room.getX() * _cellSize;
	int _roomY = room.getY() * _cellSize;
	setFloor(FLOOR_CONCRETE, _roomX, _roomY + endHeight, _roomX + _roomSize,
		_roomY + _roomSize + endHeight);
    }

    protected abstract MazeCell carveRoomExit(MazeCell door) throws Exception;

    /**
     * Get the neighbour cell of type {@link RoomCell}
     * 
     * @param refCell the cell of reference
     * @return the neighbour room cell
     */
    protected NeighbourCell getRoomNeighbour(MazeCell refCell) {
	NeighbourCellArray _neighbours = getNeighboursCell(refCell);

	for (int i = 0; i < _neighbours.size; i++) {
	    NeighbourCell _checkCell = _neighbours.get(i);
	    if (_checkCell.getNeighbour() instanceof RoomCell) {
		_neighbours.removeValue(_checkCell, true);

		generatorPools.neighbourCellArrayPool.free(_neighbours);

		return _checkCell;
	    }
	}
	generatorPools.neighbourCellArrayPool.free(_neighbours);

	return null;
    }

    private void setTile(int regionId, final int x, final int y, final TileType type)
	    throws Exception {
	Entity _tile = tiles[x][y];
	if (_tile != null) {
	    engine.removeEntity(_tile);
	}

	_tile = levelFactory.createTile(regionId, x, y, type);
	tiles[x][y] = _tile;
    }

    protected void setFloor(int regionId, int x1, int y1, int x2, int y2) throws Exception {
	for (int y = y1; y < y2; y++) {
	    for (int x = x1; x < x2; x++) {
		setFloor(regionId, x, y);
	    }
	}
    }

    protected void setFloor(int regionId, int x, int y) throws Exception {
	setTile(regionId, x, y, TileType.FLOOR);
    }

    protected void setWall(int regionId, int x1, int y1, int x2, int y2) throws Exception {
	for (int y = y1; y < y2; y++) {
	    for (int x = x1; x < x2; x++) {
		setWall(regionId, x, y);
	    }
	}
    }

    protected void setWall(int regionId, int x, int y) throws Exception {
	setTile(regionId, x, y, TileType.WALL);
    }

    public Array<Wall> getWalls() {
	return walls;
    }

    public Entity[][] getTiles() {
	return tiles;
    }

    public MazeCell getStart() {
	return start;
    }

    public MazeCell getEnd() {
	return end;
    }

    public MazeCell[][] getMazeCells() {
	return mazeCells;
    }

    public Array<Entity> getMonsters() {
	return monsters;
    }

    public Array<Room> getRooms() {
	return rooms;
    }
}