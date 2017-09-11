package com.omega.amazehing.game.entity.component.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TextureComponent implements Component, Poolable {

    private TextureRegion region;

    public TextureComponent() {
    }

    public TextureComponent setTexture(TextureRegion region) {
	this.region = region;

	return this;
    }

    public TextureRegion getTexture() {
	return region;
    }

    @Override
    public void reset() {
	region = null;
    }
}