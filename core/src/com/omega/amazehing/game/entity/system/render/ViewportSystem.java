package com.omega.amazehing.game.entity.system.render;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.omega.amazehing.Constants;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.event.SizeChangedComponent;
import com.omega.amazehing.game.entity.component.render.ViewportComponent;
import com.omega.amazehing.game.entity.component.transform.SizeComponent;

public class ViewportSystem extends IteratingSystem {

    private EntityEngine engine;

    private Entity mainViewport;

    private static final ComponentMapper<ViewportComponent> viewportMapper = ComponentMapperHandler
	    .getViewportMapper();
    private static final ComponentMapper<SizeChangedComponent> sizeChangedMapper = ComponentMapperHandler
	    .getSizeChangedMapper();
    private static final ComponentMapper<SizeComponent> sizeMapper = ComponentMapperHandler
	    .getSizeMapper();

    @SuppressWarnings("unchecked")
    public ViewportSystem() {
	super(Family.all(ViewportComponent.class, SizeComponent.class, SizeChangedComponent.class)
		.get(), Constants.Game.System.VIEWPORT_SYSTEM_PRIORITY);
    }

    @Override
    public void addedToEngine(Engine engine) {
	super.addedToEngine(engine);

	this.engine = (EntityEngine) engine;

	CameraSystem _cameraSystem = engine.getSystem(CameraSystem.class);
	Camera _camera = _cameraSystem.getMainCamera();

	mainViewport = this.engine.createEntity();
	mainViewport
		.add(this.engine.createComponent(ViewportComponent.class).setViewport(
			new ExtendViewport(Constants.Level.UNIT_SCALE, Constants.Level.UNIT_SCALE,
				_camera)))
		.add(this.engine.createComponent(SizeComponent.class)
			.setWidth(Gdx.graphics.getWidth()).setHeight(Gdx.graphics.getHeight()))
		.add(this.engine.createComponent(SizeChangedComponent.class).setHasChanged(true));
	engine.addEntity(mainViewport);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
	SizeChangedComponent _sizeChangedComp = sizeChangedMapper.get(entity);
	boolean _hasChanged = _sizeChangedComp.hasChanged();
	if (_hasChanged) {
	    ViewportComponent _viewportComp = viewportMapper.get(entity);
	    SizeComponent _sizeComp = sizeMapper.get(entity);

	    Viewport _viewport = _viewportComp.getViewport();

	    _viewport.update((int) _sizeComp.getWidth(), (int) _sizeComp.getHeight());

	    _sizeChangedComp.setHasChanged(false);
	}
    }

    public void resize(int width, int height) {
	mainViewport.add(
		engine.createComponent(SizeComponent.class).setWidth(width).setHeight(height)).add(
		engine.createComponent(SizeChangedComponent.class).setHasChanged(true));
    }
}