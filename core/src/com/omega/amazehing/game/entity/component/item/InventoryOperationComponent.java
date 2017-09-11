package com.omega.amazehing.game.entity.component.item;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class InventoryOperationComponent implements Component, Poolable {

    public enum InventoryOperation {
	ADD, REMOVE, SORT;
    }

    private InventoryOperation operation;

    public InventoryOperationComponent() {
    }

    public InventoryOperation getOperation() {
	return operation;
    }

    public InventoryOperationComponent setOperation(InventoryOperation operation) {
	this.operation = operation;

	return this;
    }

    @Override
    public void reset() {
	operation = null;
    }
}