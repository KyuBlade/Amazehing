package com.omega.amazehing.ui.hotbar;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class HotbarSlot extends Container<Actor> {

    private HotbarSlotStyle style;

    public HotbarSlot(Skin skin) {
	this(skin, "default");
    }

    public HotbarSlot(Skin skin, String styleName) {
	this(skin.get(styleName, HotbarSlotStyle.class));
    }

    public HotbarSlot(HotbarSlotStyle style) {
	setStyle(style);

	setTouchable(Touchable.enabled);
	fill();
    }

    public HotbarSlotStyle getStyle() {
	return style;
    }

    public void setStyle(HotbarSlotStyle style) {
	this.style = style;

	setBackground(style.background);
    }

    static public class HotbarSlotStyle {

	public Drawable background;

	public HotbarSlotStyle() {
	}

	public HotbarSlotStyle(Drawable background) {
	    this.background = background;
	}

	public HotbarSlotStyle(HotbarSlotStyle style) {
	    background = style.background;
	}
    }

}
