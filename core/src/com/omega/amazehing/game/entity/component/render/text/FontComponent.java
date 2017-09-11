package com.omega.amazehing.game.entity.component.render.text;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Pool.Poolable;

public class FontComponent implements Component, Poolable {

    private BitmapFont font;

    public FontComponent() {
    }

    public BitmapFont getFont() {
	return font;
    }

    public FontComponent setFont(BitmapFont font) {
	this.font = font;

	return this;
    }

    @Override
    public void reset() {
	font = null;
    }
}