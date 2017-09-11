package com.omega.amazehing.game.ai.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class DefaultIndexedNode implements IndexedNode<DefaultIndexedNode> {

    protected Vector2 position;
    private int index;

    private Array<Connection<DefaultIndexedNode>> connections;

    public DefaultIndexedNode(int x, int y, int graphHeight) {
	position = new Vector2(x, y);

	index = x * graphHeight + y;

	this.connections = new Array<Connection<DefaultIndexedNode>>(4);
    }

    @Override
    public int getIndex() {
	return index;
    }

    @Override
    public Array<Connection<DefaultIndexedNode>> getConnections() {
	return connections;
    }

    public void addConnection(Connection<DefaultIndexedNode> connection) {
	connections.add(connection);
    }

    public Vector2 getPosition() {
	return position;
    }
}