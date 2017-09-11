package com.omega.amazehing.game.input.action.impl.action;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.movement.MovementDirectionComponent;
import com.omega.amazehing.game.input.action.InputAction;
import com.omega.amazehing.game.level.generator.cell.Direction;
import com.omega.amazehing.handler.PlayerHandler;

public class MoveBackwardInputAction implements InputAction {

    private PlayerHandler playerHandler;

    private static final ComponentMapper<MovementDirectionComponent> moveDirMapper = ComponentMapperHandler
	    .getMovementDirectionMapper();

    public MoveBackwardInputAction(PlayerHandler playerHandler) {
	this.playerHandler = playerHandler;
    }

    @Override
    public void onDown() {
	Entity _player = playerHandler.getPlayer();
	if (_player != null) {
	    moveDirMapper.get(_player).addDirection(Direction.S);
	}
    }

    @Override
    public void onUp() {
	Entity _player = playerHandler.getPlayer();
	if (_player != null) {
	    moveDirMapper.get(_player).removeDirection(Direction.S);
	}
    }
}