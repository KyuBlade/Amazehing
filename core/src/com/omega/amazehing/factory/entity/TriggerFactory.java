package com.omega.amazehing.factory.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.omega.amazehing.factory.BodyFactory;
import com.omega.amazehing.factory.Factory;
import com.omega.amazehing.factory.body.shape.Shape;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.BodyComponent;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent.ViewerCategory;
import com.omega.amazehing.game.entity.component.ViewerNameComponent;
import com.omega.amazehing.game.entity.component.trigger.TriggerActivationComponent;
import com.omega.amazehing.game.entity.component.trigger.TriggerTypeComponent;
import com.omega.amazehing.game.entity.component.trigger.TriggerTypeComponent.TriggerType;

public class TriggerFactory implements Factory {

    private EntityEngine engine;
    private BodyFactory bodyFactory;

    public TriggerFactory(EntityEngine engine, BodyFactory bodyFactory) {
	this.engine = engine;
	this.bodyFactory = bodyFactory;
    }

    public Entity createTrigger(TriggerType type, float x, float y, Shape shape) {
	Entity _entity = engine.createEntity();
	Body _body = bodyFactory.createTrigger(x, y, shape);
	_entity.add(engine.createComponent(BodyComponent.class).setBody(_body));
	_body.setUserData(_entity);

	_entity.add(
		engine.createComponent(ViewerCategoryComponent.class).setCategory(
			ViewerCategory.TRIGGER))
		.add(engine.createComponent(ViewerNameComponent.class).setName("Trigger"))
		.add(engine.createComponent(TriggerActivationComponent.class))
		.add(engine.createComponent(TriggerTypeComponent.class).setType(type));

	engine.addEntity(_entity);

	return _entity;
    }
}