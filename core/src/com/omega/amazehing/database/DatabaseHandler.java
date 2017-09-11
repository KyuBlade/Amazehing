package com.omega.amazehing.database;

import java.io.IOException;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mensa.database.sqlite.core.Database;
import com.mensa.database.sqlite.core.DatabaseFactory;
import com.mensa.database.sqlite.core.SQLiteContext;
import com.mensa.database.sqlite.core.SQLiteException;

public class DatabaseHandler {

    protected Database database;

    public DatabaseHandler(FileHandle databasePath) throws SQLiteException, IOException {
	database = DatabaseFactory.getNewDatabase(new SQLiteContext<Application>(Gdx.app), false,
		databasePath.path(), 1, null, null);
	database.setupDatabase();
	database.openOrCreateDatabase();
    }

    public void beginTransaction() throws SQLiteException {
	database.beginTransaction();
    }

    public void endTransaction() throws SQLiteException {
	database.endTransaction();
    }

    public void commit() throws SQLiteException {
	database.commit();
    }

    public void close() {
	try {
	    database.closeDatabase();
	} catch (SQLiteException e) {
	}
    }
}