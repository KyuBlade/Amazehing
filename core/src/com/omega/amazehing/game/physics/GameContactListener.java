package com.omega.amazehing.game.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.trigger.TriggerActivationComponent;

public class GameContactListener implements ContactListener {

    private EntityEngine engine;

    private ComponentMapper<TriggerActivationComponent> triggerMapper = ComponentMapperHandler.getTriggerActivationMapper();

    public GameContactListener(EntityEngine engine) {
	this.engine = engine;
    }

    @Override
    public void beginContact(Contact contact) {
	Fixture _fixtureA = contact.getFixtureA();
	Fixture _fixtureB = contact.getFixtureB();

	Entity _entityA = (Entity) _fixtureA.getBody().getUserData();
	Entity _entityB = (Entity) _fixtureB.getBody().getUserData();

	if (_entityA != null && _entityB != null) {
	    handleBeginContact(_entityA, _entityB);
	    handleBeginContact(_entityB, _entityA);
	}
    }

    @Override
    public void endContact(Contact contact) {
	Fixture _fixtureA = contact.getFixtureA();
	Fixture _fixtureB = contact.getFixtureB();

	Entity _entityA = (Entity) _fixtureA.getBody().getUserData();
	Entity _entityB = (Entity) _fixtureB.getBody().getUserData();

	if (_entityA != null && _entityB != null) {
	    handleEndContact(_entityA, _entityB);
	    handleEndContact(_entityB, _entityA);
	}
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    private void handleBeginContact(Entity e1, Entity e2) {
	if (triggerMapper.has(e1)) {
	    e1.add(engine.createComponent(TriggerActivationComponent.class).setTarget(e2).setActive(true));
	}
    }

    private void handleEndContact(Entity e1, Entity e2) {
	if (triggerMapper.has(e1)) {
	    e1.add(engine.createComponent(TriggerActivationComponent.class).setTarget(null).setActive(false));
	}
    }

}
