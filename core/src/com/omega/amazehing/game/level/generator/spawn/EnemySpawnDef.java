package com.omega.amazehing.game.level.generator.spawn;

import java.io.Serializable;

public class EnemySpawnDef extends AbstractSpawnDef implements Serializable {

    private static final long serialVersionUID = 1098702981914009977L;

    private EnemyType type;

    public EnemySpawnDef() {
    }

    public EnemySpawnDef(float x, float y, EnemyType type) {
	super(x, y);

	this.type = type;
    }

    public EnemyType getType() {
	return type;
    }

    public enum EnemyType {
	Small, Medium, Big, Elite, Boss;
    }

}
