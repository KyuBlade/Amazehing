package com.omega.amazehing.game.entity.component.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CameraComponent implements Component, Poolable {

    private Camera camera;

    public CameraComponent() {
    }

    public Camera getCamera() {
	return camera;
    }

    public CameraComponent setCamera(Camera camera) {
	this.camera = camera;

	return this;
    }

    @Override
    public void reset() {
	camera = null;
    }
}