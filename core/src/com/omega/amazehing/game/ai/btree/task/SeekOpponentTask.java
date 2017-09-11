package com.omega.amazehing.game.ai.btree.task;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectSet;
import com.omega.amazehing.game.ai.btree.Blackboard;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.ai.path.TargetComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;

public class SeekOpponentTask extends LeafTask<Blackboard> {

    private static final OpponentQueryCallback callback = new OpponentQueryCallback();
    private static final OpponentRayCastCallback rayCastCallback = new OpponentRayCastCallback();

    private static final ComponentMapper<PositionComponent> positionMapper = ComponentMapperHandler
	    .getPositionMapper();
    private static final ComponentMapper<TargetComponent> targetMapper = ComponentMapperHandler
	    .getTargetMapper();

    @Override
    public void run(Blackboard object) {
	World _world = object.getWorld();
	Entity _entity = object.getCurrentEntity();
	TargetComponent _targetComp = targetMapper.get(_entity);

	if (_targetComp.getTarget() != null) {
	    success();

	    return;
	}

	PositionComponent _posComp = positionMapper.get(_entity);
	Vector2 _position = _posComp.getPosition();
	float _radius = 10f;

	_world.QueryAABB(callback, _position.x - _radius, _position.y - _radius,
		_position.x + _radius, _position.y + _radius);

	ObjectSet<Body> _bodies = callback.bodies;
	for (Body body : _bodies) {
	    _world.rayCast(rayCastCallback, _position, body.getPosition());
	    if (!rayCastCallback.hit) {
		_bodies.remove(body);
	    }
	}

	// Get closest body
	Body _closestBody = null;
	float _closestDst = 0f;
	for (Body body : _bodies) {
	    if (_closestBody == null) {
		_closestBody = body;
		_closestDst = _position.dst(_closestBody.getPosition());

		continue;
	    }

	    float _bodyDst = _position.dst(body.getPosition());
	    if (_bodyDst < _closestDst) {
		_closestBody = body;
		_closestDst = _bodyDst;
	    }
	}

	callback.bodies.clear();
	rayCastCallback.hit = false;

	if (_closestBody == null) {
	    fail();
	} else {
	    Entity _target = (Entity) _closestBody.getUserData();
	    _targetComp.setTarget(_target);

	    success();
	}
    }

    @Override
    protected Task<Blackboard> copyTo(Task<Blackboard> task) {
	return task;
    }

    static class OpponentQueryCallback implements QueryCallback {

	protected ObjectSet<Body> bodies;

	public OpponentQueryCallback() {
	    bodies = new ObjectSet<Body>();
	}

	@Override
	public boolean reportFixture(Fixture fixture) {
	    Body _body = fixture.getBody();
	    Entity _entity = (Entity) _body.getUserData();
	    if (_entity != null) {
		bodies.add(_body);
	    }

	    return true;
	}
    }

    static class OpponentRayCastCallback implements RayCastCallback {

	protected boolean hit;

	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal,
		float fraction) {
	    Gdx.app.debug("RayCast", "Hit " + fixture.getBody());
	    hit = true;

	    return 0;
	}
    }
}