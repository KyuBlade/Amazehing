package com.omega.amazehing.game.ai.steering.debug;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.game.ai.steering.AbstractSteeringBehaviorDebug;
import com.omega.amazehing.game.ai.steering.behavior.WanderSteeringBehavior;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.DebugComponent;
import com.omega.amazehing.game.entity.component.DebugComponent.DebugType;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent.ViewerCategory;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.entity.component.transform.RadiusComponent;

public class WanderSteeringBehaviorDebug extends
	AbstractSteeringBehaviorDebug<WanderSteeringBehavior> {

    private Entity radius;
    private Entity target;

    public WanderSteeringBehaviorDebug(EntityEngine engine, FactoryManager factoryManager,
	    WanderSteeringBehavior behavior) {
	super(engine, factoryManager, behavior);

	Vector2 _wanderCenter = steeringBehavior.getWanderCenter();
	radius = renderFactory.createCircle(ViewerCategory.AI, "Wander Radius", Color.GREEN,
		_wanderCenter.x, _wanderCenter.y, steeringBehavior.getWanderRadius(), false, 1,
		false);
	radius.add(engine.createComponent(DebugComponent.class).setType(DebugType.AI));
	engine.addEntity(radius);

	Vector2 _wanderInternalPos = steeringBehavior.getInternalTargetPosition();
	target = renderFactory.createCircle(ViewerCategory.AI, "Wander Target", Color.RED,
		_wanderInternalPos.x, _wanderInternalPos.y, 0.2f, true, 0, false);
	target.add(engine.createComponent(DebugComponent.class).setType(DebugType.AI));
	engine.addEntity(target);
    }

    @Override
    public void update() {
	Vector2 _wanderCenter = steeringBehavior.getWanderCenter();
	radius.add(engine.createComponent(PositionComponent.class).setPosition(_wanderCenter)).add(
		engine.createComponent(RadiusComponent.class).setRadius(
			steeringBehavior.getWanderRadius()));

	Vector2 _wanderInternalPos = steeringBehavior.getInternalTargetPosition();
	target.add(engine.createComponent(PositionComponent.class).setPosition(_wanderInternalPos));
    }

    @Override
    public void onRemoved() {
	engine.removeEntity(radius);
	engine.removeEntity(target);
    }
}