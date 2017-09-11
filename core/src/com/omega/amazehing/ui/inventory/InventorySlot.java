package com.omega.amazehing.ui.inventory;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class InventorySlot extends Container<InventoryItem> {

    public InventorySlot(Skin skin) {
	InventorySlotStyle _style = skin.get("default", InventorySlotStyle.class);
	setBackground(_style.background);

	setTouchable(Touchable.enabled);
	fill();
    }

    static public class InventorySlotStyle {

	public Drawable background;

	public InventorySlotStyle() {
	}

    }

}
