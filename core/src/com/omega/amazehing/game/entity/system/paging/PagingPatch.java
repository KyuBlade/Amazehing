package com.omega.amazehing.game.entity.system.paging;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.omega.amazehing.game.entity.EntityEngine;

public class PagingPatch {

    private EntityEngine engine;
    private Array<Entity> pagedEntities;
    private Vector2 position;
    private boolean isActive;
    private boolean isLoaded;
    private Object userData;

    public PagingPatch(EntityEngine engine, Vector2 position) {
	this.engine = engine;
	this.position = position;

	pagedEntities = new Array<Entity>();
    }

    public void addEntity(Entity entity) {
	pagedEntities.add(entity);
    }

    public void removeEntity(Entity entity) {
	pagedEntities.removeValue(entity, false);
    }

    public Vector2 getPosition() {
	return position;
    }

    public boolean isActive() {
	return isActive;
    }

    protected void setActive(boolean isActive) {
	this.isActive = isActive;
    }

    public boolean isLoaded() {
	return isLoaded;
    }

    public void setLoaded(boolean isLoaded) {
	this.isLoaded = isLoaded;
    }

    public void setUserData(Object userData) {
	this.userData = userData;
    }

    public Object getUserData() {
	return userData;
    }

    public Array<Entity> getPagedEntities() {
	return pagedEntities;
    }

    @Override
    public String toString() {
	return "PagingPatch[" + position.x + ", " + position.y + ", " + isLoaded + ", " + isActive + "]";
    }
}