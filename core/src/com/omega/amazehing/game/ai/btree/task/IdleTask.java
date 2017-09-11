package com.omega.amazehing.game.ai.btree.task;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.omega.amazehing.game.ai.btree.Blackboard;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.ai.steering.SteeringComponent;

public class IdleTask extends LeafTask<Blackboard> {

    private static final ComponentMapper<SteeringComponent> steeringMapper = ComponentMapperHandler
	    .getSteeringMapper();

    @Override
    public void run(Blackboard object) {
	Gdx.app.debug("BTree", "Idle");

	success();
    }

    @Override
    protected Task<Blackboard> copyTo(Task<Blackboard> task) {
	return task;
    }
}