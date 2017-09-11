package com.omega.amazehing.game.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.omega.amazehing.Constants;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.BodyComponent;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.ai.InSightComponent;
import com.omega.amazehing.game.entity.component.ai.path.TargetComponent;
import com.omega.amazehing.game.entity.component.node.ParentNodeComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.entity.component.transform.RotationComponent;
import com.omega.amazehing.game.entity.component.trigger.TriggerActivationComponent;
import com.omega.amazehing.game.entity.component.trigger.TriggerTypeComponent;
import com.omega.amazehing.game.entity.component.trigger.TriggerTypeComponent.TriggerType;

public class TriggerSystem extends IteratingSystem {

    private EntityEngine engine;

    private ComponentMapper<TriggerTypeComponent> triggerTypeMapper = ComponentMapperHandler
	    .getTriggerTypeMapper();
    private ComponentMapper<TriggerActivationComponent> triggerActivationMapper = ComponentMapperHandler
	    .getTriggerActivationMapper();
    private ComponentMapper<BodyComponent> bodyMapper = ComponentMapperHandler.getBodyMapper();
    private ComponentMapper<PositionComponent> positionMapper = ComponentMapperHandler
	    .getPositionMapper();
    private ComponentMapper<RotationComponent> rotationMapper = ComponentMapperHandler
	    .getRotationMapper();
    private ComponentMapper<ParentNodeComponent> parentNodeMapper = ComponentMapperHandler
	    .getParentNodeMapper();
    private ComponentMapper<InSightComponent> sightMapper = ComponentMapperHandler
	    .getInSightMapper();
    private ComponentMapper<TargetComponent> targetMapper = ComponentMapperHandler
	    .getTargetMapper();

    @SuppressWarnings("unchecked")
    public TriggerSystem() {
	super(Family.all(TriggerTypeComponent.class, TriggerActivationComponent.class).get(),
		Constants.Game.System.TRIGGER_SYSTEM_PRIORITY);
    }

    @Override
    public void addedToEngine(Engine engine) {
	super.addedToEngine(engine);

	this.engine = (EntityEngine) engine;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
	TriggerTypeComponent _triggerTypeComp = triggerTypeMapper.get(entity);
	TriggerType _triggerType = _triggerTypeComp.getType();
	TriggerActivationComponent _triggerActivationComp = triggerActivationMapper.get(entity);
	boolean _isActive = _triggerActivationComp.isActive();
	Entity _target = _triggerActivationComp.getTarget();
	ParentNodeComponent _parentComp = parentNodeMapper.get(entity);
	Entity _parent = null;
	if (_parentComp != null) {
	    _parent = _parentComp.getParent();
	}

	switch (_triggerType) {
	    case EndTrigger:
		onEndTrigger(_target, _isActive);

		break;
	    case EnemyAttack:
		onFovTrigger((_parent != null) ? _parent : entity, _target, _isActive);

		break;
	    default:
		break;

	}
    }

    private void onEndTrigger(Entity e, boolean isActivate) {
	if (isActivate) {
	    Gdx.app.debug("Trigger", "Activate");

	}
    }

    private void onFovTrigger(Entity triggered, Entity target, boolean isActivate) {
	if (triggered == null || target == null)
	    return;
    }
}
