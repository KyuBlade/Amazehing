package com.omega.amazehing.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.omega.amazehing.game.entity.component.DebugComponent.DebugType;
import com.omega.amazehing.util.DebugManager;

public class DebugWindow extends Window {

    private CheckBox physCheck;
    private CheckBox uiCheck;
    private CheckBox aiCheck;
    private CheckBox pagingCheck;
    private CheckBox roomCheck;

    public DebugWindow(Skin skin, I18NBundle i18n) {
	super(i18n.get("debug.title"), skin);

	left().pad(10f).top().defaults().left();
	physCheck = new CheckBox(i18n.get("debug.phys"), skin);
	physCheck.setChecked(DebugManager.getInstance().get(DebugType.PHYS_BODY));
	physCheck.addListener(new ChangeListener() {

	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		DebugManager.getInstance().set(DebugType.PHYS_BODY, physCheck.isChecked());
	    }
	});
	add(physCheck).row();

	uiCheck = new CheckBox(i18n.get("debug.ui"), skin);
	uiCheck.setChecked(DebugManager.getInstance().get(DebugType.UI));
	uiCheck.addListener(new ChangeListener() {

	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		boolean _isActive = uiCheck.isChecked();
		DebugManager.getInstance().set(DebugType.UI, _isActive);
		DebugWindow.this.getStage().setDebugAll(_isActive);
	    }
	});
	add(uiCheck).row();

	aiCheck = new CheckBox(i18n.get("debug.ai"), skin);
	aiCheck.setChecked(DebugManager.getInstance().get(DebugType.AI));
	aiCheck.addListener(new ChangeListener() {

	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		DebugManager.getInstance().set(DebugType.AI, aiCheck.isChecked());
	    }
	});
	add(aiCheck).row();

	pagingCheck = new CheckBox(i18n.get("debug.paging"), skin);
	pagingCheck.setChecked(DebugManager.getInstance().get(DebugType.PAGING));
	pagingCheck.addListener(new ChangeListener() {

	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		DebugManager.getInstance().set(DebugType.PAGING, pagingCheck.isChecked());
	    }
	});
	add(pagingCheck).row();

	roomCheck = new CheckBox(i18n.get("debug.room"), skin);
	roomCheck.setChecked(DebugManager.getInstance().get(DebugType.ROOM));
	roomCheck.addListener(new ChangeListener() {

	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		DebugManager.getInstance().set(DebugType.ROOM, roomCheck.isChecked());
	    }
	});
	add(roomCheck).row();

	setSize(200f, 300f);
	pack();
    }
}