package com.omega.amazehing.factory.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.omega.amazehing.factory.Factory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.IdentityComponent;
import com.omega.amazehing.game.entity.component.LoadBalanceComponent;
import com.omega.amazehing.game.entity.component.TaskComponent;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent.ViewerCategory;
import com.omega.amazehing.game.entity.component.ViewerNameComponent;
import com.omega.amazehing.game.entity.component.ai.path.SourceComponent;
import com.omega.amazehing.game.entity.component.ai.path.TargetComponent;
import com.omega.amazehing.game.entity.component.event.CallbackComponent;
import com.omega.amazehing.game.entity.component.event.CallbackComponent.Callback;
import com.omega.amazehing.game.entity.component.item.InventoryOperationComponent;
import com.omega.amazehing.game.entity.component.item.InventoryOperationComponent.InventoryOperation;
import com.omega.amazehing.game.entity.component.item.QuantityComponent;
import com.omega.amazehing.game.entity.component.movement.ImpulseComponent;
import com.omega.amazehing.task.Task;

public class ProcessingFactory implements Factory {

    private EntityEngine engine;

    public ProcessingFactory(EntityEngine engine) {
	this.engine = engine;
    }

    /**
     * Create a pathfinding process.
     * 
     * @param source the entity start point
     * @param target the target end point
     * @return the created entity
     */
    public Entity createPathfinding(Entity source, Entity target) {
	Entity _pathfinding = engine.createEntity();
	_pathfinding
		.add(engine.createComponent(ViewerCategoryComponent.class)
			.setCategory(ViewerCategory.PROCESSING))
		.add(engine.createComponent(ViewerNameComponent.class).setName("Pathfinding"))
		.add(engine.createComponent(SourceComponent.class).setSource(source))
		.add(engine.createComponent(TargetComponent.class).setTarget(target));
	engine.addEntity(_pathfinding);

	return _pathfinding;
    }

    /**
     * Create a process that will impulse a physics body.
     * The source entity must have a BodyComponent.
     * 
     * @param source the entity to apply the impulse
     * @param impulse the vector indicating the direction and the force of the impulse
     * @return the created entity
     */
    public Entity createPhysicsImpulse(Entity source, Vector2 impulse) {
	Entity _impulse = engine.createEntity();
	_impulse.add(engine.createComponent(SourceComponent.class).setSource(source))
		.add(engine.createComponent(ImpulseComponent.class).setImpulse(impulse));
	engine.addEntity(_impulse);

	return _impulse;
    }

    public Entity createLoadBalancedTask(Task task) {
	return createLoadBalancedTask(task, null);
    }

    public Entity createLoadBalancedTask(Task task, Callback callback) {
	Entity _loadBalanceTask = engine.createEntity();
	_loadBalanceTask.add(engine.createComponent(LoadBalanceComponent.class))
		.add(engine.createComponent(TaskComponent.class).setTask(task));

	if (callback != null) {
	    _loadBalanceTask
		    .add(engine.createComponent(CallbackComponent.class).setCallback(callback));
	}
	engine.addEntity(_loadBalanceTask);

	return _loadBalanceTask;
    }

    public Entity createInventoryAdd(long itemId, int quantity, Callback callback) {
	return createInventoryOperation(InventoryOperation.ADD, itemId, quantity, callback);
    }

    public Entity createInventoryAdd(long itemId, int quantity) {
	return createInventoryOperation(InventoryOperation.ADD, itemId, quantity);
    }

    public Entity createInventoryRemove(long itemId, int quantity, Callback callback) {
	return createInventoryOperation(InventoryOperation.REMOVE, itemId, quantity, callback);
    }

    public Entity createInventoryRemove(long itemId, int quantity) {
	return createInventoryOperation(InventoryOperation.REMOVE, itemId, quantity);
    }

    public Entity createInventorySort() {
	Entity _sort = engine.createEntity();
	_sort.add(engine.createComponent(InventoryOperationComponent.class)
		.setOperation(InventoryOperation.SORT));
	engine.addEntity(_sort);

	return _sort;
    }

    private Entity createInventoryOperation(InventoryOperation operation, long itemId, int quantity,
	    Callback callback) {
	Entity _operation = createInventoryOperation(operation, itemId, quantity);
	_operation.add(engine.createComponent(CallbackComponent.class).setCallback(callback));

	return _operation;
    }

    private Entity createInventoryOperation(InventoryOperation operation, long itemId,
	    int quantity) {
	Entity _operation = engine.createEntity();
	_operation
		.add(engine.createComponent(InventoryOperationComponent.class)
			.setOperation(InventoryOperation.SORT))
		.add(engine.createComponent(IdentityComponent.class).setIdentity(itemId))
		.add(engine.createComponent(QuantityComponent.class).setQuantity(quantity));
	engine.addEntity(_operation);

	return _operation;
    }
}