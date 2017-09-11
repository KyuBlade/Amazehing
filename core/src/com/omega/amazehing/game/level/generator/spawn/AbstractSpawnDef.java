package com.omega.amazehing.game.level.generator.spawn;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;

public abstract class AbstractSpawnDef implements Serializable {

    private static final long serialVersionUID = 6098963917722401022L;

    protected Vector2 position;

    public AbstractSpawnDef() {
    }

    public AbstractSpawnDef(float x, float y) {
	this.position = new Vector2(x, y);
    }

    public Vector2 getPosition() {
	return position;
    }

    public float getX() {
	return position.x;
    }

    public float getY() {
	return position.y;
    }

}
