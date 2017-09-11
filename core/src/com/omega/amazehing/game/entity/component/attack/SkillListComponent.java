package com.omega.amazehing.game.entity.component.attack;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

public class SkillListComponent implements Component, Poolable {

    private Array<Entity> skills = new Array<Entity>();

    public SkillListComponent() {
    }

    public Array<Entity> getSkills() {
	return skills;
    }

    public SkillListComponent addSkill(Entity skill) {
	skills.add(skill);

	return this;
    }

    public SkillListComponent removeSkill(Entity skill) {
	skills.removeValue(skill, true);

	return this;
    }

    public SkillListComponent clearSkills() {
	skills.clear();

	return this;
    }

    public void reset() {
	skills.clear();
    };
}