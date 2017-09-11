package com.omega.amazehing.ui.character;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.render.TextureComponent;

public class CharacterItem extends Container<Actor> {

    private static final ComponentMapper<TextureComponent> regionMapper = ComponentMapperHandler
	    .getTextureMapper();

    private Entity item;

    private Image image;

    public CharacterItem(Entity item) {
	this.item = item;

	setTouchable(Touchable.enabled);

	TextureComponent _regionComp = regionMapper.get(item);
	image = new Image(_regionComp.getTexture());
	image.setScaling(Scaling.fit);
	setActor(image);
    }

    public Entity getItem() {
	return item;
    }
}
