package com.omega.amazehing.ui.character;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.omega.amazehing.screen.game.CharacterScreen.CharacterSlotType;

public class CharacterSlot extends Container<CharacterItem> {

    private CharacterSlotStyle style;
    private CharacterSlotStyle slotTypeStyle;
    private static CharacterSlotStyle slotEmptyStyle;
    private CharacterSlotType allowedType;
    private CharacterItem characterItem;

    public CharacterSlot(CharacterSlotType allowedType, Skin skin, String styleName) {
	this.allowedType = allowedType;

	slotTypeStyle = skin.get(styleName, CharacterSlotStyle.class);

	if (slotEmptyStyle == null) {
	    slotEmptyStyle = skin.get(CharacterSlotStyle.class);
	}

	setStyle(slotTypeStyle);

	setTouchable(Touchable.enabled);
	fill();
    }

    public CharacterSlotStyle getStyle() {
	return style;
    }

    public void setStyle(CharacterSlotStyle style) {
	this.style = style;

	setBackground(style.background);
    }

    public CharacterSlotType getAllowedType() {
	return allowedType;
    }

    public void setCharacterItem(CharacterItem characterItem) {
	this.characterItem = characterItem;

	if (characterItem != null) {
	    setStyle(slotEmptyStyle);
	} else {
	    setStyle(slotTypeStyle);
	}

	setActor(characterItem);
    }

    public CharacterItem getCharacterItem() {
	return characterItem;
    }

    static public class CharacterSlotStyle {

	public Drawable background;

	public CharacterSlotStyle() {
	}
    }
}
