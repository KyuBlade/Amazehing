package com.omega.amazehing.database;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.mensa.database.sqlite.core.DatabaseCursor;
import com.mensa.database.sqlite.core.SQLiteException;
import com.omega.amazehing.database.query.PlayerQueryFactory;
import com.omega.amazehing.database.query.QueryFactory;
import com.omega.amazehing.database.query.TileQueryFactory;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.entity.LevelFactory;
import com.omega.amazehing.factory.entity.PlayerFactory;
import com.omega.amazehing.factory.entity.ProcessingFactory;
import com.omega.amazehing.factory.entity.RenderFactory;
import com.omega.amazehing.game.entity.component.event.CallbackComponent.Callback;
import com.omega.amazehing.game.entity.system.balancer.LoadBalancerSystem;
import com.omega.amazehing.render.RegionManager;
import com.omega.amazehing.task.TaskListener;
import com.omega.amazehing.task.TileLoadingTask;

public class SaveDatabaseHandler extends DatabaseHandler {

    private static final String TILE_TABLE_NAME = "Tile";
    private static final String WALL_TABLE_NAME = "Wall";
    private static final String WALL_CORNER_TABLE_NAME = "WallCorner";
    private static final String TRIGGER_TABLE_NAME = "Trigger";
    private static final String MONSTER_TABLE_NAME = "Monster";
    private static final String PLAYER_TABLE_NAME = "Player";

    private FactoryManager factoryManager;
    private ExecutorService executor;

    private ObjectMap<Class<? extends QueryFactory<?>>, QueryFactory<?>> factories;

    public SaveDatabaseHandler(FileHandle saveFile, FactoryManager factoryManager)
	    throws SQLiteException, IOException {
	super(saveFile);

	this.factoryManager = factoryManager;
	executor = Executors.newSingleThreadExecutor();

	StringBuilder _sBuild = new StringBuilder();

	// Build database
	_sBuild.append("CREATE TABLE IF NOT EXISTS '").append(TILE_TABLE_NAME).append("' (")
		.append("'Type' INTEGER NOT NULL DEFAULT 0, ")
		.append("'RegionId' INTEGER NOT NULL DEFAULT 0, ")
		.append("'x' REAL NOT NULL DEFAULT 0, ").append("'y' REAL NOT NULL DEFAULT 0, ")
		.append("'Width' REAL NOT NULL DEFAULT 1, ")
		.append("'Height' REAL NOT NULL DEFAULT 1, ")
		.append("'Rotation' REAL NOT NULL DEFAULT 0, ")
		.append("'IsBlend' INTEGER NOT NULL DEFAULT 0, ")
		.append("'ZIndex' INTEGER NOT NULL DEFAULT 0").append(");");

	database.execSQL(_sBuild.toString());

	_sBuild.setLength(0);

	_sBuild.append("CREATE TABLE IF NOT EXISTS '").append(PLAYER_TABLE_NAME).append("' (")
		.append("'x' REAL NOT NULL DEFAULT 0, ").append("'y' REAL NOT NULL DEFAULT 0, ")
		.append("'Rotation' REAL NOT NULL DEFAULT 0, ")
		.append("'Health' INTEGER NOT NULL DEFAULT 0").append(");");

	database.execSQL(_sBuild.toString());

	_sBuild.setLength(0);

	RenderFactory _renderFactory = factoryManager.getFactory(RenderFactory.class);
	RegionManager _regionManager = _renderFactory.getRegionManager();

	// Register factories
	factories = new ObjectMap<Class<? extends QueryFactory<?>>, QueryFactory<?>>();
	factories.put(TileQueryFactory.class, new TileQueryFactory(database, TILE_TABLE_NAME));
	factories.put(PlayerQueryFactory.class,
		new PlayerQueryFactory(database, PLAYER_TABLE_NAME));
    }

    public void insertTile(Entity tile) throws SQLiteException {
	TileQueryFactory _queryFactory = (TileQueryFactory) factories.get(TileQueryFactory.class);
	_queryFactory.insertTile(tile);
    }

    /**
     * Load the provided patch tiles.
     * 
     * @param position position of the patch
     * @param size size of the patch
     * @param listener called when the last task is processed
     * @throws Exception
     */
    public void loadTilesFromPatch(final Vector2 position, final float size, final Callback callback)
	    throws Exception {
	executor.submit(new Runnable() {

	    @Override
	    public void run() {
		TileQueryFactory _queryFactory = (TileQueryFactory) factories
			.get(TileQueryFactory.class);
		ProcessingFactory _processFactory = factoryManager
			.getFactory(ProcessingFactory.class);
		LevelFactory _levelFactory = factoryManager.getFactory(LevelFactory.class);
		RenderFactory _renderFactory = factoryManager.getFactory(RenderFactory.class);
		Engine _engine = factoryManager.getEngine();

		DatabaseCursor _result;
		try {
		    _result = _queryFactory.getTilesFromPatch(position.x * size, position.y * size,
			    size);
		} catch (SQLiteException e) {
		    throw new RuntimeException(e);
		}
		int _columnIndex;
		int _type;
		int _regionId;
		float _x;
		float _y;
		float _width;
		float _height;

		TileLoadingTask _task = null;

		int _resultCount = 0;
		try {
		    _resultCount = _result.getCount();
		} catch (SQLiteException e) {
		    throw new RuntimeException(e);
		}

		int _i = 0;
		while (_result.next()) {
		    _i++;
		    _columnIndex = 0;
		    _type = _result.getInt(_columnIndex++);
		    _regionId = _result.getInt(_columnIndex++);
		    _x = _result.getFloat(_columnIndex++);
		    _y = _result.getFloat(_columnIndex++);
		    _width = _result.getFloat(_columnIndex++);
		    _height = _result.getFloat(_columnIndex++);

		    if (_i == _resultCount) {
			_task = new TileLoadingTask(_levelFactory, _regionId, _x, _y, _type,
				callback);
		    } else {
			_task = new TileLoadingTask(_levelFactory, _regionId, _x, _y, _type);
		    }
		    _processFactory.createLoadBalancedTask(_task);
		}
	    }
	});
    }

    public void insertPlayer(Entity player) throws SQLiteException {
	PlayerQueryFactory _queryFactory = (PlayerQueryFactory) factories
		.get(PlayerQueryFactory.class);

	_queryFactory.insertPlayer(player);
    }

    public void loadPlayer() throws SQLiteException {
	PlayerQueryFactory _queryFactory = (PlayerQueryFactory) factories
		.get(PlayerQueryFactory.class);
	PlayerFactory _playerFactory = factoryManager.getFactory(PlayerFactory.class);

	DatabaseCursor _result = _queryFactory.getPlayer();
	int _index;
	float _x;
	float _y;
	float _rotation;
	int _health;
	while (_result.next()) {
	    _index = 0;
	    Gdx.app.debug("Create", "Player");
	    _x = _result.getFloat(_index++);
	    _y = _result.getFloat(_index++);
	    _rotation = _result.getFloat(_index++);
	    _health = _result.getInt(_index++);

	    _playerFactory.createPlayer(_x, _y);
	}
    }

    @Override
    public void close() {
	super.close();

	for (QueryFactory<?> factory : factories.values()) {
	    factory.close();
	}
    }
}