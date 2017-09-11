package com.omega.amazehing.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;

public class BodyComponent implements Component, Poolable {

    private Body body;

    public BodyComponent() {
    }

    public BodyComponent setBody(Body body) {
	this.body = body;

	return this;
    }

    public Body getBody() {
	return body;
    }

    @Override
    public void reset() {
	body = null;
    }
}