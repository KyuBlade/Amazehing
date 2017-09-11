package com.omega.amazehing.ui.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class InventorySlotSource extends Source {

    private Image image;

    public InventorySlotSource(Actor actor, Drawable image, DragAndDrop dragnDrop) {
	super(actor);

	this.image = new Image(image);
	this.image.setSize(32f, 32f);
	dragnDrop.setDragActorPosition(-16f, 16f);
    }

    @Override
    public Payload dragStart(InputEvent event, float x, float y, int pointer) {
	Payload _payload = new Payload();
	_payload.setDragActor(image);

	return _payload;
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload,
	    Target target) {
	Gdx.app.debug("Target", "" + target);
    }
}