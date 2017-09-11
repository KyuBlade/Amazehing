package com.omega.amazehing.game.level.generator.trigger;

import java.io.Serializable;

import com.badlogic.gdx.math.Rectangle;
import com.omega.amazehing.game.entity.component.trigger.TriggerTypeComponent.TriggerType;

public class EndTrigger extends Trigger<Rectangle> implements Serializable {

    private static final long serialVersionUID = 2567181682630512745L;

    public EndTrigger() {
    }

    public EndTrigger(float x, float y, float width, float height) {
	this.shape = new Rectangle(x, y, width, height);
	type = TriggerType.EndTrigger;
    }

}
