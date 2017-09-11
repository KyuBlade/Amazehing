package com.omega.amazehing.game.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.omega.amazehing.Constants;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.physics.GameContactListener;

public class PhysicsSystem extends IntervalSystem implements Disposable {

    private World world;

    public PhysicsSystem() {
	super(1f / 60f, Constants.Game.System.PHYSICS_SYSTEM_PRIORITY);
    }

    @Override
    public void addedToEngine(Engine engine) {
	world = new World(new Vector2(0f, 0f), true);
	world.setContactListener(new GameContactListener((EntityEngine) engine));
    }

    @Override
    protected void updateInterval() {
	world.step(1f / 60f, 6, 2);
    }

    public World getWorld() {
	return world;
    }

    @Override
    public void dispose() {
	if (world != null) {
	    world.dispose();
	}
    }
}