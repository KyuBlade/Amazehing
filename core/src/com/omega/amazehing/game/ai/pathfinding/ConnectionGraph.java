package com.omega.amazehing.game.ai.pathfinding;

import com.badlogic.gdx.ai.pfa.indexed.DefaultIndexedGraph;
import com.badlogic.gdx.utils.Array;

public class ConnectionGraph extends DefaultIndexedGraph<DefaultIndexedNode> {

    private int width;
    private int height;

    public ConnectionGraph(Array<DefaultIndexedNode> nodes, int width, int height) {
	super(nodes);

	this.width = width;
	this.height = height;
    }

    public DefaultIndexedNode getNode(int x, int y) {
	return nodes.get(x * height + y);
    }

    public DefaultIndexedNode getNode(int index) {
	return nodes.get(index);
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }
}