package com.omega.amazehing.accessor;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.render.ZoomComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.entity.component.transform.RotationComponent;
import com.omega.amazehing.game.entity.component.transform.SizeComponent;

public class EntityAccessor implements TweenAccessor<Entity> {

    public static final int POSITION_X = 1;
    public static final int POSITION_Y = 2;
    public static final int POSITION_XY = 3;
    public static final int WIDTH = 4;
    public static final int HEIGHT = 5;
    public static final int SIZE = 6; // width and height
    public static final int ROTATION = 7;
    public static final int ZOOM = 8;

    public static final ComponentMapper<PositionComponent> positionMapper = ComponentMapperHandler
	    .getPositionMapper();
    public static final ComponentMapper<SizeComponent> sizeMapper = ComponentMapperHandler
	    .getSizeMapper();
    public static final ComponentMapper<RotationComponent> rotationMapper = ComponentMapperHandler
	    .getRotationMapper();
    public static final ComponentMapper<ZoomComponent> zoomMapper = ComponentMapperHandler
	    .getZoomMapper();

    @Override
    public int getValues(Entity target, int tweenType, float[] returnValues) {
	switch (tweenType) {
	    case POSITION_X:
		Vector2 _position = getPosition(target);
		returnValues[0] = _position.x;

		return 1;
	    case POSITION_Y:
		_position = getPosition(target);
		returnValues[0] = _position.y;

		return 1;
	    case POSITION_XY:
		_position = getPosition(target);
		returnValues[0] = _position.x;
		returnValues[1] = _position.y;

		return 2;
	    case WIDTH:
		SizeComponent _sizeComp = getSize(target);
		returnValues[0] = _sizeComp.getWidth();

		return 1;
	    case HEIGHT:
		_sizeComp = getSize(target);
		returnValues[0] = _sizeComp.getHeight();

		return 1;
	    case SIZE:
		_sizeComp = getSize(target);
		returnValues[0] = _sizeComp.getWidth();
		returnValues[1] = _sizeComp.getHeight();

		return 2;
	    case ROTATION:
		RotationComponent _rotComp = rotationMapper.get(target);
		assert _rotComp != null : "The target don't have a rotation component";

		returnValues[0] = _rotComp.getRotation();

		return 1;
	    case ZOOM:
		ZoomComponent _zoomComp = zoomMapper.get(target);
		assert _zoomComp != null : "The target don't have a zoom component";

		returnValues[0] = _zoomComp.getZoom();

		return 1;
	    default:
		assert false;
		return -1;
	}
    }

    @Override
    public void setValues(Entity target, int tweenType, float[] newValues) {
	switch (tweenType) {
	    case POSITION_X:
		Vector2 _position = getPosition(target);
		_position.x = newValues[0];

		break;
	    case POSITION_Y:
		_position = getPosition(target);
		_position.y = newValues[0];

		break;
	    case POSITION_XY:
		_position = getPosition(target);
		_position.x = newValues[0];
		_position.y = newValues[1];

		break;
	    case WIDTH:
		SizeComponent _sizeComp = getSize(target);
		_sizeComp.setWidth(newValues[0]);

		break;
	    case HEIGHT:
		_sizeComp = getSize(target);
		_sizeComp.setHeight(newValues[0]);

		break;
	    case SIZE:
		_sizeComp = getSize(target);
		_sizeComp.setWidth(newValues[0]);
		_sizeComp.setHeight(newValues[1]);

		break;
	    case ROTATION:
		RotationComponent _rotComp = rotationMapper.get(target);
		assert _rotComp != null : "The target don't have a rotation component";

		_rotComp.setRotation(newValues[0]);

		break;
	    case ZOOM:
		ZoomComponent _zoomComp = zoomMapper.get(target);
		assert _zoomComp != null : "The target don't have a zoom component";

		_zoomComp.setZoom(newValues[0]);

		break;
	    default:
		assert false;
		break;
	}
    }

    private final Vector2 getPosition(Entity target) {
	PositionComponent _posComp = positionMapper.get(target);
	assert _posComp != null : "The target don't have a position component";

	return _posComp.getPosition();
    }

    private final SizeComponent getSize(Entity target) {
	SizeComponent _sizeComp = sizeMapper.get(target);
	assert _sizeComp != null : "The target don't have a size component";

	return _sizeComp;
    }
}