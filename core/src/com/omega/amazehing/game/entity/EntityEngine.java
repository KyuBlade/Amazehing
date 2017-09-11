package com.omega.amazehing.game.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class EntityEngine extends PooledEngine implements Disposable {

    private Array<Entity> toAdd;
    private Array<Entity> toRemove;

    public EntityEngine() {
	super(5, 25, 25, 100);

	toAdd = new Array<Entity>();
	toRemove = new Array<Entity>();
    }

    @Override
    public Entity createEntity() {
	return new Entity();
    }

    @Override
    public <T extends Component> T createComponent(Class<T> componentType) {
	try {
	    return componentType.newInstance();
	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	}

	return null;
    }

    @Override
    public void update(float deltaTime) {
	super.update(deltaTime);

	for (Entity _toAdd : toAdd) {
	    if (_toAdd != null) {
		super.addEntity(_toAdd);
	    }
	}
	toAdd.clear();

	for (Entity _toRemove : toRemove) {
	    if (_toRemove != null) {
		super.removeEntity(_toRemove);
	    }
	}
	toRemove.clear();
    }

    @Override
    public void addEntity(Entity entity) {
	toAdd.add(entity);
    }

    @Override
    public void removeEntity(Entity entity) {
	toRemove.add(entity);
    }

    @Override
    public void dispose() {
	for (EntitySystem _system : getSystems()) {
	    removeSystem(_system);
	    if (_system instanceof Disposable) {
		((Disposable) _system).dispose();
	    }
	}
    }
}