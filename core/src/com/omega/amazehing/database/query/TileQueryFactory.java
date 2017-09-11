package com.omega.amazehing.database.query;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mensa.database.sqlite.core.Database;
import com.mensa.database.sqlite.core.DatabaseCursor;
import com.mensa.database.sqlite.core.PreparedStatement;
import com.mensa.database.sqlite.core.SQLiteException;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.TileTypeComponent;
import com.omega.amazehing.game.entity.component.render.BlendComponent;
import com.omega.amazehing.game.entity.component.render.TextureComponent;
import com.omega.amazehing.game.entity.component.render.ZIndexComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.entity.component.transform.RotationComponent;
import com.omega.amazehing.game.entity.component.transform.SizeComponent;
import com.omega.amazehing.game.level.generator.tile.TileType;
import com.omega.amazehing.render.RegionManager;
import com.omega.amazehing.render.region.IndexedTextureRegion;

public class TileQueryFactory extends QueryFactory<Entity> {

    private PreparedStatement insertPrepStat;
    private PreparedStatement getPatchPrepStat;

    private static final ComponentMapper<TileTypeComponent> typeMapper = ComponentMapperHandler
	    .getTileTypeMapper();
    private static final ComponentMapper<TextureComponent> textureMapper = ComponentMapperHandler
	    .getTextureMapper();
    private static final ComponentMapper<PositionComponent> positionMapper = ComponentMapperHandler
	    .getPositionMapper();
    private static final ComponentMapper<SizeComponent> sizeMapper = ComponentMapperHandler
	    .getSizeMapper();
    private static final ComponentMapper<RotationComponent> rotationMapper = ComponentMapperHandler
	    .getRotationMapper();
    private static final ComponentMapper<BlendComponent> blendMapper = ComponentMapperHandler
	    .getBlendMapper();
    private static final ComponentMapper<ZIndexComponent> zindexMapper = ComponentMapperHandler
	    .getZIndexMapper();

    public TileQueryFactory(Database database, String table) throws SQLiteException {
	super(database, table);

	stringBuilder.append("INSERT INTO ").append(table)
		.append(" (Type, RegionId, x, y, Width, Height, Rotation, IsBlend, ZIndex) ")
		.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
	insertPrepStat = database.getPreparedStatement(stringBuilder.toString());
	stringBuilder.setLength(0);

	stringBuilder.append("SELECT * FROM ").append(table).append(" WHERE x BETWEEN ? AND ? ")
		.append("AND y BETWEEN ? AND ?;");
	getPatchPrepStat = database.getPreparedStatement(stringBuilder.toString());
	stringBuilder.setLength(0);
    }

    public void insertTile(Entity tile) throws SQLiteException {
	TileTypeComponent _typeComp = typeMapper.get(tile);
	TextureComponent _textureComp = textureMapper.get(tile);
	PositionComponent _positionComp = positionMapper.get(tile);
	SizeComponent _sizeComp = sizeMapper.get(tile);
	RotationComponent _rotationComp = rotationMapper.get(tile);
	BlendComponent _blendComp = blendMapper.get(tile);
	ZIndexComponent _zindexComp = zindexMapper.get(tile);

	TileType _type = _typeComp.getType();
	TextureRegion _region = _textureComp.getTexture();
	int _regionId = ((IndexedTextureRegion) _region).getRegionId();
	Vector2 _position = _positionComp.getPosition();

	insertPrepStat.setInt(1, _type.getId());
	insertPrepStat.setInt(2, _regionId);
	insertPrepStat.setFloat(3, _position.x);
	insertPrepStat.setFloat(4, _position.y);
	insertPrepStat.setFloat(5, _sizeComp.getWidth());
	insertPrepStat.setFloat(6, _sizeComp.getHeight());
	insertPrepStat.setFloat(7, _rotationComp.getRotation());
	insertPrepStat.setInt(8, _blendComp.isBlending() ? 1 : 0);
	insertPrepStat.setInt(9, _zindexComp.getZindex());

	insertPrepStat.executeInsert();
    }

    public DatabaseCursor getTilesFromPatch(float x, float y, float size) throws SQLiteException {
	getPatchPrepStat.setFloat(1, x);
	getPatchPrepStat.setFloat(2, x + size - 1);
	getPatchPrepStat.setFloat(3, y);
	getPatchPrepStat.setFloat(4, y + size - 1);

	return getPatchPrepStat.executeQuery();
    }

    @Override
    public void close() {
	try {
	    insertPrepStat.close();
	    getPatchPrepStat.close();
	} catch (SQLiteException e) {
	    e.printStackTrace();
	}
    }
}