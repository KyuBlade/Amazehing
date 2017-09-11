package com.omega.amazehing.factory;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.omega.amazehing.factory.body.shape.RectangleShape;
import com.omega.amazehing.factory.body.shape.Shape;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.system.PhysicsSystem;
import com.omega.amazehing.game.level.generator.wall.Wall;
import com.omega.amazehing.game.physics.CollisionFilter;

/**
 * Factory to create physics bodies.
 */
public class BodyFactory implements Factory {

    private World world;

    public BodyFactory(EntityEngine engine) {
	PhysicsSystem _physSystem = engine.getSystem(PhysicsSystem.class);
	world = _physSystem.getWorld();
    }

    /**
     * Create player body.
     * 
     * @param x
     * @param y
     * @return the created body
     */
    public Body createPlayer(float x, float y) {
	BodyDef _bodyDef = new BodyDef();
	_bodyDef.type = BodyType.DynamicBody;
	_bodyDef.position.set(x, y);

	Body _body = world.createBody(_bodyDef);
	_body.setFixedRotation(true);

	CircleShape _circle = new CircleShape();
	_circle.setRadius(0.5f);

	FixtureDef _fixtureDef = new FixtureDef();
	_fixtureDef.shape = _circle;
	_fixtureDef.density = 0f;
	_fixtureDef.friction = 0f;
	_fixtureDef.restitution = 0f;

	_body.createFixture(_fixtureDef);

	_circle.dispose();

	return _body;
    }

    /**
     * Create an enemy body.
     * 
     * @param x
     * @param y
     * @param radius
     * @return the created body
     */
    public Body createEnemy(float x, float y, float radius) {
	BodyDef _bodyDef = new BodyDef();
	_bodyDef.type = BodyType.DynamicBody;
	_bodyDef.position.set(x, y);

	Body _body = world.createBody(_bodyDef);
	_body.setFixedRotation(true);

	CircleShape _circle = new CircleShape();
	_circle.setRadius(radius);

	FixtureDef _fixtureDef = new FixtureDef();
	_fixtureDef.shape = _circle;
	_fixtureDef.density = 0f;
	_fixtureDef.friction = 0f;
	_fixtureDef.restitution = 0f;

	_body.createFixture(_fixtureDef);

	_circle.dispose();

	return _body;
    }

    /**
     * Create a wall body from a wall object.
     * 
     * @param wall the wall object to create
     * @return the created body
     */
    public Body createWall(Wall wall) {
	return createWall(wall.x, wall.y, wall.width, wall.height, wall.getRotation());
    }

    /**
     * Create a wall body.
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param rotation
     * @return the created body
     */
    public Body createWall(float x, float y, float width, float height, float rotation) {
	BodyDef _wallBodyDef = new BodyDef();
	_wallBodyDef.type = BodyType.StaticBody;

	_wallBodyDef.position.set(x, y);

	Body _body = world.createBody(_wallBodyDef);

	float _halfWidth = width * 0.5f;
	float _halfHeight = height * 0.5f;
	PolygonShape _shapeBox = new PolygonShape();
	_shapeBox.setAsBox(_halfWidth, _halfHeight, Vector2.Zero,
		rotation * MathUtils.degreesToRadians);

	Fixture _fixture = _body.createFixture(_shapeBox, 0f);
	// _fixture.getFilterData().categoryBits = CollisionFilter.PLAYER_FILTER;

	return _body;
    }

    /**
     * Create a trigger sensor body.
     * 
     * @param x
     * @param y
     * @param shape the detection shape
     * @return the created sensor body
     */
    public Body createTrigger(float x, float y, Shape shape) {
	BodyDef _bodyDef = new BodyDef();

	Body _triggerBody = world.createBody(_bodyDef);

	FixtureDef _fixDef = new FixtureDef();
	_fixDef.isSensor = true;
	if (shape instanceof RectangleShape) {
	    RectangleShape _castedShape = (RectangleShape) shape;
	    PolygonShape _physicsShape = new PolygonShape();
	    _physicsShape.setAsBox(
		    _castedShape.getWidth() * 0.5f,
		    _castedShape.getHeight() * 0.5f,
		    new Vector2(x + _castedShape.getWidth() * 0.5f - 0.5f, y - _castedShape
			    .getHeight()), 0f);
	    _fixDef.shape = _physicsShape;
	} else if (shape instanceof com.omega.amazehing.factory.body.shape.CircleShape) {
	    com.omega.amazehing.factory.body.shape.CircleShape _castedShape = (com.omega.amazehing.factory.body.shape.CircleShape) shape;
	    CircleShape _physicsShape = new CircleShape();
	    _physicsShape.setPosition(new Vector2(x, y));
	    _physicsShape.setRadius(_castedShape.getRadius());
	    _fixDef.shape = _physicsShape;
	}

	Filter _collisionFilter = _fixDef.filter;
	_collisionFilter.categoryBits = CollisionFilter.TRIGGER_FILTER;
	_collisionFilter.maskBits = CollisionFilter.PLAYER_FILTER;

	_triggerBody.createFixture(_fixDef);
	_fixDef.shape.dispose();

	return _triggerBody;
    }

    public void destroyBody(Body body) {
	if (body == null) {
	    throw new NullPointerException("Body should not be null.");
	}

	world.destroyBody(body);
    }
}