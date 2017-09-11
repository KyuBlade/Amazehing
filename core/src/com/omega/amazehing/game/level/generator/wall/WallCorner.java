package com.omega.amazehing.game.level.generator.wall;

import com.badlogic.gdx.math.Rectangle;

public class WallCorner extends Rectangle {

    private static final long serialVersionUID = 5353558739678681370L;

    private boolean hasLeftWall;
    private boolean hasRightWall;
    private boolean hasUpWall;
    private boolean hasDownWall;

    public WallCorner() {
    }

    public WallCorner(Rectangle intersection, boolean hasLeftWall, boolean hasRightWall, boolean hasUpWall, boolean hasDownWall) {
	super(intersection);

	this.hasLeftWall = hasLeftWall;
	this.hasRightWall = hasRightWall;
	this.hasUpWall = hasUpWall;
	this.hasDownWall = hasDownWall;
    }

    public boolean hasLeftWall() {
	return hasLeftWall;
    }

    public boolean hasRightWall() {
	return hasRightWall;
    }

    public boolean hasUpWall() {
	return hasUpWall;
    }

    public boolean hasDownWall() {
	return hasDownWall;
    }

}
