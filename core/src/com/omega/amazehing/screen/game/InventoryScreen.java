package com.omega.amazehing.screen.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.gdx.extension.screen.BaseScreen;
import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.factory.entity.ItemFactory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.IdentityComponent;
import com.omega.amazehing.game.entity.component.item.StackableComponent;
import com.omega.amazehing.ui.Window;
import com.omega.amazehing.ui.inventory.InventoryItem;
import com.omega.amazehing.ui.inventory.InventorySlot;
import com.omega.amazehing.ui.inventory.InventorySlotTarget;

public class InventoryScreen extends BaseScreen {

    private static Logger logger = LoggerFactory.getLogger(InventoryScreen.class);

    private static final ComponentMapper<IdentityComponent> idMapper = ComponentMapperHandler
	    .getIdentityMapper();
    private static final ComponentMapper<StackableComponent> stackableMapper = ComponentMapperHandler
	    .getStackableMapper();

    private EntityEngine engine;
    private ItemFactory itemFactory;

    private Window window;
    private DragAndDrop dragnDrop;
    private Array<InventoryItem> items;

    private final int xCount = 5;
    private final int yCount = 7;

    private float minWidth = 275f;
    private float minHeight = 400f;
    private float maxWidth = 300f;
    private float maxHeight = 450f;
    private float windowScaleWidth = 0.4f;
    private float windowScaleHeight = 0.8f;
    private float padRight = 20f;

    public InventoryScreen(final ScreenManager screenManager, I18NBundle i18n, EntityEngine engine,
	    ItemFactory itemFactory, Table parentLayout, DragAndDrop dragnDrop) {
	super(screenManager, 14);

	layout.setTouchable(Touchable.childrenOnly);

	this.engine = screenManager.getScreen(GameScreen.class).getEntityEngine();
	this.dragnDrop = dragnDrop;
	this.itemFactory = itemFactory;

	window = new Window(i18n.get("inventory.title"), skin) {

	    @Override
	    public void onClose() {
		screenManager.hideScreen(InventoryScreen.this);
	    }
	};
	window.top().defaults().space(2f).size(45f);

	items = new Array<InventoryItem>(xCount * yCount);
	for (int y = 0; y < yCount; y++) {
	    for (int x = 0; x < xCount; x++) {
		final InventorySlot _invSlot = new InventorySlot(skin);
		window.add(_invSlot);
		dragnDrop.addTarget(new InventorySlotTarget(_invSlot));
	    }
	    window.row();
	}

	parentLayout.addActor(window);
	window.setVisible(false);
	updateSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	window.setPosition(Gdx.graphics.getWidth() - padRight, Gdx.graphics.getHeight() * 0.5f,
		Align.right);

	for (int i = 0; i < 4; i++) {
	    addItem(1, 1);
	}
    }

    @Override
    public void resize(int width, int height) {
	updateSize(width, height);
    }

    private void updateSize(int width, int height) {
	float _width = width * windowScaleWidth;
	if (_width > maxWidth) {
	    _width = maxWidth;
	} else if (_width < minWidth) {
	    _width = minWidth;
	}
	float _height = height * windowScaleHeight;
	if (_height > maxHeight) {
	    _height = maxHeight;
	} else if (_height < minHeight) {
	    _height = minHeight;
	}
	window.setSize(_width, _height);
    }

    public boolean addItem(Entity item, int quantity) {
	if (item == null) {
	    return false;
	}

	boolean _isAdded = false;
	boolean _isStackable = stackableMapper.get(item) != null;
	if (_isStackable) { // Find existing item in the inventory
	    _isAdded = addStack(item, quantity);
	} else { // Add to an empty slot
	    _isAdded = addDistinct(item, quantity);
	}

	if (!_isAdded) {
	    logger.info("No slot found");
	}

	return _isAdded;
    }

