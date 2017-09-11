package com.omega.amazehing.factory.entity;

import com.badlogic.ashley.core.Entity;
import com.omega.amazehing.Assets;
import com.omega.amazehing.factory.Factory;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.InteractionComponent;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent;
import com.omega.amazehing.game.entity.component.ViewerNameComponent;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent.ViewerCategory;
import com.omega.amazehing.game.input.action.interaction.GatheringInteraction;

public class InteractiveObjectFactory implements Factory {

    private FactoryManager factoryManager;
    private RenderFactory renderFactory;
    private EntityEngine engine;

    public InteractiveObjectFactory(EntityEngine engine, FactoryManager factoryManager) {
	this.engine = engine;
	this.factoryManager = factoryManager;

	renderFactory = factoryManager.getFactory(RenderFactory.class);
    }

    public Entity createRift(float x, float y) {
	Entity _rift = renderFactory.createAnimation(Assets.Animations.BREACH, x, y, 2f, 2f, 0f,
		false, true, 2, true);
	_rift.add(
		engine.createComponent(ViewerCategoryComponent.class).setCategory(
			ViewerCategory.INTERACTIVE_OBJECT))
		.add(engine.createComponent(ViewerNameComponent.class).setName("Rift"))
		.add(engine.createComponent(InteractionComponent.class).setInteraction(
			new GatheringInteraction(engine, factoryManager)));

	return _rift;
    }
}