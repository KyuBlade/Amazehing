package com.omega.amazehing.game.level.generator.cell;

import com.omega.amazehing.game.level.generator.entity.Room;

public class RoomCell extends MazeCell {

    private Room room;

    public RoomCell() {
    }

    public RoomCell(Room room, int x, int y) {
	super(x, y);

	this.room = room;
    }

    public Room getRoom() {
	return room;
    }
}
