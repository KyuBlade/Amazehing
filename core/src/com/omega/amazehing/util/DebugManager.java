package com.omega.amazehing.util;

import com.badlogic.gdx.utils.ObjectMap;
import com.omega.amazehing.game.entity.component.DebugComponent;
import com.omega.amazehing.game.entity.component.DebugComponent.DebugType;

public class DebugManager {

    private ObjectMap<DebugType, Boolean> debugs;

    private static DebugManager instance;

    private DebugManager() {
	DebugType[] _debugTypes = DebugType.values();
	debugs = new ObjectMap<DebugComponent.DebugType, Boolean>(_debugTypes.length);
	for (DebugType _debugType : _debugTypes) {
	    debugs.put(_debugType, false);
	}
    }

    public static final DebugManager getInstance() {
	if (instance == null) {
	    instance = new DebugManager();
	}

	return instance;
    }

    public boolean get(DebugType type) {
	return debugs.get(type);
    }

    public void set(DebugType type, boolean isActive) {
	debugs.put(type, isActive);
    }
}