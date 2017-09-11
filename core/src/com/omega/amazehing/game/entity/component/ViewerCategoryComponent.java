package com.omega.amazehing.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ViewerCategoryComponent implements Component, Poolable {

    public enum ViewerCategory {
	ANY("Any"), SPRITE("Sprites"), PAGING_DEBUG("Paging Debug"), ROOM_DEBUG("Room Debug"), AI(
		"Ai"), INTERACTIVE_OBJECT("Interactive Objects"), ITEM("Items"), SKILL(
			"Skills"), MONSTER("Monsters"), SPAWN("Spawns"), TRIGGER(
				"Triggers"), PROCESSING("Processing");

	private String name;

	private ViewerCategory(String name) {
	    this.name = name;
	}

	public String getName() {
	    return name;
	}
    }

    private ViewerCategory category;

    public ViewerCategoryComponent() {
    }

    public ViewerCategory getCategory() {
	return category;
    }

    public ViewerCategoryComponent setCategory(ViewerCategory category) {
	this.category = category;

	return this;
    }

    @Override
    public void reset() {
	category = null;
    }
}