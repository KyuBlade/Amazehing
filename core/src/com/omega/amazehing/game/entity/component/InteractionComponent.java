package com.omega.amazehing.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.omega.amazehing.game.input.action.interaction.AbstractInteraction;

public class InteractionComponent implements Component, Poolable {

    public enum InteractionType {
	OVER, LEFT_CLICK, RIGHT_CLICK;
    }

    private AbstractInteraction interaction;

    public InteractionComponent() {
    }

    public AbstractInteraction getInteraction() {
	return interaction;
    }

    public InteractionComponent setInteraction(AbstractInteraction interaction) {
	this.interaction = interaction;

	return this;
    }

    @Override
    public void reset() {
	interaction = null;
    }
}