package com.omega.amazehing.database.query;

import com.mensa.database.sqlite.core.Database;
import com.mensa.database.sqlite.core.SQLiteException;

public abstract class QueryFactory<T> {

    protected static StringBuilder stringBuilder = new StringBuilder();

    protected Database database;
    protected String table;

    public QueryFactory(Database database, String table) throws SQLiteException {
	this.database = database;
	this.table = table;
    }

    public abstract void close();
}