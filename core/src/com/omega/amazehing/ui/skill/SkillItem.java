package com.omega.amazehing.ui.skill;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.utils.Scaling;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.render.TextureComponent;

public class SkillItem extends Container<Image> {

    private static final ComponentMapper<TextureComponent> regionMapper = ComponentMapperHandler
	    .getTextureMapper();

    private Skin skin;

    private DragAndDrop dragnDrop;
    private Image image;

    private Entity skill;

    public SkillItem(SkillItem source) {
	this(source.skill, source.dragnDrop, source.skin);
    }

    public SkillItem(Entity skill, final DragAndDrop dragnDrop, Skin skin) {
	this.skill = skill;
	this.dragnDrop = dragnDrop;
	this.skin = skin;

	setTouchable(Touchable.enabled);

	TextureComponent _regionComp = regionMapper.get(skill);
	image = new Image(_regionComp.getTexture());
	image.setScaling(Scaling.fit);

	setActor(image);

	addListener(new ClickListener(Buttons.RIGHT) {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {

	    }
	});
	dragnDrop.addSource(new Source(this) {

	    @Override
	    public Payload dragStart(InputEvent event, float x, float y, int pointer) {
		Payload _payload = new Payload();
		Image _dragImage = new Image(SkillItem.this.getImage().getDrawable());
		_dragImage.setSize(32f, 32f);
		_payload.setDragActor(_dragImage);
		dragnDrop.setDragActorPosition(-16f, 16f);

		return _payload;
	    }
	});
    }

    public Image getImage() {
	return image;
    }

    public Entity getSkill() {
	return skill;
    }

    public Skin getSkin() {
	return skin;
    }

}
