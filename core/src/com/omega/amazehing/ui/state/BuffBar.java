package com.omega.amazehing.ui.state;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.SnapshotArray;

public class BuffBar extends Container<Group> {

    private boolean isVertical;
    private Group group;

    public BuffBar(Skin skin, boolean isVertical) {
	this.isVertical = !isVertical;
	setVertical(isVertical);
    }

    public void setVertical(boolean isVertical) {
	if (this.isVertical == isVertical) {
	    return;
	}

	this.isVertical = isVertical;

	SnapshotArray<Actor> _oldItems = group.getChildren();
	if (isVertical) {
	    group = new VerticalGroup();
	} else {
	    group = new HorizontalGroup();
	}

	for (Actor item : _oldItems) {
	    group.addActor(item);
	}

	setActor(group);
    }

    public void addBuff() {

    }

    public void removeBuff() {

    }

    public void toogleVertical() {
	setVertical(!isVertical);
    }
}