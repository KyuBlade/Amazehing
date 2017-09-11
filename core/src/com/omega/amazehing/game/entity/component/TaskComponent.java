package com.omega.amazehing.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.omega.amazehing.task.Task;

public class TaskComponent implements Component, Poolable {

    private Task task;

    public TaskComponent() {
    }

    public Task getTask() {
	return task;
    }

    public TaskComponent setTask(Task task) {
	this.task = task;

	return this;
    }

    @Override
    public void reset() {
	task = null;
    }
}