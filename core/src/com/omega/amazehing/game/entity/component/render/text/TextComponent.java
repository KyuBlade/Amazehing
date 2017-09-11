package com.omega.amazehing.game.entity.component.render.text;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TextComponent implements Component, Poolable {

    private String text;

    public TextComponent() {
    }

    public String getText() {
	return text;
    }

    public TextComponent setText(String text) {
	this.text = text;

	return this;
    }

    @Override
    public void reset() {
	text = null;
    }
}