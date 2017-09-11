package com.omega.amazehing.ui.inventory;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;

public class InventorySlotTarget extends Target {

    public InventorySlotTarget(Actor actor) {
	super(actor);
    }

    @Override
    public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
	return true;
    }

    @Override
    public void drop(Source source, Payload payload, float x, float y, int pointer) {
	Actor _sourceActor = source.getActor();
	if (_sourceActor instanceof InventoryItem) {
	    InventorySlot _sourceSlot = (InventorySlot) source.getActor().getParent();
	    InventoryItem _sourceItem = _sourceSlot.getActor();
	    InventorySlot _targetSlot = (InventorySlot) getActor();
	    InventoryItem _targetItem = _targetSlot.getActor();

	    _targetSlot.setActor(_sourceItem);
	    _sourceSlot.setActor(_targetItem);
	}
    }
}