    public boolean addItem(long itemId, int quantity) {
	Entity _item;
	try {
	    _item = itemFactory.getItem(itemId);
	} catch (Exception e) {
	    logger.warn("Item {} not found.\n {}", itemId, e);

	    return false;
	}

	return addItem(_item, quantity);
    }

    private boolean addStack(Entity item, int quantity) {
	InventoryItem _invItem = getInventoryItemFor(item);
	if (_invItem != null) {
	    int _quantity = _invItem.getQuantity();
	    _quantity += quantity;
	    _invItem.setQuantity(_quantity);
	} else {
	    _invItem = new InventoryItem(item, quantity, dragnDrop, skin);
	    InventorySlot _emptySlot = getEmptySlot();
	    if (_emptySlot == null) {
		return false;
	    }

	    _emptySlot.setActor(_invItem);
	    items.add(_invItem);
	}

	return true;
    }

    private boolean addDistinct(Entity item, int quantity) {
	if (quantity > 1) {
	    idMapper.get(item).getIdentity();
	}

	for (int q = 0; q < quantity; q++) {
	    InventorySlot _emptySlot = (InventorySlot) getEmptySlot();
	    if (_emptySlot == null) {
		return false;
	    }

	    InventoryItem _invItem = new InventoryItem(item, 1, dragnDrop, skin);
	    _emptySlot.setActor(_invItem);
	    items.add(_invItem);
	}

	return true;
    }

    public boolean removeItem(Entity item, int quantity) {
	IdentityComponent _idComp = idMapper.get(item);
	long _itemId = _idComp.getIdentity();

	return removeItem(_itemId, quantity);
    }

    public boolean removeItem(long itemId, int quantity) {
	InventoryItem _invItem = getInventoryItemFor(itemId);
	if (_invItem == null) {
	    return false;
	}
	boolean _isStackable = stackableMapper.get(_invItem.getItem()) != null;

	if (_isStackable) {
	    int _currentQuantity = _invItem.getQuantity();
	    int _newQuantity = _currentQuantity - quantity;
	    if (_newQuantity <= 0) {
		_invItem.remove();
		items.removeValue(_invItem, true);
	    }
	} else {
	    for (int i = 0; i < quantity; i++) {
		_invItem = getInventoryItemFor(itemId);
		if (_invItem != null) {
		    _invItem.remove();
		    items.removeValue(_invItem, true);
		} else {
		    return false;
		}
	    }
	}

	return true;
    }

    public boolean removeItem(InventoryItem item) {
	boolean _isRemoved = items.removeValue(item, true);
	if (_isRemoved) {
	    item.remove();
	    GameInterfaceScreen _hudScreen = screenManager.getScreen(GameInterfaceScreen.class);
	    _hudScreen.getHotbar().removeItem(item);
	}

	return _isRemoved;
    }

    private InventoryItem getInventoryItemFor(Entity item) {
	IdentityComponent _idComp = idMapper.get(item);
	long _itemId = _idComp.getIdentity();

	return getInventoryItemFor(_itemId);
    }

    private InventoryItem getInventoryItemFor(long itemId) {
	for (int i = items.size - 1; i >= 0; i--) {
	    InventoryItem _invItem = items.get(i);
	    long _itemId = idMapper.get(_invItem.getItem()).getIdentity();
	    if (_itemId == itemId) {
		return _invItem;
	    }
	}

	return null;
    }

    @SuppressWarnings("rawtypes")
    private InventorySlot getEmptySlot() {
	Array<Cell> _invCells = window.getCells();
	for (int i = 0; i < _invCells.size; i++) { // Find empty slot
	    InventorySlot _invSlot = (InventorySlot) _invCells.get(i).getActor();
	    if (_invSlot.getActor() == null) {
		return _invSlot;
	    }
	}

	return null;
    }

    public Array<InventoryItem> getItems() {
	return items;
    }

    @Override
    protected void show() {
	super.show();

	window.setVisible(true);
	window.toFront();
    }

    @Override
    protected void hide() {
	super.hide();

	window.setVisible(false);
    }
}