package com.omega.amazehing.game.ai.btree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.utils.ObjectMap;
import com.omega.amazehing.Assets;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.game.entity.EntityEngine;

public class BehaviorTreeManager {

    private ObjectMap<String, BehaviorTree<Blackboard>> repository;
    private BehaviorTreeParser<Blackboard> parser;
    private Blackboard blackboard;

    public BehaviorTreeManager(EntityEngine engine, FactoryManager factoryManager) {
	repository = new ObjectMap<String, BehaviorTree<Blackboard>>();
	parser = new BehaviorTreeParser<Blackboard>(BehaviorTreeParser.DEBUG_NONE);
	blackboard = new Blackboard(engine, factoryManager);

	registerArchetypeTree(Assets.BehaviorTrees.CLOSE_ENCOUTER);
    }

    private void registerArchetypeTree(String treeReference) {
	BehaviorTree<Blackboard> _behaviorTree = parser.parse(Gdx.files.internal(treeReference),
		blackboard);
	repository.put(treeReference, _behaviorTree);
    }

    public BehaviorTree<Blackboard> getBehaviorTree(String treeReference) {
	return repository.get(treeReference);
    }

    public Blackboard getBlackboard() {
	return blackboard;
    }
}