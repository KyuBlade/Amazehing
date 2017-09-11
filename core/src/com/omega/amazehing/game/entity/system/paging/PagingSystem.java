package com.omega.amazehing.game.entity.system.paging;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.omega.amazehing.Constants;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.entity.RenderFactory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.paging.PagedComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;

public class PagingSystem extends EntitySystem {

    private EntityEngine engine;
    private RenderFactory renderFactory;

    private Family family;

    private Camera camera;
    private float patchesSize;

    private Array<Entity> toRemove;
    private Array<Entity> toAdd;

    private ObjectMap<Vector2, PagingPatch> patches;
    private Array<PagingPatch> activePatches;
    private Array<PagingPatch> lastActivePatches;
    private Array<PagingListener> listeners;

    private Array<PagingPatch> unloadQueue;

    private Vector2 tmpVect = new Vector2();

    private static final ComponentMapper<PositionComponent> positionMapper = ComponentMapperHandler
	    .getPositionMapper();

    public PagingSystem(FactoryManager factoryManager, Camera camera) {
	this(factoryManager, camera, Family.all(PagedComponent.class).get(),
		Constants.Game.System.PAGING_SYSTEM_PRIORITY);
    }

    public PagingSystem(FactoryManager factoryManager, Camera camera, Family family, int priority) {
	super(priority);

	this.family = family;
	this.camera = camera;
	this.patchesSize = Constants.Game.PAGING_PATCHES_SIZE;

	toAdd = new Array<Entity>();
	toRemove = new Array<Entity>();
	activePatches = new Array<PagingPatch>(9);
	patches = new ObjectMap<Vector2, PagingPatch>();
	lastActivePatches = new Array<PagingPatch>(9);
	listeners = new Array<PagingListener>();
	unloadQueue = new Array<PagingPatch>();

	renderFactory = factoryManager.getFactory(RenderFactory.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
	this.engine = (EntityEngine) engine;

	addListener(new DebugListener(this.engine, renderFactory));
	addListener(new DynamicLoadingListener(this.engine));

	engine.addEntityListener(family, new EntityListener() {

	    @Override
	    public void entityRemoved(Entity entity) {
		removePagedEntity(entity);
	    }

	    @Override
	    public void entityAdded(Entity entity) {
		// addPagedEntity(entity);
		toAdd.add(entity);
	    }
	});
    }

    @Override
    public void update(float deltaTime) {
	tmpVect.set(MathUtils.floor(camera.position.x / patchesSize),
		MathUtils.floor(camera.position.y / patchesSize));

	lastActivePatches.clear();
	lastActivePatches.addAll(activePatches);
	activePatches.clear();

	activePatches.add(getPatch(tmpVect)); // Center
	activePatches.add(getPatch(tmpVect.sub(1f, 1f))); // Top Left
	activePatches.add(getPatch(tmpVect.add(1f, 0f))); // Top Center
	activePatches.add(getPatch(tmpVect.add(1f, 0f))); // Top Right
	activePatches.add(getPatch(tmpVect.add(0f, 1f))); // Center Right
	activePatches.add(getPatch(tmpVect.add(0f, 1f))); // Bottom Right
	activePatches.add(getPatch(tmpVect.sub(1f, 0f))); // Bottom Center
	activePatches.add(getPatch(tmpVect.sub(1f, 0f))); // Bottom Left
	activePatches.add(getPatch(tmpVect.sub(0f, 1f))); // Center Left

	for (PagingPatch patch : lastActivePatches) {
	    if (!activePatches.contains(patch, false)) {
		patch.setActive(false);
		for (PagingListener _listener : listeners) {
		    _listener.onRemoved(PagingSystem.this, patch);
		}
	    }
	}
	for (PagingPatch patch : activePatches) {
	    if (!lastActivePatches.contains(patch, false)) {
		patch.setActive(true);
		for (PagingListener _listener : listeners) {
		    _listener.onAdded(PagingSystem.this, patch);
		}
	    }
	}

	for (Entity entity : toRemove) {
	    removePagedEntity(entity);
	}
	toRemove.clear();

	for (Entity entity : toAdd) {
	    addPagedEntity(entity);
	}
	toAdd.clear();

	int _i = 0;
	for (PagingPatch patch : unloadQueue) {
	    if (patch.isLoaded()) {
		for (Entity entity : patch.getPagedEntities()) {
		    engine.removeEntity(entity);
		}

		unloadQueue.removeIndex(_i);
		patch.setLoaded(false);
	    }

	    _i++;
	}
    }

    protected void unloadPatch(PagingPatch patch) {
	unloadQueue.add(patch);
    }

    private PagingPatch getPatch(Vector2 position) {
	PagingPatch _patch = patches.get(position);
	if (_patch == null) {
	    _patch = new PagingPatch(engine, position.cpy());
	    patches.put(_patch.getPosition(), _patch);
	}

	return _patch;
    }

    public void addPagedEntity(Entity entity) {
	PositionComponent _posComp = positionMapper.get(entity);
	Vector2 _position = _posComp.getPosition();
	PagingPatch _patch = getPatch(tmpVect.set(MathUtils.floor(_position.x / patchesSize),
		MathUtils.floor(_position.y / patchesSize)));
	_patch.addEntity(entity);
    }

    public void removePagedEntity(Entity entity) {
	PositionComponent _posComp = positionMapper.get(entity);
	Vector2 _position = _posComp.getPosition();
	PagingPatch _patch = getPatch(tmpVect.set(MathUtils.floor(_position.x / patchesSize),
		MathUtils.floor(_position.y / patchesSize)));
	_patch.removeEntity(entity);
    }

    public Array<PagingListener> getListeners() {
	return listeners;
    }

    public void addListener(PagingListener listener) {
	listeners.add(listener);
    }

    public void removeListener(PagingListener listener) {
	listeners.removeValue(listener, false);
    }

    public float getPatchesSize() {
	return patchesSize;
    }

    public Array<PagingPatch> getActivePatches() {
	return activePatches;
    }
}