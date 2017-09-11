package com.omega.amazehing.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class LevelComponent implements Component, Poolable {

    private int level;

    public LevelComponent() {
    }

    public int getLevel() {
	return level;
    }

    public LevelComponent setLevel(int level) {
	this.level = level;

	return this;
    }

    @Override
    public void reset() {
	level = 0;
    }
}