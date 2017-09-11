package com.omega.amazehing.game.entity.component.item;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.omega.amazehing.factory.entity.ItemFactory.ItemType;

public class ItemTypeComponent implements Component, Poolable {

    private ItemType type;

    public ItemTypeComponent() {
    }

    public ItemType getType() {
	return type;
    }

    public ItemTypeComponent setType(ItemType type) {
	this.type = type;

	return this;
    }

    public void reset() {
	type = null;
    }
}