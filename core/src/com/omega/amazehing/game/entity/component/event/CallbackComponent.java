package com.omega.amazehing.game.entity.component.event;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CallbackComponent implements Component, Poolable {

    private Callback callback;

    public CallbackComponent() {
    }

    public CallbackComponent setCallback(Callback callback) {
	this.callback = callback;

	return this;
    }

    public Callback getCallback() {
	return callback;
    }

    @Override
    public void reset() {
	callback = null;
    }

    public interface Callback {

	void call() throws Exception;
    }
}