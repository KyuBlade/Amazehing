package com.omega.amazehing.game.level.generator.trigger;

import com.omega.amazehing.game.entity.component.trigger.TriggerTypeComponent.TriggerType;

public abstract class Trigger<Shape2D> {

    protected Shape2D shape;
    protected TriggerType type;

    public Trigger() {
    }

    public Shape2D getShape() {
	return shape;
    }

    public TriggerType getType() {
	return type;
    }

}
