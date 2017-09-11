package com.omega.amazehing.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent.ViewerCategory;

public class CategoryNode extends Node {

    public CategoryNode(ViewerCategory category, Skin skin) {
	super(new Label(category.getName(), skin));
    }
}
