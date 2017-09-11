package com.omega.amazehing.ui.skill;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class SkillSlot extends Container<SkillItem> {

    public SkillSlot(SkillItem skillItem, Skin skin) {
	SkillSlotStyle _style = skin.get("default", SkillSlotStyle.class);
	setBackground(_style.background);

	setTouchable(Touchable.enabled);
	fill();
	setActor(skillItem);
    }

    static public class SkillSlotStyle {

	public Drawable background;

	public SkillSlotStyle() {
	}

    }

}
