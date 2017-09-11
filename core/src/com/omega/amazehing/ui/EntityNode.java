package com.omega.amazehing.ui;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.ViewerNameComponent;

public class EntityNode extends Node implements Comparable<EntityNode> {

    private static final ComponentMapper<ViewerNameComponent> nameMapper = ComponentMapperHandler.getViewerNameMapper();

    private Entity entity;

    public EntityNode(Entity entity, Skin skin) {
	super(new Label("", skin));

	this.entity = entity;

	ViewerNameComponent _nameComp = nameMapper.get(entity);
	StringBuilder _sBuild = new StringBuilder();
	if (_nameComp != null) {
	    _sBuild.append(_nameComp.getName()).append(" [Id=").append(entity.getId()).append(']');
	} else {
	    _sBuild.append("Entity [Id=").append(entity.getId()).append(']');
	}
	((Label) getActor()).setText(_sBuild.toString());
    }

    @Override
    public int compareTo(EntityNode o) {
	if (entity.getId() < o.entity.getId()) {
	    return -1;
	} else if (entity.getId() > o.entity.getId()) {
	    return 1;
	}
	return 0;
    }
}
