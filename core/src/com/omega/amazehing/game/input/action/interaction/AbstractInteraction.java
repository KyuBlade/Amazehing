package com.omega.amazehing.game.input.action.interaction;

import com.badlogic.ashley.core.Entity;

public abstract class AbstractInteraction {

    protected boolean isOverContinuous;
    protected boolean isLeftContinuous;
    protected boolean isRightContinuous;

    public AbstractInteraction() {
    }

    public abstract void onOver(Entity entity, boolean isOvering);

    public abstract void onLeftCLick(Entity entity, boolean isDown);

    public abstract void onRightClick(Entity entity, boolean isDown);

    public boolean isOverContinuous() {
	return isOverContinuous;
    }

    public boolean isLeftContinuous() {
	return isLeftContinuous;
    }

    public boolean isRightContinuous() {
	return isRightContinuous;
    }
}