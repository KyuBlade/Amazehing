package com.omega.amazehing.screen.game;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.extension.screen.BaseScreen;
import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.factory.entity.ItemFactory.ItemType;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.IdentityComponent;
import com.omega.amazehing.game.entity.component.item.ItemTypeComponent;
import com.omega.amazehing.ui.Window;
import com.omega.amazehing.ui.character.CharacterItem;
import com.omega.amazehing.ui.character.CharacterSlot;
import com.omega.amazehing.ui.inventory.InventoryItem;
import com.omega.amazehing.ui.inventory.InventorySlot;

public class CharacterScreen extends BaseScreen {

    private EntityEngine engine;

    private ComponentMapper<IdentityComponent> idMapper = ComponentMapperHandler
	    .getIdentityMapper();
    private ComponentMapper<ItemTypeComponent> typeMapper = ComponentMapperHandler
	    .getItemTypeMapper();

    private ObjectMap<CharacterSlotType, CharacterItem> equipedItems;

    private Window window;
    private DragAndDrop dragnDrop;

    private CharacterSlot headSlot;
    private CharacterSlot necklaceSlot;
    private CharacterSlot leftRingSlot;
    private CharacterSlot rightRingSlot;
    private CharacterSlot leftWeaponSlot;
    private CharacterSlot bodySlot;
    private CharacterSlot rightWeaponSlot;
    private CharacterSlot legsSlot;
    private CharacterSlot feetSlot;

    private float minWidth = 250f;
    private float minHeight = 400f;
    private float maxWidth = 300f;
    private float maxHeight = 600f;
    private float windowScaleWidth = 0.4f;
    private float windowScaleHeight = 0.8f;
    private float padLeft = 20f;

    public enum CharacterSlotType {
	HEAD, NECKLACE, ARMOR, LEFT_RING, RIGHT_RING, LEFT_WEAPON, RIGHT_WEAPON, LEGS, FEET;
    }

