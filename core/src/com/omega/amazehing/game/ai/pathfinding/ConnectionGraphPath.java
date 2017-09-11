package com.omega.amazehing.game.ai.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ConnectionGraphPath extends DefaultGraphPath<Connection<DefaultIndexedNode>> implements
	Poolable {

    /**
     * Create an array of waypoints to use for follow path steering behavior.
     * 
     * @return the array of waypoints
     */
    public Array<Vector2> createWaypoints() {
	Array<Vector2> _waypoints = new Array<Vector2>(nodes.size);
	for (Connection<DefaultIndexedNode> connection : nodes) {
	    DefaultIndexedNode _node = connection.getToNode();
	    _waypoints.add(_node.position);
	}

	return _waypoints;
    }

    public void reset() {
	nodes.clear();
    }
}