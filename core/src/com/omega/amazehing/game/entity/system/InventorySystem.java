package com.omega.amazehing.game.entity.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;
import com.gdx.extension.screen.ScreenManager;
import com.mensa.database.sqlite.core.SQLiteException;
import com.omega.amazehing.Constants;
import com.omega.amazehing.factory.entity.ItemFactory;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.IdentityComponent;
import com.omega.amazehing.game.entity.component.item.InventoryComponent;
import com.omega.amazehing.game.entity.component.item.InventoryOperationComponent;
import com.omega.amazehing.game.entity.component.item.QuantityComponent;
import com.omega.amazehing.screen.game.InventoryScreen;

public class InventorySystem extends IteratingSystem {

    private static final Logger logger = LoggerFactory.getLogger(InventorySystem.class);

    private ScreenManager screenManager;
    private InventoryScreen inventoryScreen;
    private ItemFactory itemFactory;

    private ComponentMapper<InventoryOperationComponent> operationMapper = ComponentMapperHandler
	    .getInventoryOperationMapper();
    private ComponentMapper<InventoryComponent> inventoryMapper = ComponentMapperHandler
	    .getInventoryMapper();
    private ComponentMapper<IdentityComponent> identityMapper = ComponentMapperHandler
	    .getIdentityMapper();
    private ComponentMapper<QuantityComponent> quantityMapper = ComponentMapperHandler
	    .getQuantityMapper();

    @SuppressWarnings("unchecked")
    public InventorySystem(ScreenManager screenManager, ItemFactory itemFactory) {
	super(Family.all(InventoryOperationComponent.class).get(),
		Constants.Game.System.INVENTORY_SYSTEM_PRIORITY);

	this.screenManager = screenManager;
	inventoryScreen = screenManager.getScreen(InventoryScreen.class);
	this.itemFactory = itemFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
	InventoryOperationComponent _operationComp = operationMapper.get(entity);
	InventoryComponent _inventoryComp = inventoryMapper.get(entity);
	Array<Entity> _stored = _inventoryComp.getStored();

	switch (_operationComp.getOperation()) {
	    case ADD:
		IdentityComponent _identityComp = identityMapper.get(entity);
		QuantityComponent _quantityComp = quantityMapper.get(entity);
		//addItem(_identityComp.getIdentity(), _quantityComp.getQuantity());

		break;
	    case REMOVE:
		_identityComp = identityMapper.get(entity);
		_quantityComp = quantityMapper.get(entity);
		removeItem(_identityComp.getIdentity(), _quantityComp.getQuantity());

		break;
	    case SORT:
		break;
	    default:
		break;
	}
    }

    private void addItem(long itemId, int quantity) throws Exception {
	Entity _itemTemplate;
	try {
	    _itemTemplate = itemFactory.getItem(itemId);
	} catch (SQLiteException e) {
	    logger.warn("Unable to add item {}", itemId);
	    return;
	}
    }

    private void removeItem(long itemId, int quantity) {

    }
}