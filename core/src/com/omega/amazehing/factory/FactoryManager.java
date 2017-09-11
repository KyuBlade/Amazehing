package com.omega.amazehing.factory;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.I18NBundle;
import com.mensa.database.sqlite.core.Database;
import com.omega.amazehing.factory.entity.InteractiveObjectFactory;
import com.omega.amazehing.factory.entity.ItemFactory;
import com.omega.amazehing.factory.entity.LevelFactory;
import com.omega.amazehing.factory.entity.MonsterFactory;
import com.omega.amazehing.factory.entity.PlayerFactory;
import com.omega.amazehing.factory.entity.ProcessingFactory;
import com.omega.amazehing.factory.entity.RenderFactory;
import com.omega.amazehing.factory.entity.SkillFactory;
import com.omega.amazehing.factory.entity.SteeringFactory;
import com.omega.amazehing.factory.entity.TriggerFactory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.setting.SettingManager;

public class FactoryManager implements Disposable {

    private Map<Class<? extends Factory>, Factory> factories;
    private EntityEngine engine;

    public FactoryManager(AssetManager assetManager, SettingManager settingManager, I18NBundle i18n,
	    EntityEngine engine, Database database) throws Exception {
	factories = new HashMap<Class<? extends Factory>, Factory>();
	this.engine = engine;

	RenderFactory _renderFactory = new RenderFactory(assetManager, engine);
	factories.put(RenderFactory.class, _renderFactory);
	BodyFactory _bodyFactory = new BodyFactory(engine);
	factories.put(BodyFactory.class, _bodyFactory);
	factories.put(ItemFactory.class,
		new ItemFactory(i18n, engine, _renderFactory.getRegionManager(), database));
	factories.put(SkillFactory.class,
		new SkillFactory(i18n, engine, _renderFactory.getRegionManager(), database));
	factories.put(PlayerFactory.class,
		new PlayerFactory(assetManager, settingManager, engine, _bodyFactory));
	factories.put(MonsterFactory.class,
		new MonsterFactory(assetManager, engine, this, database));
	factories.put(TriggerFactory.class, new TriggerFactory(engine, _bodyFactory));
	factories.put(InteractiveObjectFactory.class, new InteractiveObjectFactory(engine, this));
	factories.put(SteeringFactory.class, new SteeringFactory(engine, this));
	factories.put(ProcessingFactory.class, new ProcessingFactory(engine));
	factories.put(TweenFactory.class, new TweenFactory());
	factories.put(LevelFactory.class, new LevelFactory(_renderFactory));
    }

    @SuppressWarnings("unchecked")
    public <T extends Factory> T getFactory(Class<T> type) {
	return (T) factories.get(type);
    }

    public void add(Factory factory) {
	factories.put(factory.getClass(), factory);
    }

    @Override
    public void dispose() {
	for (Factory _factory : factories.values()) {
	    if (_factory instanceof Disposable) {
		((Disposable) _factory).dispose();
	    }
	}
    }

    public EntityEngine getEngine() {
	return engine;
    }
}