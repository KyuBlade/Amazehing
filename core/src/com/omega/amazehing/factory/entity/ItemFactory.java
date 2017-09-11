package com.omega.amazehing.factory.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.LongMap;
import com.mensa.database.sqlite.core.Database;
import com.mensa.database.sqlite.core.DatabaseCursor;
import com.mensa.database.sqlite.core.PreparedStatement;
import com.mensa.database.sqlite.core.SQLiteException;
import com.omega.amazehing.Assets;
import com.omega.amazehing.factory.Factory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.IdentityComponent;
import com.omega.amazehing.game.entity.component.LevelComponent;
import com.omega.amazehing.game.entity.component.NameComponent;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent.ViewerCategory;
import com.omega.amazehing.game.entity.component.ViewerNameComponent;
import com.omega.amazehing.game.entity.component.attack.CastTimeComponent;
import com.omega.amazehing.game.entity.component.attack.CooldownComponent;
import com.omega.amazehing.game.entity.component.attack.DamageComponent;
import com.omega.amazehing.game.entity.component.attack.RangeComponent;
import com.omega.amazehing.game.entity.component.item.DestroyOnUseComponent;
import com.omega.amazehing.game.entity.component.item.ItemTypeComponent;
import com.omega.amazehing.game.entity.component.item.StackableComponent;
import com.omega.amazehing.game.entity.component.item.TwoHandedComponent;
import com.omega.amazehing.game.entity.component.render.TextureComponent;
import com.omega.amazehing.render.RegionManager;

public class ItemFactory implements Factory, Disposable {

    private I18NBundle i18n;
    private EntityEngine engine;
    private RegionManager regionManager;
    private Database database;
    private PreparedStatement statement;

    private LongMap<Entity> templates;

    public enum ItemType {
	ANY, HEAD, NECKLACE, ARMOR, RING, WEAPON, LEGS, FEET, RESOURCE;
    }

    public ItemFactory(I18NBundle i18n, EntityEngine engine, RegionManager regionManager,
	    Database db) throws SQLiteException {
	this.i18n = i18n;
	this.engine = engine;
	this.regionManager = regionManager;
	this.database = db;

	templates = new LongMap<Entity>();

	statement = database.getPreparedStatement("SELECT * FROM Item WHERE ID = ?");
    }

    public Entity getItem(long itemId) throws Exception {
	Entity _item = templates.get(itemId);
	if (_item != null) {
	    return _item;
	}

	statement.setLong(1, itemId);

	DatabaseCursor _itemCursor = statement.executeQuery();
	if (_itemCursor.next()) {
	    int _index = 0;
	    long _id = _itemCursor.getLong(_index++);
	    int _textureId = _itemCursor.getInt(_index++);
	    String _name = i18n.get(_itemCursor.getString(_index++));
	    int _level = _itemCursor.getInt(_index++);
	    String _useTypeName = _itemCursor.getString(_index++);
	    ItemType _useType = ItemType.valueOf(ItemType.class, _useTypeName);
	    boolean _isStackable = (_itemCursor.getInt(_index++) > 0) ? true : false;
	    boolean _isDestroyedOnUse = (_itemCursor.getInt(_index++) > 0) ? true : false;
	    boolean _isTwoHanded = (_itemCursor.getInt(_index++) > 0) ? true : false;
	    int _delay = _itemCursor.getInt(_index++);
	    float _range = _itemCursor.getFloat(_index++);
	    int _damageMin = _itemCursor.getInt(_index++);
	    int _damageMax = _itemCursor.getInt(_index++);
	    int _castTime = _itemCursor.getInt(_index++);

	    _item = engine.createEntity();
	    _item.add(engine.createComponent(ViewerCategoryComponent.class)
		    .setCategory(ViewerCategory.ITEM))
		    .add(engine.createComponent(ViewerNameComponent.class).setName(_name))
		    .add(engine.createComponent(NameComponent.class).setName(_name))
		    .add(engine.createComponent(IdentityComponent.class).setIdentity(_id))
		    .add(engine.createComponent(CooldownComponent.class).setDelay(_delay))
		    .add(engine.createComponent(ItemTypeComponent.class).setType(_useType));

	    TextureComponent _textureComp = engine.createComponent(TextureComponent.class);
	    TextureRegion _region = regionManager.get(Assets.Atlases.ITEMS_ID, _textureId);
	    if (_region == null) {
		_region = regionManager.getFailOverRegion(Assets.Atlases.ITEMS_ID);
	    }
	    _textureComp.setTexture(_region);
	    _item.add(_textureComp);

	    if (_level > 0) {
		_item.add(engine.createComponent(LevelComponent.class).setLevel(_level));
	    }
	    if (_isStackable) {
		_item.add(engine.createComponent(StackableComponent.class));
	    }
	    if (_isDestroyedOnUse) {
		_item.add(engine.createComponent(DestroyOnUseComponent.class));
	    }
	    if (_isTwoHanded) {
		_item.add(engine.createComponent(TwoHandedComponent.class));
	    }
	    if (_range > 0) {
		_item.add(engine.createComponent(RangeComponent.class).setRange(_range));
	    }
	    if (_damageMin > _damageMax) {
		throw new IllegalArgumentException(
			"Min damages should not be higher than max damages.");
	    } else if (_damageMax > 0) {
		_item.add(engine.createComponent(DamageComponent.class).setMinDamage(_damageMin)
			.setMaxDamage(_damageMax));
	    }
	    if (_castTime > 0) {
		_item.add(engine.createComponent(CastTimeComponent.class).setCastTime(_castTime));
	    }

	    templates.put(itemId, _item);
	    engine.addEntity(_item);
	    _itemCursor.close();

	    return _item;
	} else {
	    _itemCursor.close();
	    return null;
	}
    }

    @Override
    public void dispose() {
	try {
	    if (database != null) {
		database.closeDatabase();
	    }
	    if (statement != null) {
		statement.close();
	    }
	} catch (SQLiteException e) {
	}
    }

}
