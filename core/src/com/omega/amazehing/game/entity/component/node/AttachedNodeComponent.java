package com.omega.amazehing.game.entity.component.node;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

public class AttachedNodeComponent implements Component, Poolable {

    private Array<Entity> attachedNodes = new Array<Entity>();

    public AttachedNodeComponent() {
    }

    public AttachedNodeComponent addNode(Entity node) {
	attachedNodes.add(node);

	return this;
    }

    public AttachedNodeComponent removeNode(Entity node) {
	attachedNodes.removeValue(node, true);

	return this;
    }

    public Array<Entity> getAttachedNodes() {
	return attachedNodes;
    }

    @Override
    public void reset() {
	attachedNodes.clear();
    }
}