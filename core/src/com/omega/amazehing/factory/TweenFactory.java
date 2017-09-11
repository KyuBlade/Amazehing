package com.omega.amazehing.factory;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.omega.amazehing.accessor.EntityAccessor;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.render.ZoomComponent;

public class TweenFactory implements Factory {

    private TweenManager manager;

    private static final ComponentMapper<ZoomComponent> zoomMapper = ComponentMapperHandler
	    .getZoomMapper();

    public TweenFactory() {
	manager = new TweenManager();

	Tween.registerAccessor(Entity.class, new EntityAccessor());
    }

    public void createCameraZoom(Entity camera, int amount) {
	ZoomComponent _zoomComp = zoomMapper.get(camera);
	float _targetZoom = _zoomComp.getZoom() + amount;

	manager.killTarget(camera, EntityAccessor.ZOOM);
	Tween.to(camera, EntityAccessor.ZOOM, 0.25f).target(_targetZoom).ease(Linear.INOUT)
		.start(manager);
    }

    public TweenManager getManager() {
	return manager;
    }
}