    public CharacterScreen(ScreenManager screenManager, I18NBundle i18n, EntityEngine engine,
	    Table parentLayout, DragAndDrop dragnDrop) {
	super(screenManager, 14);

	this.dragnDrop = dragnDrop;
	this.engine = engine;

	equipedItems = new ObjectMap<CharacterScreen.CharacterSlotType, CharacterItem>(
		CharacterSlotType.values().length);

	headSlot = createSlot(CharacterSlotType.HEAD, "head");
	necklaceSlot = createSlot(CharacterSlotType.NECKLACE, "necklace");
	leftRingSlot = createSlot(CharacterSlotType.LEFT_RING, "ring");
	rightRingSlot = createSlot(CharacterSlotType.RIGHT_RING, "ring");
	leftWeaponSlot = createSlot(CharacterSlotType.LEFT_WEAPON, "weapon");
	bodySlot = createSlot(CharacterSlotType.ARMOR, "armor");
	rightWeaponSlot = createSlot(CharacterSlotType.RIGHT_WEAPON, "weapon");
	legsSlot = createSlot(CharacterSlotType.LEGS, "legs");
	feetSlot = createSlot(CharacterSlotType.FEET, "feet");

	window = new Window(i18n.get("character.title"), skin);
	window.top().pad(10f);
	window.defaults().size(40f).expandX().pad(5f);
	window.add(headSlot).colspan(3).row();
	window.add(necklaceSlot).colspan(3).row();
	window.add(leftRingSlot);
	window.add();
	window.add(rightRingSlot).row();
	window.add(leftWeaponSlot);
	window.add(bodySlot);
	window.add(rightWeaponSlot).row();
	window.add(legsSlot).colspan(3).row();
	window.add(feetSlot).colspan(3).row();
	window.top();

	parentLayout.addActor(window);
	window.setVisible(false);

	updateSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	window.setPosition(padLeft, Gdx.graphics.getHeight() * 0.5f, Align.left);
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

    public boolean equipItem(CharacterSlotType slotType, InventoryItem item) {
	boolean _isEquiped = equipItem(slotType, item.getItem());
	if (_isEquiped) {
	    InventoryScreen _invScreen = screenManager.getScreen(InventoryScreen.class);
	    _invScreen.removeItem(item);
	}

	return _isEquiped;
    }

    public boolean equipItem(InventoryItem item) {
	if (item == null) {
	    return false;
	}

	boolean _isEquiped = equipItem(item.getItem());
	if (_isEquiped) {
	    InventoryScreen _invScreen = screenManager.getScreen(InventoryScreen.class);
	    _invScreen.removeItem(item);

	    GameInterfaceScreen _hudScreen = screenManager.getScreen(GameInterfaceScreen.class);
	    _hudScreen.getHotbar().removeItem(item);
	}

	return _isEquiped;
    }

    public boolean equipItem(Entity item) {
	if (item == null) {
	    return false;
	}

	ItemTypeComponent _typeComp = typeMapper.get(item);
	ItemType _itemType = _typeComp.getType();
	CharacterSlotType _slotType = null;
	switch (_itemType) {
	    case ANY:
		return false;
	    case ARMOR:
		_slotType = CharacterSlotType.ARMOR;
		break;
	    case FEET:
		_slotType = CharacterSlotType.FEET;
		break;
	    case HEAD:
		_slotType = CharacterSlotType.HEAD;
		break;
	    case LEGS:
		_slotType = CharacterSlotType.LEGS;
		break;
	    case NECKLACE:
		_slotType = CharacterSlotType.NECKLACE;
		break;
	    case RING:
		CharacterItem _leftRing = equipedItems.get(CharacterSlotType.LEFT_RING);
		CharacterItem _rightRing = equipedItems.get(CharacterSlotType.RIGHT_RING);
		if (_leftRing == null || _rightRing != null) {
		    _slotType = CharacterSlotType.LEFT_RING;
		} else {
		    _slotType = CharacterSlotType.RIGHT_RING;
		}
		break;
	    case WEAPON:
		CharacterItem _leftWeapon = equipedItems.get(CharacterSlotType.LEFT_WEAPON);
		CharacterItem _rightWeapon = equipedItems.get(CharacterSlotType.RIGHT_WEAPON);
		if (_leftWeapon == null || _rightWeapon != null) {
		    _slotType = CharacterSlotType.LEFT_WEAPON;
		} else {
		    _slotType = CharacterSlotType.RIGHT_WEAPON;
		}
		break;
	    default:
		return false;
	}

	return equipItem(_slotType, item);
    }

    public boolean equipItem(CharacterSlotType slotType, Entity item) {
	if (slotType == null || item == null) {
	    return false;
	}

	ItemType _itemType = typeMapper.get(item).getType();
	switch (_itemType) {
	    case ANY:
		return false;
	    case ARMOR:
		if (slotType != CharacterSlotType.ARMOR) {
		    return false;
		}
		break;
	    case FEET:
		if (slotType != CharacterSlotType.FEET) {
		    return false;
		}
		break;
	    case HEAD:
		if (slotType != CharacterSlotType.HEAD) {
		    return false;
		}
		break;
	    case LEGS:
		if (slotType != CharacterSlotType.LEGS) {
		    return false;
		}
		break;
	    case NECKLACE:
		if (slotType != CharacterSlotType.NECKLACE) {
		    return false;
		}
		break;
	    case RING:
		if (slotType != CharacterSlotType.LEFT_RING && slotType != CharacterSlotType.RIGHT_RING) {
		    return false;
		}
		break;
	    case WEAPON:
		if (slotType != CharacterSlotType.LEFT_WEAPON && slotType != CharacterSlotType.RIGHT_WEAPON) {
		    return false;
		}
		break;
	    default:
		return false;
	}
	// TODO : Check for level
	CharacterSlot _slot = null;
	switch (slotType) {
	    case ARMOR:
		_slot = bodySlot;
		break;
	    case FEET:
		_slot = feetSlot;
		break;
	    case HEAD:
		_slot = headSlot;
		break;
	    case LEFT_RING:
		_slot = leftRingSlot;
		break;
	    case LEFT_WEAPON:
		_slot = leftWeaponSlot;
		break;
	    case LEGS:
		_slot = legsSlot;
		break;
	    case NECKLACE:
		_slot = necklaceSlot;
		break;
	    case RIGHT_RING:
		_slot = rightRingSlot;
		break;
	    case RIGHT_WEAPON:
		_slot = rightWeaponSlot;
		break;
	    default:
		return false;

	}

	CharacterItem _currentItem = equipedItems.get(slotType);
	CharacterItem _newItem = createCharacterItem(item);
	equipedItems.put(slotType, _newItem);
	_slot.setCharacterItem(_newItem);

	if (_currentItem != null) {
	    InventoryScreen _invScreen = screenManager.getScreen(InventoryScreen.class);
	    if (_invScreen == null) {
		return false;
	    }
	    _invScreen.addItem(_currentItem.getItem(), 1);
	}

	return true;
    }

    public boolean unequipItem(CharacterItem item) {
	if (item == null) {
	    return false;
	}

	CharacterSlot _slot = (CharacterSlot) item.getParent();
	if (_slot == null) {
	    return false;
	}

	equipedItems.remove(_slot.getAllowedType());
	_slot.setCharacterItem(null);

	InventoryScreen _invScreen = screenManager.getScreen(InventoryScreen.class);
	_invScreen.addItem(item.getItem(), 1);

	return true;
    }

    private CharacterSlot createSlot(CharacterSlotType type, String styleName) {
	CharacterSlot _slot = new CharacterSlot(type, skin, styleName);
	dragnDrop.addTarget(new Target(_slot) {

	    @Override
	    public void drop(Source source, Payload payload, float x, float y, int pointer) {
		InventorySlot _sourceSlot = (InventorySlot) source.getActor().getParent();
		InventoryItem _sourceItem = _sourceSlot.getActor();
		CharacterSlot _targetSlot = (CharacterSlot) getActor();
		CharacterItem _targetItem = _targetSlot.getActor();

		equipItem(_targetSlot.getAllowedType(), _sourceItem);
	    }

	    @Override
	    public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
		return true;
	    }
	});

	return _slot;
    }

    private CharacterItem createCharacterItem(Entity item) {
	final CharacterItem _charItem = new CharacterItem(item);
	_charItem.addListener(new ClickListener(Buttons.RIGHT) {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		Gdx.app.debug("Unequip", "Unequiped : " + unequipItem(_charItem));
	    }
	});

	return _charItem;
    }

    @Override
    protected void show() {
	super.show();

	window.toFront();
	window.setVisible(true);
    }

    @Override
    protected void hide() {
	super.hide();

	window.setVisible(false);
    }
}