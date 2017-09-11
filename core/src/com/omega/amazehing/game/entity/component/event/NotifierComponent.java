package com.omega.amazehing.game.entity.component.event;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.omega.amazehing.game.entity.component.event.CallbackComponent.Callback;

public class NotifierComponent implements Component, Poolable {

    public static enum NotifierEvent {
	ADDED, REMOVED;
    }

    private ObjectMap<NotifierEvent, Callback> events;

    public NotifierComponent() {
	events = new ObjectMap<NotifierEvent, Callback>(NotifierEvent.values().length);
    }

    public NotifierComponent addEvent(NotifierEvent event, Callback callback) {
	events.put(event, callback);

	return this;
    }

    public boolean hasEvent(NotifierEvent event) {
	return events.containsKey(event);
    }

    public ObjectMap<NotifierEvent, Callback> getEvents() {
	return events;
    }

    public Callback getEvent(NotifierEvent event) {
	return events.get(event);
    }

    @Override
    public void reset() {
	events.clear();
    }
}