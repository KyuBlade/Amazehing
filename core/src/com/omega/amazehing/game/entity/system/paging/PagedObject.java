package com.omega.amazehing.game.entity.system.paging;

import com.badlogic.gdx.math.Vector2;

public abstract class PagedObject<T> implements PagingListener {

    private boolean isActive;
    private boolean isDynamic;
    protected T object;

    public PagedObject(T object) {
	this(object, false);
    }

    public PagedObject(T object, boolean isDynamic) {
	this.object = object;
    }

    protected void setActive(boolean isActive) {
	this.isActive = isActive;
    }

    public boolean isActive() {
	return isActive;
    }

    public boolean isDynamic() {
	return isDynamic;
    }

    public T getObject() {
	return object;
    }

    public abstract Vector2 getPosition();
}
