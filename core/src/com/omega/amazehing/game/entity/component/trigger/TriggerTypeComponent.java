package com.omega.amazehing.game.entity.component.trigger;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TriggerTypeComponent implements Component, Poolable {

    private TriggerType type;

    public TriggerTypeComponent() {
    }

    public TriggerType getType() {
	return type;
    }

    public TriggerTypeComponent setType(TriggerType type) {
	this.type = type;

	return this;
    }

    @Override
    public void reset() {
	type = null;
    }

    public enum TriggerType {
	EndTrigger, EnemyAttack;
    }
}