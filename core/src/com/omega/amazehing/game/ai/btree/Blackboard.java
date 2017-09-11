package com.omega.amazehing.game.ai.btree;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.system.PhysicsSystem;

public class Blackboard {

    private FactoryManager factoryManager;
    private EntityEngine engine;
    private World world;

    private Entity currentEntity;

    public Blackboard(EntityEngine engine, FactoryManager factoryManager) {
	this.engine = engine;
	this.factoryManager = factoryManager;

	PhysicsSystem _physSystem = engine.getSystem(PhysicsSystem.class);
	world = _physSystem.getWorld();
    }

    public FactoryManager getFactoryManager() {
	return factoryManager;
    }

    public EntityEngine getEngine() {
	return engine;
    }

    public World getWorld() {
	return world;
    }

    public Entity getCurrentEntity() {
	return currentEntity;
    }

    public void setCurrentEntity(Entity currentEntity) {
	this.currentEntity = currentEntity;
    }
}