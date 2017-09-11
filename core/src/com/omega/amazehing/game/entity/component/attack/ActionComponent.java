package com.omega.amazehing.game.entity.component.attack;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ActionComponent implements Component, Poolable {

    public ActionComponent() {
    }

    @Override
    public void reset() {
    }

    public enum ActionType {
	Attack, Use;
    }
}