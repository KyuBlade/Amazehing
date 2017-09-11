package com.omega.amazehing.game.entity.system.ai;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.omega.amazehing.Constants;
import com.omega.amazehing.exception.PathNotFoundException;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.NamedThreadFactory;
import com.omega.amazehing.factory.entity.SteeringFactory;
import com.omega.amazehing.game.ai.pathfinding.ConnectionGraph;
import com.omega.amazehing.game.ai.pathfinding.ConnectionGraphPath;
import com.omega.amazehing.game.ai.pathfinding.DefaultIndexedNode;
import com.omega.amazehing.game.ai.pathfinding.ManathanHeuristic;
import com.omega.amazehing.game.ai.steering.AbstractSteeringAgent;
import com.omega.amazehing.game.ai.steering.behavior.FollowPathSteeringBehavior;
import com.omega.amazehing.game.ai.steering.behavior.PrioritySteeringBehavior;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.ai.path.PathComponent;
import com.omega.amazehing.game.entity.component.ai.path.PathfindingComponent;
import com.omega.amazehing.game.entity.component.ai.path.SourceComponent;
import com.omega.amazehing.game.entity.component.ai.path.TargetComponent;
import com.omega.amazehing.game.entity.component.ai.steering.SteeringComponent;
import com.omega.amazehing.game.entity.component.movement.MovementDirectionComponent;
import com.omega.amazehing.game.entity.component.movement.MovementTypeComponent;
import com.omega.amazehing.game.entity.component.movement.MovementTypeComponent.MovementType;
import com.omega.amazehing.game.entity.component.movement.VelocityComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.level.generator.cell.Direction;

public class PathfindingSystem extends IteratingSystem implements Disposable {

    private static final Logger logger = LoggerFactory.getLogger(PathfindingSystem.class);

    private EntityEngine engine;
    private SteeringFactory steeringFactory;

    private ConnectionGraphPath path;
    private ConnectionGraph graph;
    private IndexedAStarPathFinder<DefaultIndexedNode> pathfinder;
    private ExecutorService executor;

    private static final ComponentMapper<SourceComponent> sourceMapper = ComponentMapperHandler
	    .getSourceMapper();
    private static final ComponentMapper<TargetComponent> targetMapper = ComponentMapperHandler
	    .getTargetMapper();
    private static final ComponentMapper<PathfindingComponent> pathfindingMapper = ComponentMapperHandler
	    .getPathfindingMapper();
    private static final ComponentMapper<PathComponent> pathMapper = ComponentMapperHandler
	    .getPathMapper();
    private static final ComponentMapper<SteeringComponent> steeringMapper = ComponentMapperHandler
	    .getSteeringMapper();
    private static final ComponentMapper<PositionComponent> positionMapper = ComponentMapperHandler
	    .getPositionMapper();

    @SuppressWarnings("unchecked")
    public PathfindingSystem(FactoryManager factoryManager) {
	super(Family.all(SourceComponent.class, TargetComponent.class).get(),
		Constants.Game.System.PATHFINDING_SYSTEM_PRIORITY);

	steeringFactory = factoryManager.getFactory(SteeringFactory.class);
	executor = Executors.newSingleThreadExecutor(new NamedThreadFactory("Pathfinding Thread"));
	path = new ConnectionGraphPath();
    }

