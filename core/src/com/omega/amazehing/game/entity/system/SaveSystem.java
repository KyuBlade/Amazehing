package com.omega.amazehing.game.entity.system;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.mensa.database.sqlite.core.SQLiteException;
import com.omega.amazehing.Constants;
import com.omega.amazehing.database.SaveDatabaseHandler;
import com.omega.amazehing.database.query.PlayerQueryFactory;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.entity.PlayerFactory;
import com.omega.amazehing.game.entity.system.paging.PagingPatch;
import com.omega.amazehing.game.entity.system.paging.PagingSystem;
import com.omega.amazehing.game.level.generator.AbstractMazeGenerator;
import com.omega.amazehing.util.BenchmarkUtil;

public class SaveSystem extends EntitySystem {

    private static final Logger logger = LoggerFactory.getLogger(SaveSystem.class);

    private FactoryManager factoryManager;
    private FileHandle saveDir;
    private FileHandle saveFile;

    private SaveDatabaseHandler database;

    private Engine engine;

    public SaveSystem(FactoryManager factoryManager, String saveName)
	    throws ClassNotFoundException {
	super(Constants.Game.System.SAVE_SYSTEM_PRIORITY);

	this.factoryManager = factoryManager;

	String _savePath = Constants.Save.DIRECTORY + File.separator;
	saveDir = Gdx.files.local(_savePath);
	saveFile = Gdx.files.local(_savePath + saveName + Constants.Save.EXTENSION);

	if (!saveDir.exists()) {
	    saveDir.mkdirs();
	}

	try {
	    database = new SaveDatabaseHandler(saveFile, factoryManager);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void addedToEngine(Engine engine) {
	this.engine = engine;
    }

    public void saveAll(AbstractMazeGenerator generator) throws SQLiteException {
	long _benchId = BenchmarkUtil.start();

	database.beginTransaction();
	Entity[][] _tiles = generator.getTiles();
	for (int y = 0; y < _tiles[0].length; y++) {
	    for (int x = 0; x < _tiles.length; x++) {
		Entity _tile = _tiles[x][y];
		database.insertTile(_tile);
	    }
	}

	PlayerFactory _playerFactory = factoryManager.getFactory(PlayerFactory.class);
	database.insertPlayer(_playerFactory.getPlayerHandler().getPlayer());
	database.commit();
	database.endTransaction();

	logger.debug("Save Level : {}", BenchmarkUtil.end(_benchId) + "ms");
    }

    public void load() throws Exception {
	database.loadPlayer();
    }

    public SaveDatabaseHandler getDatabase() {
	return database;
    }

    public void close() {
	if (database != null) {
	    database.close();
	}
    }
}