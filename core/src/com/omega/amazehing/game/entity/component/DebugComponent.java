package com.omega.amazehing.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class DebugComponent implements Component, Poolable {

    public enum DebugType {
	PAGING, AI, UI, PHYS_BODY, ROOM;
    }

    private DebugType type;

    public DebugComponent() {
    }

    public DebugType getType() {
	return type;
    }

    public DebugComponent setType(DebugType type) {
	this.type = type;

	return this;
    }

    @Override
    public void reset() {
	type = null;
    }
}