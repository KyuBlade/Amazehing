package com.omega.amazehing.game.level.generator.cell;

import com.omega.amazehing.game.level.generator.entity.Room;

public class RoomDoor extends MazeCell {

    private Room room;

    public RoomDoor() {
    }

    public RoomDoor(Room room, int x, int y) {
	super(x, y);

	this.room = room;
    }

    public Room getRoom() {
	return room;
    }
}