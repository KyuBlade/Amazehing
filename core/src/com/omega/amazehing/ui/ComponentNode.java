package com.omega.amazehing.ui;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;

public class ComponentNode extends Node {

    private Component component;

    public ComponentNode(Component component, Skin skin) {
	super(new Label(component.getClass().getSimpleName(), skin));

	this.component = component;
    }

    public Component getComponent() {
	return component;
    }

    public void setComponent(Component component) {
	this.component = component;
    }

}
