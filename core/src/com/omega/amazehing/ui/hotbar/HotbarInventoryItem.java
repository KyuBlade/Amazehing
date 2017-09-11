package com.omega.amazehing.ui.hotbar;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.omega.amazehing.ui.inventory.InventoryItem;

public class HotbarInventoryItem extends InventoryItem {

    private InventoryItem source;

    public HotbarInventoryItem(InventoryItem source) {
	super(source);

	this.source = source;

	addListener(new ClickListener(Buttons.RIGHT) {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		remove();
	    }
	});
    }

    public InventoryItem getSource() {
	return source;
    }

}
