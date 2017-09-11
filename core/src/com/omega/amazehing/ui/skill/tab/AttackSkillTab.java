package com.omega.amazehing.ui.skill.tab;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.gdx.extension.ui.tab.TabContainer;
import com.omega.amazehing.factory.entity.SkillFactory;
import com.omega.amazehing.ui.skill.SkillItem;
import com.omega.amazehing.ui.skill.SkillSlot;

public class AttackSkillTab extends TabContainer {

    public AttackSkillTab(DragAndDrop dragnDrop, Skin skin, SkillFactory skillFactory)
	    throws Exception {
	super(skin);

	top().left();
	defaults().size(45f);
	add(new SkillSlot(new SkillItem(skillFactory.getSkill(1), dragnDrop, skin), skin));
    }
}