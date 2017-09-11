package com.omega.amazehing.task;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.omega.amazehing.factory.entity.LevelFactory;
import com.omega.amazehing.game.entity.component.event.NotifierComponent;
import com.omega.amazehing.game.entity.component.event.CallbackComponent.Callback;
import com.omega.amazehing.game.entity.component.event.NotifierComponent.NotifierEvent;
import com.omega.amazehing.game.level.generator.tile.TileType;

public class TileLoadingTask implements Task {

    private LevelFactory levelFactory;
    private int regionId;
    private float x;
    private float y;
    private int type;
    private Callback callback;

    public TileLoadingTask(LevelFactory levelFactory, int regionId, float x, float y, int type) {
	this(levelFactory, regionId, x, y, type, null);
    }

    public TileLoadingTask(LevelFactory levelFactory, int regionId, float x, float y, int type,
	    Callback callback) {
	this.levelFactory = levelFactory;
	this.regionId = regionId;
	this.x = x;
	this.y = y;
	this.type = type;
	this.callback = callback;
    }

    @Override
    public void run() throws Exception {
	Entity _tile = levelFactory.createTile(regionId, x, y, TileType.getFor(type));
	if (callback != null) {
	    _tile.add(levelFactory.getEngine().createComponent(NotifierComponent.class)
		    .addEvent(NotifierEvent.ADDED, callback));
	}
    }
}