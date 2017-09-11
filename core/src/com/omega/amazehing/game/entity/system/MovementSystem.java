package com.omega.amazehing.game.entity.system;

import java.util.EnumSet;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.omega.amazehing.Constants;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.BodyComponent;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.movement.AngularVelocityComponent;
import com.omega.amazehing.game.entity.component.movement.MoveToComponent;
import com.omega.amazehing.game.entity.component.movement.MovementDirectionComponent;
import com.omega.amazehing.game.entity.component.movement.MovementReferenceComponent;
import com.omega.amazehing.game.entity.component.movement.MovementTypeComponent;
import com.omega.amazehing.game.entity.component.movement.MovementTypeComponent.MovementType;
import com.omega.amazehing.game.entity.component.movement.SpeedComponent;
import com.omega.amazehing.game.entity.component.movement.VelocityComponent;
import com.omega.amazehing.game.entity.component.node.AttachedNodeComponent;
import com.omega.amazehing.game.entity.component.node.ParentNodeComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.entity.component.transform.RotationComponent;
import com.omega.amazehing.game.entity.component.transform.SizeComponent;
import com.omega.amazehing.game.level.generator.cell.Direction;

public class MovementSystem extends IteratingSystem {

    private EntityEngine engine;

    private static final ComponentMapper<VelocityComponent> velocityMapper = ComponentMapperHandler
	    .getVelocityMapper();
    private static final ComponentMapper<AngularVelocityComponent> angularVelocityMapper = ComponentMapperHandler
	    .getAngularVelocityMapper();
    private static final ComponentMapper<BodyComponent> bodyMapper = ComponentMapperHandler
	    .getBodyMapper();
    private static final ComponentMapper<SpeedComponent> speedMapper = ComponentMapperHandler
	    .getSpeedMapper();
    private static final ComponentMapper<PositionComponent> positionMapper = ComponentMapperHandler
	    .getPositionMapper();
    private static final ComponentMapper<RotationComponent> rotationMapper = ComponentMapperHandler
	    .getRotationMapper();
    private static final ComponentMapper<MovementTypeComponent> movTypeMapper = ComponentMapperHandler
	    .getMovementTypeMapper();
    private static final ComponentMapper<MovementReferenceComponent> movRefMapper = ComponentMapperHandler
	    .getMovementReferenceMapper();
    private static final ComponentMapper<AttachedNodeComponent> attachedNodesMapper = ComponentMapperHandler
	    .getAttachedNodeMapper();
    private static final ComponentMapper<MoveToComponent> moveToMapper = ComponentMapperHandler
	    .getMoveToMapper();
    private static final ComponentMapper<MovementDirectionComponent> moveDirMapper = ComponentMapperHandler
	    .getMovementDirectionMapper();

    private Pool<Vector2> vectorPool;

    @SuppressWarnings("unchecked")
    public MovementSystem() {
	super(Family
		.one(VelocityComponent.class, AngularVelocityComponent.class, SpeedComponent.class)
		.all(PositionComponent.class, RotationComponent.class, SizeComponent.class)
		.exclude(ParentNodeComponent.class).get(),
		Constants.Game.System.MOVEMENT_SYSTEM_PRIORITY);

	vectorPool = Pools.get(Vector2.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
	super.addedToEngine(engine);

	this.engine = (EntityEngine) engine;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
	PositionComponent _posComp = positionMapper.get(entity);
	RotationComponent _rotComponent = rotationMapper.get(entity);
	VelocityComponent _veloComp = velocityMapper.get(entity);
	AngularVelocityComponent _angVeloComp = angularVelocityMapper.get(entity);
	RotationComponent _rotComp = rotationMapper.get(entity);
	BodyComponent _bodyComp = bodyMapper.get(entity);
	SpeedComponent _speedComp = speedMapper.get(entity);

	Vector2 _position = _posComp.getPosition();
	Vector2 _velo = _veloComp.getVelocity();
	float _rotation = _rotComp.getRotation();
	float _speed = _speedComp.getSpeed();

	MovementDirectionComponent _moveDirComp = moveDirMapper.get(entity);
	if (_moveDirComp != null) {
	    EnumSet<Direction> _dirs = _moveDirComp.getDirections();

	    if (_dirs.contains(Direction.N)) { // Up
		_velo.y = -1f;
	    } else if (_dirs.contains(Direction.S)) { // Down
		_velo.y = 1f;
	    } else {
		_velo.y = 0f;
	    }

	    if (_dirs.contains(Direction.W)) { // Left
		_velo.x = -1f;
	    } else if (_dirs.contains(Direction.E)) { // Right
		_velo.x = 1f;
	    } else {
		_velo.x = 0f;
	    }
	}

	if (_angVeloComp != null) {
	    float _angularVelo = _angVeloComp.getAngularVelocity();
	    _rotComponent.add(_angularVelo);
	}

	if (_veloComp != null) {
	    Vector2 _tmpVelo = vectorPool.obtain();
	    _tmpVelo.x = (_velo.x * _speed);
	    _tmpVelo.y = (_velo.y * _speed);

	    MovementTypeComponent _movTypeComp = movTypeMapper.get(entity);
	    if (_rotComp != null) {
		if (_movTypeComp != null && _movTypeComp.getMovementType() == MovementType.RELATIVE) {
		    _tmpVelo.rotate(_rotation + 90f);
		} else {
		    
		}
	    }

	    // Update the physics body
	    if (_bodyComp != null) {
		Body _body = _bodyComp.getBody();
		_body.setLinearVelocity(_tmpVelo);

		// Set last position
		_posComp.setPosition(_body.getPosition());
		_body.setTransform(_body.getPosition(), _rotComp.toRadians());
	    } else {
		Vector2 _tmpPos = vectorPool.obtain();
		_tmpVelo.scl(deltaTime);
		_tmpPos.add(_position);
		_tmpPos.add(_tmpVelo);
		_posComp.setPosition(_tmpPos);
		vectorPool.free(_tmpPos);
	    }

	    vectorPool.free(_tmpVelo);
	}

	// Update children
	AttachedNodeComponent _attachedNodeComp = attachedNodesMapper.get(entity);
	if (_attachedNodeComp != null) {
	    Array<Entity> _attachedNodes = _attachedNodeComp.getAttachedNodes();
	    for (int i = 0; i < _attachedNodes.size; i++) {
		Entity _attachedNode = _attachedNodes.get(i);

		_attachedNode
			.add(engine.createComponent(PositionComponent.class).setPosition(_position))
			.add(engine.createComponent(RotationComponent.class).setRotation(_rotation));

		_bodyComp = bodyMapper.get(_attachedNode);
		if (_bodyComp != null) {
		    _bodyComp.getBody().setTransform(_position.x, _position.y, _rotation);
		}
	    }
	}
    }
}
