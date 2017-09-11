package com.omega.amazehing.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.gdx.extension.screen.BaseScreen;
import com.gdx.extension.screen.ScreenManager;
import com.gdx.extension.ui.tab.Tab;
import com.gdx.extension.ui.tab.TabPane;
import com.gdx.extension.ui.tab.TabPane.TabPosition;
import com.omega.amazehing.factory.entity.SkillFactory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.ui.Window;
import com.omega.amazehing.ui.skill.tab.AttackSkillTab;
import com.omega.amazehing.ui.skill.tab.DefenseSkillTab;
import com.omega.amazehing.ui.skill.tab.SupportSkillTab;

public class SkillScreen extends BaseScreen {

    private DragAndDrop dragnDrop;
    private EntityEngine engine;
    private Window window;
    private TabPane tabpane;
    private Tab attackTab;
    private Tab defenseTab;
    private Tab supportTab;

    private float minWidth = 400f;
    private float minHeight = 300f;
    private float maxWidth = 800f;
    private float maxHeight = 600f;
    private float windowScaleWidth = 0.4f;
    private float windowScaleHeight = 0.8f;

    public SkillScreen(ScreenManager screenManager, I18NBundle i18n, EntityEngine engine,
	    SkillFactory skillFactory, Table parentLayout, DragAndDrop dragnDrop) throws Exception {
	super(screenManager, 14);

	this.dragnDrop = dragnDrop;
	this.engine = engine;

	tabpane = new TabPane(TabPosition.LEFT, skin);

	window = new Window(i18n.get("skills.title"), skin);
	window.top().pad(10f).add(tabpane);

	attackTab = new Tab(new Label("Attack", skin), new AttackSkillTab(dragnDrop, skin,
		skillFactory), skin);
	defenseTab = new Tab(new Label("Defense", skin), new DefenseSkillTab(skin), skin);
	supportTab = new Tab(new Label("Support", skin), new SupportSkillTab(skin), skin);

	tabpane.addTab(attackTab);
	tabpane.addTab(defenseTab);
	tabpane.addTab(supportTab);
	tabpane.setCurrentTab(0);

	parentLayout.addActor(window);
	window.setVisible(false);

	updateSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	window.setPosition(Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.5f,
		Align.center);
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