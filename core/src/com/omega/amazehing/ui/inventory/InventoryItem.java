package com.omega.amazehing.ui.inventory;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.render.TextureComponent;

public class InventoryItem extends Container<Stack> {

    private static final ComponentMapper<TextureComponent> regionMapper = ComponentMapperHandler
	    .getTextureMapper();

    private Skin skin;

    private DragAndDrop dragnDrop;
    private Stack stack;
    private Image image;
    private Label quantityLabel;

    private Entity item;
    private int quantity;

    public InventoryItem(InventoryItem source) {
	this(source, source.item, source.quantity, source.dragnDrop, source.skin);
    }

    public InventoryItem(Entity item, int quantity, DragAndDrop dragnDrop, Skin skin) {
	this(null, item, quantity, dragnDrop, skin);
    }

    public InventoryItem(final InventoryItem source, Entity item, int quantity,
	    final DragAndDrop dragnDrop, Skin skin) {
	final InventoryItem _source = (source != null) ? source : this;
	this.item = item;
	this.quantity = quantity;
	this.dragnDrop = dragnDrop;
	this.skin = skin;

	setTouchable(Touchable.enabled);

	TextureComponent _regionComp = regionMapper.get(item);

	stack = new Stack();
	image = new Image(_regionComp.getTexture());
	image.setScaling(Scaling.fit);
	quantityLabel = new Label(Integer.toString(quantity), skin);
	quantityLabel.setFontScale(0.75f);
	quantityLabel.setAlignment(Align.bottomRight);
	stack.add(image);
	stack.add(quantityLabel);

	setActor(stack);

	addListener(new ClickListener(Buttons.RIGHT) {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {

	    }
	});
	dragnDrop.addSource(new InventorySlotSource(_source, image.getDrawable(), dragnDrop));
    }

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;

	quantityLabel.setText(Integer.toString(quantity));
    }

    public Image getImage() {
	return image;
    }

    public Entity getItem() {
	return item;
    }

    public Skin getSkin() {
	return skin;
    }
}