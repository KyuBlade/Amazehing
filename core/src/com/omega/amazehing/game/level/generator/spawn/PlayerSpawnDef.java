package com.omega.amazehing.game.level.generator.spawn;

import java.io.Serializable;

public class PlayerSpawnDef extends AbstractSpawnDef implements Serializable {

    private static final long serialVersionUID = 13648512264442997L;

    public PlayerSpawnDef() {
    }

    public PlayerSpawnDef(float x, float y) {
	super(x, y);
    }

}