    protected void processEntity(Entity entity, float deltaTime) {
	PathfindingComponent _pathfindingComp = pathfindingMapper.get(entity);
	Future<ConnectionGraphPath> _future;
	if (_pathfindingComp == null) { // Need to launch pathfinding
	    SourceComponent _sourceComp = sourceMapper.get(entity);
	    TargetComponent _targetComp = targetMapper.get(entity);
	    Entity _sourceEntity = _sourceComp.getSource();
	    Entity _targetEntity = _targetComp.getTarget();
	    PositionComponent _sourcePosComp = positionMapper.get(_sourceEntity);
	    PositionComponent _targetPosComp = positionMapper.get(_targetEntity);
	    Vector2 _sourcePos = _sourcePosComp.getPosition();
	    Vector2 _targetPos = _targetPosComp.getPosition();

	    path.clear();
	    _future = findPath(_sourcePos, _targetPos, path);
	    entity.add(engine.createComponent(PathfindingComponent.class).setFuture(_future));
	} else { // Pathfinding launched
	    _future = _pathfindingComp.getFuture();
	    if (_future.isDone()) { // Path found
		SourceComponent _sourceComp = sourceMapper.get(entity);
		Entity _source = _sourceComp.getSource();
		SteeringComponent _steeringComp = steeringMapper.get(_source);
		AbstractSteeringAgent _agent = _steeringComp.getSteeringAgent();
		SteeringBehavior<Vector2> _behavior = _agent.getSteeringBehavior();

		if (_behavior instanceof PrioritySteeringBehavior) {
		    PrioritySteeringBehavior _priorityBehavior = (PrioritySteeringBehavior) _behavior;
		    try {
			ConnectionGraphPath _path = _future.get();
			FollowPathSteeringBehavior _followPathBehavior = _priorityBehavior
				.get(FollowPathSteeringBehavior.class);

			if (_followPathBehavior == null) { // Create new path behavior
			    _followPathBehavior = steeringFactory.createFollowPathSteering(_source,
				    _path.createWaypoints());
			    _priorityBehavior.add(_followPathBehavior);
			} else { // Update current path behavior
			    try {
				LinePath<Vector2> _followPath = (LinePath<Vector2>) _followPathBehavior
					.getPath();
				_followPath.createPath(_path.createWaypoints());
				_followPathBehavior.setPath(_followPath);
			    } catch (IllegalArgumentException e) {
			    }
			}
		    } catch (Exception e) {
			logger.error("Unable to find path.", e);
		    }
		}
		engine.removeEntity(entity);
	    }
	}
    }

    @Override
    public void addedToEngine(Engine engine) {
	super.addedToEngine(engine);

	this.engine = (EntityEngine) engine;
    }

    public void init(int width, int height) {
	Array<DefaultIndexedNode> _nodes = new Array<DefaultIndexedNode>(width * height);
	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < height; y++) {
		_nodes.add(new DefaultIndexedNode(x, y, height));
	    }
	}

	graph = new ConnectionGraph(_nodes, width, height);
	pathfinder = new IndexedAStarPathFinder<DefaultIndexedNode>(graph, true);
	executor = Executors.newCachedThreadPool(new NamedThreadFactory("Pathfinding Thread"));
    }

    public Future<ConnectionGraphPath> findPath(Vector2 start, Vector2 end, ConnectionGraphPath path) {
	return findPath(start.x, start.y, end.x, end.y, path);
    }

    public Future<ConnectionGraphPath> findPath(float startX, float startY, float endX, float endY,
	    ConnectionGraphPath path) {
	return findPath(MathUtils.floor(startX), MathUtils.floor(startY), MathUtils.floor(endX),
		MathUtils.floor(endY), path);
    }

    public Future<ConnectionGraphPath> findPath(final int startX, final int startY, final int endX,
	    final int endY, final ConnectionGraphPath path) {
	Callable<ConnectionGraphPath> _callable = new Callable<ConnectionGraphPath>() {

	    @Override
	    public ConnectionGraphPath call() throws Exception {
		DefaultIndexedNode _startNode = graph.getNode(startX, startY);
		DefaultIndexedNode _endNode = graph.getNode(endX, endY);

		if (_startNode == null || _endNode == null) {
		    throw new NullPointerException("Start node or end end must not be null.");
		}

		if (!pathfinder.searchConnectionPath(_startNode, _endNode, new ManathanHeuristic(),
			path)) {
		    throw new PathNotFoundException();
		}

		return path;
	    }
	};

	return executor.submit(_callable);
    }

    public ConnectionGraph getGraph() {
	return graph;
    }

    @Override
    public void dispose() {
	if (executor != null) {
	    executor.shutdownNow();
	}
    }
}