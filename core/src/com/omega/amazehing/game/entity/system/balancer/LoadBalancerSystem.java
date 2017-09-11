package com.omega.amazehing.game.entity.system.balancer;

import java.util.concurrent.LinkedBlockingQueue;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.omega.amazehing.Constants;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.LoadBalanceComponent;
import com.omega.amazehing.game.entity.component.TaskComponent;
import com.omega.amazehing.game.entity.component.event.CallbackComponent;
import com.omega.amazehing.game.entity.component.event.CallbackComponent.Callback;
import com.omega.amazehing.task.Task;
import com.omega.amazehing.util.Utils;

public class LoadBalancerSystem extends EntitySystem {

    private final long maxLoadTime = ((long) ((1f / 30f) * 1000));
    private float loadTime;
    private long startLoadTime;
    private long totalLoadTime;

    private LinkedBlockingQueue<Entity> loadQueue;

    private static final ComponentMapper<TaskComponent> taskMapper = ComponentMapperHandler
	    .getTaskMapper();
    private static final ComponentMapper<CallbackComponent> callbackMapper = ComponentMapperHandler
	    .getCallbackMapper();

    public LoadBalancerSystem() {
	super(Constants.Game.System.LOAD_BALANCER_SYSTEM_PRIORITY);

	loadQueue = new LinkedBlockingQueue<Entity>();
    }

    @Override
    public void addedToEngine(Engine engine) {
	engine.addEntityListener(Family.all(LoadBalanceComponent.class).get(),
		new EntityListener() {

		    @Override
		    public void entityRemoved(Entity entity) {

		    }

		    @Override
		    public void entityAdded(Entity entity) {
			loadQueue.add(entity);
		    }
		});
    }

    @Override
    public void update(float deltaTime) {
	startLoadTime = System.currentTimeMillis();
	totalLoadTime = 0L;
	loadTime = 0L;
	int _i = 0;
	while (totalLoadTime < maxLoadTime && !loadQueue.isEmpty()) {
	    _i++;
	    Entity _entity = loadQueue.poll();
	    TaskComponent _taskComp = taskMapper.get(_entity);
	    CallbackComponent _callbackComp = callbackMapper.get(_entity);
	    Task _task = _taskComp.getTask();
	    try {
		_task.run();

		if (_callbackComp != null) {
		    Callback _callback = _callbackComp.getCallback();
		    _callback.call();
		}
	    } catch (Exception e) {
		Utils.reportFatalException(e);
	    }

	    getEngine().removeEntity(_entity);

	    loadTime = System.currentTimeMillis() - startLoadTime;
	    totalLoadTime += loadTime;

	    // Gdx.app.debug("LoadBalancer",
	    // "Elapsed : " + loadTime + ", Total Elapsed : " + totalLoadTime + ", Max load time : " + maxLoadTime);
	}
	// Gdx.app.debug("Tasks executed", "" + _i);
    }
}