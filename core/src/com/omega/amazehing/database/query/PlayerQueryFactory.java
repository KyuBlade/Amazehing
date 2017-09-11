package com.omega.amazehing.database.query;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.mensa.database.sqlite.core.Database;
import com.mensa.database.sqlite.core.DatabaseCursor;
import com.mensa.database.sqlite.core.PreparedStatement;
import com.mensa.database.sqlite.core.SQLiteException;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.attack.state.HealthComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.entity.component.transform.RotationComponent;

public class PlayerQueryFactory extends QueryFactory<Entity> {

    private PreparedStatement insertPrepStat;
    private PreparedStatement updatePrepStat;
    private PreparedStatement getPrepStat;

    private static final ComponentMapper<PositionComponent> positionMapper = ComponentMapperHandler
	    .getPositionMapper();
    private static final ComponentMapper<RotationComponent> rotationMapper = ComponentMapperHandler
	    .getRotationMapper();
    private static final ComponentMapper<HealthComponent> healthMapper = ComponentMapperHandler
	    .getHealthMapper();

    public PlayerQueryFactory(Database database, String table) throws SQLiteException {
	super(database, table);

	stringBuilder.append("INSERT INTO ").append(table).append(" (x, y, Rotation, Health) ")
		.append("VALUES (?, ?, ?, ?);");
	insertPrepStat = database.getPreparedStatement(stringBuilder.toString());
	stringBuilder.setLength(0);

	stringBuilder.append("UPDATE ").append(table)
		.append(" SET x = ?, y = ?, Rotation = ?, Health = ?;");
	updatePrepStat = database.getPreparedStatement(stringBuilder.toString());
	stringBuilder.setLength(0);

	stringBuilder.append("SELECT * FROM ").append(table).append(';');
	getPrepStat = database.getPreparedStatement(stringBuilder.toString());
	stringBuilder.toString();
    }

    public void insertPlayer(Entity player) throws SQLiteException {
	PositionComponent _posComp = positionMapper.get(player);
	RotationComponent _rotComp = rotationMapper.get(player);
	HealthComponent _healthComp = healthMapper.get(player);

	Vector2 _position = _posComp.getPosition();

	insertPrepStat.setFloat(1, _position.x);
	insertPrepStat.setFloat(2, _position.y);
	insertPrepStat.setFloat(3, _rotComp.getRotation());
	insertPrepStat.setInt(4, _healthComp.getCurrent());

	insertPrepStat.executeInsert();
    }

    public void updatePlayer(Entity player) throws SQLiteException {
	PositionComponent _posComp = positionMapper.get(player);
	RotationComponent _rotComp = rotationMapper.get(player);
	HealthComponent _healthComp = healthMapper.get(player);

	Vector2 _position = _posComp.getPosition();

	updatePrepStat.setFloat(1, _position.x);
	updatePrepStat.setFloat(2, _position.y);
	updatePrepStat.setFloat(3, _rotComp.getRotation());
	updatePrepStat.setInt(4, _healthComp.getCurrent());

	updatePrepStat.executeUpdateDelete();
    }

    public DatabaseCursor getPlayer() throws SQLiteException {
	return getPrepStat.executeQuery();
    }

    @Override
    public void close() {
	try {
	    insertPrepStat.close();
	    updatePrepStat.close();
	    getPrepStat.close();
	} catch (SQLiteException e) {
	    e.printStackTrace();
	}
    }
}