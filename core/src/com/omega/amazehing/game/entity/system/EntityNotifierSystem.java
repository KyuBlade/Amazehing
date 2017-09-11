package com.omega.amazehing.game.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.omega.amazehing.Constants;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.event.CallbackComponent.Callback;
import com.omega.amazehing.game.entity.component.event.NotifierComponent;
import com.omega.amazehing.game.entity.component.event.NotifierComponent.NotifierEvent;

public class EntityNotifierSystem extends EntitySystem {

    private Family family;

    private static final ComponentMapper<NotifierComponent> notifierMapper = ComponentMapperHandler
	    .getNotifierMapper();

    public EntityNotifierSystem() {
	super(Constants.Game.System.ENTITY_NOTIFIER_SYSTEM_PRIORITY);

	family = Family.all(NotifierComponent.class).get();
    }

    @Override
    public void addedToEngine(Engine engine) {
	engine.addEntityListener(family, new EntityListener() {

	    @Override
	    public void entityRemoved(Entity entity) {
		NotifierComponent _notifComp = notifierMapper.get(entity);
		Callback _removedCallback = _notifComp.getEvent(NotifierEvent.REMOVED);
		try {
		    if (_removedCallback != null) {
			_removedCallback.call();
		    }
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
	    }

	    @Override
	    public void entityAdded(Entity entity) {
		NotifierComponent _notifComp = notifierMapper.get(entity);
		Callback _addedCallback = _notifComp.getEvent(NotifierEvent.ADDED);
		try {
		    if (_addedCallback != null) {
			_addedCallback.call();
		    }
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
	    }
	});
    }
}