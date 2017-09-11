package com.omega.amazehing.game.ai.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;

public class ManathanHeuristic implements Heuristic<DefaultIndexedNode> {

    @Override
    public float estimate(DefaultIndexedNode startNode, DefaultIndexedNode endNode) {
	Vector2 _startPosition = startNode.getPosition();
	Vector2 _endPosition = endNode.getPosition();

	return Math.abs(_endPosition.x - _startPosition.x) + Math.abs(_endPosition.y - _startPosition.y);
    }
}
