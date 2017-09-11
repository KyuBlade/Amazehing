package com.omega.amazehing.factory.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.IntMap;
import com.mensa.database.sqlite.core.Database;
import com.mensa.database.sqlite.core.DatabaseCursor;
import com.mensa.database.sqlite.core.PreparedStatement;
import com.mensa.database.sqlite.core.SQLiteException;
import com.omega.amazehing.Assets;
import com.omega.amazehing.factory.Factory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.IdentityComponent;
import com.omega.amazehing.game.entity.component.LevelComponent;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent.ViewerCategory;
import com.omega.amazehing.game.entity.component.ViewerNameComponent;
import com.omega.amazehing.game.entity.component.attack.CastTimeComponent;
import com.omega.amazehing.game.entity.component.attack.CooldownComponent;
import com.omega.amazehing.game.entity.component.attack.DamageComponent;
import com.omega.amazehing.game.entity.component.attack.RangeComponent;
import com.omega.amazehing.game.entity.component.render.TextureComponent;
import com.omega.amazehing.game.entity.component.skill.PassiveComponent;
import com.omega.amazehing.render.RegionManager;

public class SkillFactory implements Factory, Disposable {

    private I18NBundle i18n;
    private EntityEngine engine;
    private RegionManager regionManager;
    private Database database;
    private PreparedStatement statement;

    private IntMap<Entity> cache;

    public SkillFactory(I18NBundle i18n, EntityEngine engine, RegionManager regionManager,
	    Database db) throws SQLiteException {
	this.i18n = i18n;
	this.engine = engine;
	this.regionManager = regionManager;
	this.database = db;

	this.statement = database.getPreparedStatement("SELECT * FROM Skill WHERE ID = ?");
	cache = new IntMap<Entity>();
    }

    public Entity getSkill(int skillId) throws Exception {
	Entity _skill = cache.get(skillId);
	if (_skill != null) {
	    return _skill;
	}

	statement.setInt(1, skillId);

	DatabaseCursor _skillCursor = statement.executeQuery();
	if (_skillCursor.next()) {
	    int _index = 0;
	    _skill = engine.createEntity();
	    int _id = _skillCursor.getInt(_index++);
	    int _textureId = _skillCursor.getInt(_index++);
	    String _name = i18n.get(_skillCursor.getString(_index++));
	    int _level = _skillCursor.getInt(_index++);
	    float _range = _skillCursor.getFloat(_index++);
	    boolean _isPassive = (_skillCursor.getInt(_index++) > 0) ? true : false;
	    int _damageMin = _skillCursor.getInt(_index++);
	    int _damageMax = _skillCursor.getInt(_index++);
	    float _castTime = _skillCursor.getFloat(_index++);
	    float _coolDown = _skillCursor.getFloat(_index++);
	    _skill.add(engine.createComponent(IdentityComponent.class).setIdentity(_id))
		    .add(engine.createComponent(ViewerCategoryComponent.class)
			    .setCategory(ViewerCategory.SKILL))
		    .add(engine.createComponent(ViewerNameComponent.class).setName(_name));

	    TextureComponent _textureComp = engine.createComponent(TextureComponent.class);
	    TextureRegion _region = regionManager.get(Assets.Atlases.SKILLS_ID, _textureId);
	    if (_region == null) {
		_region = regionManager.getFailOverRegion(Assets.Atlases.SKILLS_ID);
	    }
	    _textureComp.setTexture(_region);
	    _skill.add(_textureComp);

	    if (_level > 0) {
		_skill.add(engine.createComponent(LevelComponent.class).setLevel(_level));
	    }
	    if (_damageMin > _damageMax) {
		throw new IllegalArgumentException(
			"Min damages should not be higher than max damages.");
	    } else if (_damageMax > 0) {
		_skill.add(engine.createComponent(DamageComponent.class).setMinDamage(_damageMin)
			.setMaxDamage(_damageMax));
	    }
	    if (_castTime > 0) {
		_skill.add(engine.createComponent(CastTimeComponent.class).setCastTime(_castTime));
	    }

	    _skill.add(engine.createComponent(RangeComponent.class).setRange(_range))
		    .add(engine.createComponent(CooldownComponent.class).setDelay(_coolDown))
		    .add(engine.createComponent(PassiveComponent.class).setPassive(_isPassive));

	    engine.addEntity(_skill);
	    cache.put(skillId, _skill);
	    _skillCursor.close();

	    return _skill;
	} else {
	    _skillCursor.close();

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
