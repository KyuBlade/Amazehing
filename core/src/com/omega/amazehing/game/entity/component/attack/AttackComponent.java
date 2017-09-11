package com.omega.amazehing.game.entity.component.attack;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;

public class AttackComponent implements Component, Poolable {

    private Entity attack;

    public AttackComponent() {
    }

    public Entity getAttack() {
	return attack;
    }

    public AttackComponent setAttack(Entity attack) {
	this.attack = attack;

	return this;
    }

    @Override
    public void reset() {
	attack = null;
    }
}