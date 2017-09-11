package com.omega.amazehing.factory.entity;

import com.badlogic.ashley.core.Entity;
import com.omega.amazehing.Assets;
import com.omega.amazehing.factory.Factory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.TileTypeComponent;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent.ViewerCategory;
import com.omega.amazehing.game.level.generator.tile.TileType;

public class LevelFactory implements Factory {

    private RenderFactory renderFactory;

    public LevelFactory(RenderFactory renderFactory) {
	this.renderFactory = renderFactory;
    }

    public Entity createTile(int regionId, float x, float y, TileType type) throws Exception {
	return createTile(regionId, x, y, type, true);
    }

    public Entity createTile(int regionId, float x, float y, TileType type, boolean add)
	    throws Exception {
	Entity _tile = renderFactory.createSprite(Assets.Atlases.LEVEL_ID, regionId,
		ViewerCategory.SPRITE, x, y, 1f, 1f, 0f, false, false,
		type == TileType.FLOOR ? 9 : 10, false, add);
	EntityEngine _engine = renderFactory.getEngine();
	_tile.add(_engine.createComponent(TileTypeComponent.class).setType(type));

	return _tile;
    }

    public EntityEngine getEngine() {
	return renderFactory.getEngine();
    }
}