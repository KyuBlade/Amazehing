package com.omega.amazehing.game.entity.component.ai.btree;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.omega.amazehing.game.ai.btree.Blackboard;

public class BehaviorTreeComponent implements Component, Poolable {

    private BehaviorTree<Blackboard> behaviorTree;

    public BehaviorTreeComponent() {
    }

    public BehaviorTree<Blackboard> getBehaviorTree() {
	return behaviorTree;
    }

    public BehaviorTreeComponent setBehaviorTree(BehaviorTree<Blackboard> behaviorTree) {
	this.behaviorTree = behaviorTree;

	return this;
    }

    @Override
    public void reset() {
	behaviorTree = null;
    }
}