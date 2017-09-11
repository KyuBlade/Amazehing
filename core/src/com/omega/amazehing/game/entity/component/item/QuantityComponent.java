package com.omega.amazehing.game.entity.component.item;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class QuantityComponent implements Component, Poolable {

    private int quantity;

    public QuantityComponent() {
    }

    public int getQuantity() {
	return quantity;
    }

    public QuantityComponent setQuantity(int quantity) {
	this.quantity = quantity;

	return this;
    }

    @Override
    public void reset() {
	quantity = 0;
    }
}