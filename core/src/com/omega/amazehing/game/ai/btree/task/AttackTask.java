package com.omega.amazehing.game.ai.btree.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.omega.amazehing.game.ai.btree.Blackboard;

public class AttackTask extends LeafTask<Blackboard> {

    public void run(Blackboard object) {
	success();
    }

    @Override
    protected Task<Blackboard> copyTo(Task<Blackboard> task) {
	return task;
    }
}