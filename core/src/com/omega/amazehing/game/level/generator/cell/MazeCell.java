package com.omega.amazehing.game.level.generator.cell;

import com.omega.amazehing.game.level.generator.wall.Wall;

public class MazeCell {

    protected int x;
    protected int y;

    protected Wall leftWall;
    protected Wall rightWall;
    protected Wall upWall;
    protected Wall dowWall;

    public MazeCell() {
    }

    public MazeCell(int x, int y) {
	this.x = x;
	this.y = y;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public Wall getLeftWall() {
	return leftWall;
    }

    public void setLeftWall(Wall leftWall) {
	this.leftWall = leftWall;
    }

    public Wall getRightWall() {
	return rightWall;
    }

    public void setRightWall(Wall rightWall) {
	this.rightWall = rightWall;
    }

    public Wall getUpWall() {
	return upWall;
    }

    public void setUpWall(Wall upWall) {
	this.upWall = upWall;
    }

    public Wall getDowWall() {
	return dowWall;
    }

    public void setDowWall(Wall dowWall) {
	this.dowWall = dowWall;
    }

    @Override
    public String toString() {
	return "[" + x + ", " + y + "]";
    }
}