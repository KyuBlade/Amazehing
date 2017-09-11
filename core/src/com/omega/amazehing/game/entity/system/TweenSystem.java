package com.omega.amazehing.game.entity.system;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.ashley.core.EntitySystem;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.TweenFactory;

public class TweenSystem extends EntitySystem {

    private TweenManager manager;

    public TweenSystem(FactoryManager factoryManager) {
	TweenFactory _tweenFactory = factoryManager.getFactory(TweenFactory.class);
	manager = _tweenFactory.getManager();
    }

    @Override
    public void update(float deltaTime) {
	manager.update(deltaTime);
    }
}