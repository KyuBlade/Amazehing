package com.omega.amazehing.game.entity.system.ai;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.omega.amazehing.Constants;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.game.ai.btree.BehaviorTreeManager;
import com.omega.amazehing.game.ai.btree.Blackboard;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.ai.btree.BehaviorTreeComponent;

public class BehaviorTreeSystem extends IteratingSystem {

    private FactoryManager factoryManager;
    private BehaviorTreeManager manager;

    private static final ComponentMapper<BehaviorTreeComponent> behaviorTreeMapper = ComponentMapperHandler
	    .getBehaviorTreeMapper();

    @SuppressWarnings("unchecked")
    public BehaviorTreeSystem(FactoryManager factoryManager) {
	super(Family.all(BehaviorTreeComponent.class).get(),
		Constants.Game.System.BEHAVIOR_TREE_SYSTEM_PRIORITY);

	this.factoryManager = factoryManager;
    }

    @Override
    public void addedToEngine(Engine engine) {
	super.addedToEngine(engine);

	manager = new BehaviorTreeManager((EntityEngine) engine, factoryManager);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
	BehaviorTreeComponent _behaviorTreeComp = behaviorTreeMapper.get(entity);
	BehaviorTree<Blackboard> _behaviorTree = _behaviorTreeComp.getBehaviorTree();

	manager.getBlackboard().setCurrentEntity(entity);
	_behaviorTree.step();
    }

    public BehaviorTreeManager getManager() {
	return manager;
    }
}