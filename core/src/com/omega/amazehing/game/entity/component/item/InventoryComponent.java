package com.omega.amazehing.game.entity.component.item;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool.Poolable;

public class InventoryComponent implements Component, Poolable {

    public enum ItemSlot {
	HEAD, NECKLACE, ARMOR, LEFT_RING, RIGHT_RING, LEFT_HAND, RIGHT_HAND, LEGS, FEET;
    }

    private Array<Entity> stored;
    private ObjectMap<ItemSlot, Entity> equiped;

    public InventoryComponent() {
    }

    public Array<Entity> getStored() {
	return stored;
    }

    public ObjectMap<ItemSlot, Entity> getEquiped() {
	return equiped;
    }

    @Override
    public void reset() {
	stored.clear();
	equiped.clear();
    }
}