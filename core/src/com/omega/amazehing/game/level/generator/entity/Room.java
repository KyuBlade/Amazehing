package com.omega.amazehing.game.level.generator.entity;

import com.omega.amazehing.game.level.generator.cell.RoomDoor;

public class Room {

    private int x;
    private int y;
    private int size;
    private boolean isCarved;
    private RoomDoor firstDoor;
    private RoomDoor secondDoor;

    public Room() {
    }

    public Room(int x, int y, int size) {
	this.x = x;
	this.y = y;
	this.size = size;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public int getSize() {
	return size;
    }

    public RoomDoor getFirstDoor() {
	return firstDoor;
    }

    public void setFirstDoor(RoomDoor firstDoor) {
	this.firstDoor = firstDoor;
    }

    public RoomDoor getSecondDoor() {
	return secondDoor;
    }

    public void setSecondDoor(RoomDoor secondDoor) {
	this.secondDoor = secondDoor;
    }

    public boolean isCarved() {
	return isCarved;
    }

    public void setCarved(boolean isCarved) {
	this.isCarved = isCarved;
    }
}