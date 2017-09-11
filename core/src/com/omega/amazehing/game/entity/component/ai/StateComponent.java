package com.omega.amazehing.game.entity.component.ai;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.utils.Pool.Poolable;

public class StateComponent implements Component, Poolable {

    private StateMachine<Entity> stateMachine;

    public StateComponent() {
    }

    public StateComponent init(Entity entity, State<Entity> state) {
	stateMachine = new DefaultStateMachine<Entity>(entity, state);

	return this;
    }

    public StateMachine<Entity> getStateMachine() {
	return stateMachine;
    }

    @Override
    public void reset() {
	stateMachine = null;
    }
}