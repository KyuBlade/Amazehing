package com.omega.amazehing.game.entity.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.omega.amazehing.Constants;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.InteractionComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.entity.component.transform.SizeComponent;
import com.omega.amazehing.game.entity.system.render.CameraSystem;
import com.omega.amazehing.game.input.action.interaction.AbstractInteraction;

public class InteractiveObjectSystem extends EntitySystem {

    private static final Logger logger = LoggerFactory.getLogger(InteractiveObjectSystem.class);

    private EntityEngine engine;
    private ImmutableArray<Entity> entities;

    private boolean update = true;
    private boolean lastLeftCLick;
    private boolean lastRightClick;
    private boolean leftClick;
    private boolean rightClick;
    private Vector3 unprojectVect;

    private Entity lastActivated;

    private static final ComponentMapper<PositionComponent> posMapper = ComponentMapperHandler
	    .getPositionMapper();
    private static final ComponentMapper<SizeComponent> scaleMapper = ComponentMapperHandler
	    .getSizeMapper();
    private static final ComponentMapper<InteractionComponent> interactionMapper = ComponentMapperHandler
	    .getInteractionMapper();

    private Entity lastOvered;

    public InteractiveObjectSystem() {
	super(Constants.Game.System.INTERACTIVE_OBJECT_SYSTEM_PRIORITY);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addedToEngine(Engine engine) {
	this.engine = (EntityEngine) engine;

	entities = engine.getEntitiesFor(Family.all(InteractionComponent.class).get());
	unprojectVect = new Vector3();
    }

    @Override
    public void update(float deltaTime) {
	if (!update) {
	    return;
	}

	CameraSystem _cameraSystem = engine.getSystem(CameraSystem.class);
	Camera _cam = _cameraSystem.getMainCamera();
	unprojectVect.set(Gdx.input.getX(), Gdx.input.getY(), 0f);
	_cam.unproject(unprojectVect);
	float _x = unprojectVect.x;
	float _y = unprojectVect.y;
	boolean _isProcessed = false;
	for (Entity _e : entities) {
	    InteractionComponent _interactionComp = interactionMapper.get(_e);
	    AbstractInteraction _interaction = _interactionComp.getInteraction();
	    boolean _isOver = isOver(_e, _x, _y);
	    if (_isOver && lastOvered != _e) {
		_interaction.onOver(_e, true);
		lastOvered = _e;
		_isProcessed = true;
	    } else if (!_isOver && lastOvered == _e) {
		_interaction.onOver(_e, false);
		lastOvered = null;
		_isProcessed = true;
	    }

	    if (_isOver && leftClick && leftClick != lastLeftCLick) {
		Gdx.app.debug("Interaction", "Activated");
		_interaction.onLeftCLick(_e, true);
		lastActivated = _e;
		_isProcessed = true;
	    } else if (!leftClick && lastActivated == _e) {
		_interaction.onLeftCLick(_e, false);
		lastActivated = null;
		_isProcessed = true;
	    }

	    if (_isProcessed) {
		break;
	    }
	}
	lastLeftCLick = leftClick;
	lastRightClick = rightClick;
    }

    private boolean isOver(Entity entity, float x, float y) {
	PositionComponent _posComp = posMapper.get(entity);
	SizeComponent _scaleComp = scaleMapper.get(entity);
	Vector2 _entityPos = _posComp.getPosition();
	float _halfWidth = _scaleComp.getWidth() * 0.5f;
	float _halfHeight = _scaleComp.getHeight() * 0.5f;

	if (x < _entityPos.x - _halfWidth || x > _entityPos.x + _halfWidth || y < _entityPos.y - _halfHeight || y > _entityPos.y + _halfHeight) {
	    return false;
	}

	return true;
    }

    public boolean fire(int button, boolean isDown) {
	if (!update) {
	    return false;
	}

	switch (button) {
	    case Buttons.LEFT:
		leftClick = isDown;
		return true;

	    case Buttons.RIGHT:
		rightClick = isDown;
		return true;

	    default:
		return false;
	}
    }

    public void setUpdate(boolean canUpdate) {
	update = canUpdate;
    }

    public void reset() {
	fire(Buttons.LEFT, false);
	fire(Buttons.RIGHT, false);
	setUpdate(false);
    }
}