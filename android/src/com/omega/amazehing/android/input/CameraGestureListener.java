package com.omega.amazehing.android.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;

public class CameraGestureListener extends GestureAdapter {

    private OrthographicCamera camera;
    private float initialZoom = 1f;

    public CameraGestureListener(OrthographicCamera camera) {
	this.camera = camera;
	this.initialZoom = camera.zoom;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
	Gdx.app.debug("TouchDown", x + " / " + y);
	return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
	float _ratio = initialDistance / distance;
	Gdx.app.debug("Zoom", initialDistance + " / " + distance + " = " + _ratio);
	float _zoom = camera.zoom;
	if (_zoom <= 0f && _ratio < 1f)
	    return false;
	else if (_zoom >= 2f && _ratio > 1f)
	    return false;

	camera.zoom = initialZoom * _ratio;

	return false;
    }

}
