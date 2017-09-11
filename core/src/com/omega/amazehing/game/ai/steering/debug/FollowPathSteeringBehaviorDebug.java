package com.omega.amazehing.game.ai.steering.debug;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath.Segment;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.entity.RenderFactory;
import com.omega.amazehing.game.ai.steering.AbstractSteeringBehaviorDebug;
import com.omega.amazehing.game.ai.steering.behavior.FollowPathSteeringBehavior;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.DebugComponent;
import com.omega.amazehing.game.entity.component.DebugComponent.DebugType;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent.ViewerCategory;
import com.omega.amazehing.game.entity.component.render.VerticeComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;

public class FollowPathSteeringBehaviorDebug extends
	AbstractSteeringBehaviorDebug<FollowPathSteeringBehavior> {

    private Entity owner;
    private Entity path;
    private Entity target;

    private RenderFactory renderFactory;

    public FollowPathSteeringBehaviorDebug(EntityEngine engine, FactoryManager factoryManager,
	    FollowPathSteeringBehavior steeringBehavior) {
	super(engine, factoryManager, steeringBehavior);

	renderFactory = factoryManager.getFactory(RenderFactory.class);

	owner = renderFactory.createCircle(ViewerCategory.AI, "FollowPath Owner", Color.BLUE, 0f,
		0f, 0.5f, true, 0, false);
	owner.add(engine.createComponent(DebugComponent.class).setType(DebugType.AI));
	target = renderFactory.createCircle(ViewerCategory.AI, "FollowPath Target", Color.RED, 0f,
		0f, 0.5f, true, 0, false);
	target.add(engine.createComponent(DebugComponent.class).setType(DebugType.AI));
	path = renderFactory.createPolyline(ViewerCategory.AI, "FollowPath Path", Color.GREEN,
		new float[0], 0);
	path.add(engine.createComponent(DebugComponent.class).setType(DebugType.AI));
    }

    @Override
    public void update() {
	// Update owner position
	Steerable<Vector2> _owner = steeringBehavior.getOwner();
	Vector2 _ownerPosition = _owner.getPosition();
	owner.add(engine.createComponent(PositionComponent.class).setPosition(_ownerPosition));

	// Update target position
	Vector2 _targetPosition = steeringBehavior.getInternalTargetPosition();
	target.add(engine.createComponent(PositionComponent.class).setPosition(_targetPosition));
    }

    public void setPath(LinePath<Vector2> linePath) {
	LinePath<Vector2> _path = (LinePath<Vector2>) linePath;
	Array<Segment<Vector2>> _segments = _path.getSegments();
	float[] _vertices = new float[_segments.size * 2 + 2];
	int _idx = 0;
	for (Segment<Vector2> segment : _segments) {
	    Vector2 _position = segment.getBegin();
	    _vertices[_idx++] = _position.x;
	    _vertices[_idx++] = _position.y;
	}

	Segment<Vector2> _lastSegment = _segments.peek();
	Vector2 _lastWaypoint = _lastSegment.getEnd();
	_vertices[_idx++] = _lastWaypoint.x;
	_vertices[_idx++] = _lastWaypoint.y;
	path.add(engine.createComponent(VerticeComponent.class).setVertices(_vertices));
    }

    @Override
    public void onRemoved() {
	engine.removeEntity(owner);
	engine.removeEntity(target);
	engine.removeEntity(path);
    }
}