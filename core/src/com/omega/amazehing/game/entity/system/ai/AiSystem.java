package com.omega.amazehing.game.entity.system.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.omega.amazehing.Constants;

public class AiSystem extends IteratingSystem {

    private MessageDispatcher messageDispatcher;

    @SuppressWarnings("unchecked")
    public AiSystem() {
	super(Family.all().get(), Constants.Game.System.AI_SYSTEM_PRIORITY);

	messageDispatcher = new MessageDispatcher();
    }

    @Override
    public void update(float deltaTime) {
	super.update(deltaTime);

	messageDispatcher.update(deltaTime);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
    }
}