package com.omega.amazehing.screen.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.I18NBundle;
import com.gdx.extension.screen.BaseScreen;
import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.entity.ItemFactory;
import com.omega.amazehing.factory.entity.SkillFactory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.ui.hotbar.Hotbar;
import com.omega.amazehing.ui.hotbar.HotbarInventoryItem;
import com.omega.amazehing.ui.hotbar.HotbarSkillItem;
import com.omega.amazehing.ui.inventory.InventoryItem;
import com.omega.amazehing.ui.state.StateBar;

public class GameInterfaceScreen extends BaseScreen {

    private StateBar healthBar;
    private StateBar staminaBar;
    private StateBar manaBar;

    private Hotbar hotbar;

    private DragAndDrop dragnDrop;

    public GameInterfaceScreen(final ScreenManager screenManager, I18NBundle i18n, EntityEngine engine,
	    FactoryManager factoryManager, DragAndDrop dragnDrop) throws Exception {
	super(screenManager, 15);

	this.dragnDrop = dragnDrop;

	layout.bottom();
	layout.setTouchable(Touchable.childrenOnly);

	healthBar = new StateBar(skin, "health");
	staminaBar = new StateBar(skin, "stamina");
	manaBar = new StateBar(skin, "mana");
	layout.add(healthBar).fillY().right();

	VerticalGroup _innerGroup = new VerticalGroup();
	_innerGroup.addActor(staminaBar);
	_innerGroup.addActor(manaBar);
	layout.add(_innerGroup).left();
	layout.row();

	hotbar = new Hotbar(10, false, dragnDrop, skin);
	layout.add(hotbar).colspan(2);

	dragnDrop.addTarget(new Target(layout) {

	    @Override
	    public void drop(Source source, Payload payload, float x, float y, int pointer) {
		Actor _sourceActor = source.getActor();
		if (_sourceActor instanceof HotbarInventoryItem || _sourceActor instanceof HotbarSkillItem) {
		    source.getActor().remove();
		} else if (_sourceActor instanceof InventoryItem) {
		    InventoryItem _invItem = (InventoryItem) _sourceActor;
		    InventoryScreen _invScreen = screenManager.getScreen(InventoryScreen.class);
		    _invScreen.removeItem(_invItem);
		}
	    }

	    @Override
	    public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
		return true;
	    }
	});

	ItemFactory _itemFactory = factoryManager.getFactory(ItemFactory.class);
	SkillFactory _skillFactory = factoryManager.getFactory(SkillFactory.class);
	screenManager.registerScreen(new InventoryScreen(screenManager, i18n, engine, _itemFactory,
		this.layout, dragnDrop), false);
	screenManager.registerScreen(new SkillScreen(screenManager, i18n, engine, _skillFactory,
		this.layout, dragnDrop), false);
	screenManager.registerScreen(new CharacterScreen(screenManager, i18n, engine, this.layout,
		dragnDrop), false);
    }

    @Override
    public void dispose() {
	screenManager.unregisterScreen(InventoryScreen.class);
	screenManager.unregisterScreen(SkillScreen.class);
	screenManager.unregisterScreen(CharacterScreen.class);
    }

    public StateBar getHealthBar() {
	return healthBar;
    }

    public StateBar getStaminaBar() {
	return staminaBar;
    }

    public StateBar getManaBar() {
	return manaBar;
    }

    public Hotbar getHotbar() {
	return hotbar;
    }
}