package com.omega.amazehing.ui.hotbar;

import java.util.Iterator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Values;
import com.badlogic.gdx.utils.SnapshotArray;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.item.ItemTypeComponent;
import com.omega.amazehing.ui.hotbar.HotbarSlot.HotbarSlotStyle;
import com.omega.amazehing.ui.inventory.InventoryItem;
import com.omega.amazehing.ui.skill.SkillItem;

public class Hotbar extends Table {

    private HotbarStyle style;

    private int slotCount;
    private boolean isVertical;
    private IntMap<HotbarSlot> slots;
    private DragAndDrop dragnDrop;

    private static final ComponentMapper<ItemTypeComponent> typeManager = ComponentMapperHandler
	    .getItemTypeMapper();

    public Hotbar(int slotCount, boolean isVertical, DragAndDrop dragnDrop, Skin skin) {
	this(slotCount, isVertical, dragnDrop, skin, "default");
    }

    public Hotbar(int slotCount, boolean isVertical, DragAndDrop dragnDrop, Skin skin,
	    String styleName) {
	this(slotCount, isVertical, dragnDrop, skin.get(styleName, HotbarStyle.class));
    }

    public Hotbar(int slotCount, boolean isVertical, DragAndDrop dragnDrop, HotbarStyle style) {
	this.slotCount = slotCount;
	this.isVertical = isVertical;
	this.dragnDrop = dragnDrop;

	defaults().size(50f);
	slots = new IntMap<HotbarSlot>(slotCount);
	for (int i = 0; i < slotCount; i++) {
	    HotbarSlot _slot = new HotbarSlot(style.slotStyle);
	    slots.put(i, _slot);
	    Cell<HotbarSlot> _cell = add(_slot);
	    if (isVertical) {
		_cell.row();
	    }
	    dragnDrop.addTarget(new Target(_slot) {

		@Override
		public void drop(Source source, Payload payload, float x, float y, int pointer) {
		    Actor _sourceActor = source.getActor();
		    if (_sourceActor instanceof HotbarInventoryItem) {
			HotbarInventoryItem _sourceItem = ((HotbarInventoryItem) _sourceActor);
			HotbarSlot _sourceSlot = (HotbarSlot) _sourceItem.getParent();
			HotbarSlot _targetSlot = (HotbarSlot) getActor();
			Actor _targetItem = _targetSlot.getActor();

			_targetSlot.setActor(_sourceItem);
			_sourceSlot.setActor(_targetItem);

		    } else if (_sourceActor instanceof InventoryItem) {
			InventoryItem _sourceItem = (InventoryItem) _sourceActor;
			HotbarSlot _targetSlot = (HotbarSlot) getActor();

			// Check if already bound
			SnapshotArray<Actor> _children = getChildren();
			Iterator<Actor> _iter = _children.iterator();
			while (_iter.hasNext()) {
			    HotbarSlot _slot = (HotbarSlot) _iter.next();
			    Actor _slotItem = _slot.getActor();
			    if (_slotItem instanceof HotbarInventoryItem) {
				HotbarInventoryItem _item = (HotbarInventoryItem) _slotItem;
				if (_item.getSource().equals(_sourceActor)) {
				    if (_targetSlot != null) {
					_slot.setActor(_targetSlot.getActor());
				    }
				    _targetSlot.setActor(_item);

				    return;
				}
			    }
			}

			_targetSlot.setActor(new HotbarInventoryItem(_sourceItem));
		    } else if (_sourceActor instanceof SkillItem) {
			SkillItem _sourceItem = (SkillItem) _sourceActor;
			HotbarSlot _targetSlot = (HotbarSlot) getActor();

			// Check if already bound
			SnapshotArray<Actor> _children = getChildren();
			Iterator<Actor> _iter = _children.iterator();
			while (_iter.hasNext()) {
			    HotbarSlot _slot = (HotbarSlot) _iter.next();
			    Actor _slotItem = _slot.getActor();
			    if (_slotItem instanceof HotbarSkillItem) {
				HotbarSkillItem _item = (HotbarSkillItem) _slotItem;
				if (_item.getSkill().equals(((SkillItem) _sourceActor).getSkill())) {
				    if (_targetSlot != null) {
					_slot.setActor(_targetSlot.getActor());
				    }
				    _targetSlot.setActor(_item);

				    return;
				}
			    }
			}

			_targetSlot.setActor(new HotbarSkillItem(_sourceItem));
		    }
		}

		@Override
		public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
		    return true;
		}
	    });
	}

	setStyle(style);
    }

    public void addItem(InventoryItem item) {
	HotbarSlot _emptySlot = getEmptySlot();
	if (_emptySlot == null)
	    return;

	_emptySlot.setActor(item);
    }

    public void removeItem(InventoryItem source) {
	Values<HotbarSlot> _slots = slots.values();
	for (HotbarSlot _slot : _slots) {
	    Actor _slotActor = _slot.getActor();
	    if (_slotActor instanceof HotbarInventoryItem) {
		HotbarInventoryItem _item = (HotbarInventoryItem) _slotActor;
		InventoryItem _source = _item.getSource();
		if (_source.equals(source)) {
		    _item.remove();
		}
	    }
	}
    }

    public IntMap<HotbarSlot> getSlots() {
	return slots;
    }

    private HotbarSlot getEmptySlot() {
	Values<HotbarSlot> _slots = slots.values();
	for (HotbarSlot _slot : _slots) {
	    if (_slot.getActor() == null) {
		return _slot;
	    }
	}

	return null;
    }

    public HotbarStyle getStyle() {
	return style;
    }

    public void setStyle(HotbarStyle style) {
	this.style = style;

	setBackground(style.background);

	SnapshotArray<Actor> _children = getChildren();
	for (int i = 0; i < _children.size; i++) {
	    HotbarSlot _child = (HotbarSlot) _children.get(i);
	    _child.setStyle(style.slotStyle);
	}

    }

    static public class HotbarStyle {

	public Drawable background;
	public HotbarSlotStyle slotStyle;

	public HotbarStyle() {
	}

	public HotbarStyle(Drawable background, HotbarSlotStyle slotStyle) {
	    this.background = background;
	    this.slotStyle = slotStyle;
	}

	public HotbarStyle(HotbarStyle style) {
	    this.background = style.background;
	    this.slotStyle = style.slotStyle;
	}

    }

}
