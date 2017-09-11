package com.omega.amazehing.factory.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.mensa.database.sqlite.core.PreparedStatement;
import com.mensa.database.sqlite.core.SQLiteException;
import com.omega.amazehing.Assets;
import com.omega.amazehing.Constants;
import com.omega.amazehing.factory.BodyFactory;
import com.omega.amazehing.factory.Factory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ActiveComponent;
import com.omega.amazehing.game.entity.component.BodyComponent;
import com.omega.amazehing.game.entity.component.ControlledComponent;
import com.omega.amazehing.game.entity.component.ViewerNameComponent;
import com.omega.amazehing.game.entity.component.attack.state.HealthComponent;
import com.omega.amazehing.game.entity.component.attack.state.ManaComponent;
import com.omega.amazehing.game.entity.component.attack.state.StaminaComponent;
import com.omega.amazehing.game.entity.component.movement.MovementDirectionComponent;
import com.omega.amazehing.game.entity.component.movement.MovementTypeComponent;
import com.omega.amazehing.game.entity.component.movement.MovementTypeComponent.MovementType;
import com.omega.amazehing.game.entity.component.movement.SpeedComponent;
import com.omega.amazehing.game.entity.component.movement.VelocityComponent;
import com.omega.amazehing.game.entity.component.render.AnimationComponent;
import com.omega.amazehing.game.entity.component.render.BlendComponent;
import com.omega.amazehing.game.entity.component.render.FlipComponent;
import com.omega.amazehing.game.entity.component.render.TextureComponent;
import com.omega.amazehing.game.entity.component.render.ZIndexComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.entity.component.transform.RotationComponent;
import com.omega.amazehing.game.entity.component.transform.SizeComponent;
import com.omega.amazehing.handler.PlayerHandler;
import com.omega.amazehing.setting.SettingManager;

public class PlayerFactory implements Factory {

    private AssetManager assetManager;
    private SettingManager settingManager;
    private EntityEngine engine;
    private BodyFactory bodyFactory;
    private PlayerHandler playerHandler;
    private PreparedStatement statement;

    public PlayerFactory(AssetManager assetManager, SettingManager settingManager,
	    EntityEngine engine, BodyFactory bodyFactory) throws SQLiteException {
	this.assetManager = assetManager;
	this.settingManager = settingManager;
	this.engine = engine;
	this.bodyFactory = bodyFactory;

	playerHandler = new PlayerHandler();
    }

    public Entity createPlayer(float x, float y) {
	final Entity _player = engine.createEntity();
	Body _body = bodyFactory.createPlayer(x, y);
	_player.add(engine.createComponent(BodyComponent.class).setBody(_body));
	_body.setUserData(_player);

	_player.add(engine.createComponent(ViewerNameComponent.class).setName("Player"))
		.add(engine.createComponent(ControlledComponent.class))
		.add(engine.createComponent(PositionComponent.class).setX(x).setY(y))
		.add(engine.createComponent(HealthComponent.class).setMax(100).setCurrent(60)
			.setRegen(1))
		.add(engine.createComponent(StaminaComponent.class).setMax(200).setRegen(5))
		.add(engine.createComponent(ManaComponent.class).setMax(200).setRegen(5))
		.add(engine.createComponent(SpeedComponent.class).setSpeed(20f))
		.add(engine.createComponent(RotationComponent.class))
		.add(engine.createComponent(SizeComponent.class).setWidth(1f).setHeight(1f))
		.add(engine.createComponent(VelocityComponent.class))
		.add(engine.createComponent(ZIndexComponent.class).setZindex(2))
		.add(engine.createComponent(TextureComponent.class))
		.add(engine.createComponent(FlipComponent.class).setFlipX(true))
		.add(engine.createComponent(BlendComponent.class).setBlending(true))
		.add(engine.createComponent(ActiveComponent.class))
		.add(engine.createComponent(MovementDirectionComponent.class))
		.add(engine.createComponent(MovementTypeComponent.class).setMovementType(
			MovementType.getByName(settingManager
				.getString(Constants.Settings.Controls.TAG_MOVEMENT_TYPE))));

	Animation _anim = assetManager.get(Assets.Animations.Player.STAND);
	_player.add(engine.createComponent(AnimationComponent.class).setAnimation(_anim));

	playerHandler.setPlayer(_player);
	engine.addEntity(_player);

	return _player;
    }

    public PlayerHandler getPlayerHandler() {
	return playerHandler;
    }
}