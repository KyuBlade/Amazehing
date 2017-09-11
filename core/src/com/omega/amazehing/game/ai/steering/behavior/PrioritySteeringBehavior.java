package com.omega.amazehing.game.ai.steering.behavior;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.omega.amazehing.game.ai.steering.debug.Debuggable;

public class PrioritySteeringBehavior extends PrioritySteering<Vector2> implements Debuggable {

    public PrioritySteeringBehavior(Steerable<Vector2> owner) {
	super(owner);
    }

    @Override
    public void updateDebug() {
	for (SteeringBehavior<Vector2> behavior : behaviors) {
	    if (behavior instanceof Debuggable) {
		((Debuggable) behavior).updateDebug();
	    }
	}
    }

    public Array<SteeringBehavior<Vector2>> getBehaviors() {
	return behaviors;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PrioritySteeringBehavior add(SteeringBehavior<Vector2> behavior) {
	if (behavior == null) {
	    return this;
	}

	remove((Class<? extends SteeringBehavior<Vector2>>) behavior.getClass());
	behaviors.add(behavior);

	return this;
    }

    public void remove(Class<? extends SteeringBehavior<Vector2>> type) {
	for (int i = 0; i < behaviors.size; i++) {
	    SteeringBehavior<Vector2> _behavior = behaviors.get(i);
	    if (_behavior.getClass().isInstance(type)) {
		SteeringBehavior<Vector2> _steeringBehavior = behaviors.removeIndex(i);
		((Debuggable) _steeringBehavior).onRemoved();
	    }
	}
    }

    @SuppressWarnings("unchecked")
    public <T extends SteeringBehavior<Vector2>> T get(Class<T> type) {
	for (int i = 0; i < behaviors.size; i++) {
	    SteeringBehavior<Vector2> _behavior = behaviors.get(i);
	    if (type.isInstance(_behavior)) {
		return (T) _behavior;
	    }
	}

	return null;
    }

    @Override
    public void onRemoved() {
	for (SteeringBehavior<Vector2> behavior : behaviors) {
	    if (behavior instanceof Debuggable) {
		((Debuggable) behavior).onRemoved();
	    }
	}
    }
}