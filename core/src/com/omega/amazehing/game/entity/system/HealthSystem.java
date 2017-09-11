package com.omega.amazehing.game.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.Constants;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.entity.PlayerFactory;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.attack.state.HealthComponent;
import com.omega.amazehing.game.entity.component.attack.state.ManaComponent;
import com.omega.amazehing.game.entity.component.attack.state.StaminaComponent;
import com.omega.amazehing.handler.PlayerHandler;
import com.omega.amazehing.screen.game.GameInterfaceScreen;
import com.omega.amazehing.ui.state.StateBar;

public class HealthSystem extends IteratingSystem {

    private PlayerHandler playerHandler;
    private ScreenManager screenManager;
    private StateBar healthBar;
    private StateBar staminaBar;
    private StateBar manaBar;

    private static final ComponentMapper<HealthComponent> healthMapper = ComponentMapperHandler
	    .getHealthMapper();
    private static final ComponentMapper<StaminaComponent> staminaMapper = ComponentMapperHandler
	    .getStaminaMapper();
    private static final ComponentMapper<ManaComponent> manaMapper = ComponentMapperHandler
	    .getManaMapper();

    @SuppressWarnings("unchecked")
    public HealthSystem(ScreenManager screenManager, FactoryManager factoryManager) {
	super(Family.one(HealthComponent.class, StaminaComponent.class, ManaComponent.class).get(),
		Constants.Game.System.HEALTH_SYSTEM_PRIORITY);

	this.screenManager = screenManager;
	PlayerFactory _playerFactory = factoryManager.getFactory(PlayerFactory.class);
	playerHandler = _playerFactory.getPlayerHandler();
    }

    @Override
    public void update(float deltaTime) {
	if (healthBar == null) {
	    GameInterfaceScreen _interfaceScreen = screenManager
		    .getScreen(GameInterfaceScreen.class);
	    healthBar = _interfaceScreen.getHealthBar();
	    staminaBar = _interfaceScreen.getStaminaBar();
	    manaBar = _interfaceScreen.getManaBar();
	}

	super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
	if (playerHandler.getPlayer() == entity) { // Main player
	    HealthComponent _healthComponent = healthMapper.get(entity);
	    healthBar.setState(_healthComponent.getCurrent(), _healthComponent.getMax());

	    StaminaComponent _staminaComponent = staminaMapper.get(entity);
	    staminaBar.setState(_staminaComponent.getCurrent(), _staminaComponent.getMax());

	    ManaComponent _manaComponent = manaMapper.get(entity);
	    manaBar.setState(_manaComponent.getCurrent(), _manaComponent.getMax());
	}
	
	
    }
}