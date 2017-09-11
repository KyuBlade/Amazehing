package com.omega.amazehing.ui;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;

public class ValueNode extends Node {

    private Label value;

    public ValueNode(Label name, Label value) {
	super(new HorizontalGroup());

	this.value = value;

	HorizontalGroup _group = (HorizontalGroup) getActor();
	_group.space(10f);
	_group.addActor(name);
	_group.addActor(value);
    }

    public void setValue(String value) {
	this.value.setText(value);
    }
}
