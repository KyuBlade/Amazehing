package com.omega.amazehing.game.ai.btree.task;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.entity.ProcessingFactory;
import com.omega.amazehing.game.ai.btree.Blackboard;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.ai.path.LastPathfindingComponent;
import com.omega.amazehing.game.entity.component.ai.path.TargetComponent;

public class PursuitTask extends LeafTask<Blackboard> {

    private long pahthfindingInterval = 25L;
    private static final ComponentMapper<TargetComponent> targetMapper = ComponentMapperHandler
	    .getTargetMapper();
    private static final ComponentMapper<LastPathfindingComponent> lastPathfindingMapper = ComponentMapperHandler
	    .getLastPathfindingMapper();

    @Override
    public void run(Blackboard object) {
	EntityEngine _engine = object.getEngine();
	FactoryManager _factoryManager = object.getFactoryManager();
	ProcessingFactory _processingFactory = _factoryManager.getFactory(ProcessingFactory.class);
	Entity _owner = object.getCurrentEntity();

	LastPathfindingComponent _lastPathfindingComp = lastPathfindingMapper.get(_owner);
	if (_lastPathfindingComp == null || System.currentTimeMillis() - _lastPathfindingComp
		.getLast() >= pahthfindingInterval) { // No path to find
	    TargetComponent _targetComp = targetMapper.get(_owner);
	    Entity _target = _targetComp.getTarget();

	    _processingFactory.createPathfinding(_owner, _target);
	    _owner.add(_engine.createComponent(LastPathfindingComponent.class).setLast(
		    System.currentTimeMillis()));

	    fail();
	} else { // Pathfinding is processing or being processed
	    success();
	}
    }

    @Override
    protected Task<Blackboard> copyTo(Task<Blackboard> task) {
	return task;
    }
}