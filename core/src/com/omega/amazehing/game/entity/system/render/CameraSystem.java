package com.omega.amazehing.game.entity.system.render;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.omega.amazehing.Constants;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.render.CameraComponent;
import com.omega.amazehing.game.entity.component.render.ZoomComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.entity.component.transform.RotationComponent;
import com.omega.amazehing.handler.PlayerHandler;

public class CameraSystem extends IteratingSystem {

    private EntityEngine engine;
    private PlayerHandler playerHandler;

    private Entity mainCamera;

    private static final ComponentMapper<CameraComponent> cameraMapper = ComponentMapperHandler
	    .getCameraMapper();
    private static final ComponentMapper<PositionComponent> positionMapper = ComponentMapperHandler
	    .getPositionMapper();
    private static final ComponentMapper<RotationComponent> rotationMapper = ComponentMapperHandler
	    .getRotationMapper();
    private static final ComponentMapper<ZoomComponent> zoomMapper = ComponentMapperHandler
	    .getZoomMapper();

    public CameraSystem(PlayerHandler playerHandler) {
	super(Family.all(CameraComponent.class, PositionComponent.class, RotationComponent.class)
		.get(), Constants.Game.System.CAMERA_SYSTEM_PRIORITY);

	this.playerHandler = playerHandler;
    }

    @Override
    public void addedToEngine(Engine engine) {
	super.addedToEngine(engine);

	this.engine = (EntityEngine) engine;

	OrthographicCamera _mainCamera = new OrthographicCamera();
	_mainCamera.setToOrtho(true, Constants.Level.UNIT_SCALE, Constants.Level.UNIT_SCALE);

	mainCamera = this.engine.createEntity();
	mainCamera.add(this.engine.createComponent(CameraComponent.class).setCamera(_mainCamera))
		.add(this.engine.createComponent(PositionComponent.class))
		.add(this.engine.createComponent(RotationComponent.class))
		.add(this.engine.createComponent(ZoomComponent.class).setMinZoom(0.5f).setMaxZoom(3f));
	engine.addEntity(mainCamera);
    }

    @Override
    public void update(float deltaTime) {
	Entity _player = playerHandler.getPlayer();
	if (_player != null) {
	    PositionComponent _posComp = positionMapper.get(_player);
	    Vector2 _pos = _posComp.getPosition();

	    mainCamera.add(engine.createComponent(PositionComponent.class).setPosition(_pos));
	}

	super.update(deltaTime);
    }

    protected void processEntity(Entity entity, float deltaTime) {
	CameraComponent _cameraComp = cameraMapper.get(entity);
	PositionComponent _posComp = positionMapper.get(entity);
	RotationComponent _rotComp = rotationMapper.get(entity);
	ZoomComponent _zoomComp = zoomMapper.get(entity);

	Camera _camera = _cameraComp.getCamera();
	Vector2 _position = _posComp.getPosition();

	Vector3 _camPos = _camera.position;
	_camPos.x = _position.x;
	_camPos.y = _position.y;

	_camera.rotate(Vector3.X, _rotComp.getRotation());

	if (_zoomComp != null) {
	    OrthographicCamera _orthoCam = ((OrthographicCamera) _camera);
	    float _targetZoom = _zoomComp.getZoom();
	    float _minZoom = _zoomComp.getMinZoom();
	    float _maxZoom = _zoomComp.getMaxZoom();

	    if (_targetZoom > _maxZoom) {
		_targetZoom = _maxZoom;
	    } else if (_targetZoom < _minZoom) {
		_targetZoom = _minZoom;
	    }

	    _zoomComp.setZoom(_targetZoom);
	    _orthoCam.zoom = _targetZoom;
	}

	_camera.update();
    }

    public Entity getMainCameraEntity() {
	return mainCamera;
    }

    public Camera getMainCamera() {
	CameraComponent _cameraComp = cameraMapper.get(mainCamera);

	return _cameraComp.getCamera();
    }
}