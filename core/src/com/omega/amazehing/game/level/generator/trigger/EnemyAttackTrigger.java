package com.omega.amazehing.game.level.generator.trigger;

import com.badlogic.gdx.math.Circle;
import com.omega.amazehing.game.entity.component.trigger.TriggerTypeComponent.TriggerType;

public class EnemyAttackTrigger extends Trigger<Circle> {

    public EnemyAttackTrigger() {
    }

    public EnemyAttackTrigger(float x, float y, float radius) {
	shape = new Circle(x, y, radius);
	type = TriggerType.EnemyAttack;
    }

}
