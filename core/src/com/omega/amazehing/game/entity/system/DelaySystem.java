package com.omega.amazehing.game.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.omega.amazehing.Constants;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.DelayComponent;

public class DelaySystem extends IteratingSystem {

    private EntityEngine engine;

    private static final ComponentMapper<DelayComponent> delayMapper = ComponentMapperHandler
	    .getDelayMapper();

    @SuppressWarnings("unchecked")
    public DelaySystem() {
	super(Family.all(DelayComponent.class).get(), Constants.Game.System.DELAY_SYSTEM_PRIORITY);
    }

    @Override
    public void addedToEngine(Engine engine) {
	super.addedToEngine(engine);

	this.engine = (EntityEngine) engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
	DelayComponent _delayComp = delayMapper.get(entity);
	_delayComp.addAccumulator(deltaTime);

	float _delay = _delayComp.getDelay();
	float _accumulator = _delayComp.getAccumulator();
	if (_accumulator < _delay) {
	    return;
	}

	_delayComp.getRunnable().run();
	if (!_delayComp.isContinuous()) {
	    engine.removeEntity(entity);
	} else {
	    _delayComp.setAccumulator(_accumulator - _delay);
	}
    }
}