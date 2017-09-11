package com.omega.amazehing.screen.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.gdx.extension.screen.BaseScreen;
import com.gdx.extension.screen.ScreenManager;
import com.gdx.extension.ui.panel.Panel;
import com.omega.amazehing.GameCore;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.system.input.GameInputSystem;
import com.omega.amazehing.game.entity.system.input.GameMenuInputSystem;
import com.omega.amazehing.screen.MainMenuScreen;
import com.omega.amazehing.screen.SettingScreen;

public class GameMenuScreen extends BaseScreen {

    private Panel menuPanel;
    private TextButton resume;
    private TextButton restart;
    private TextButton settings;
    private TextButton exit;

    public GameMenuScreen(final ScreenManager screenManager, final GameCore core, final EntityEngine engine) {
	super(screenManager, 90);

	I18NBundle _i18n = core.getI18n();

	layout.setBackground(skin.getDrawable("backgrounds/black_alpha"));
	layout.setTouchable(Touchable.enabled);

	menuPanel = new Panel(skin);
	resume = new TextButton(_i18n.get("menu.game.resume"), skin);
	restart = new TextButton(_i18n.get("menu.game.restart"), skin);
	settings = new TextButton(_i18n.get("menu.settings"), skin);
	exit = new TextButton(_i18n.get("menu.game.exit"), skin);

	resume.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		engine.getSystem(GameMenuInputSystem.class).setProcessing(false);
		engine.getSystem(GameInputSystem.class).setProcessing(true);
		screenManager.hideScreen(GameMenuScreen.class);
	    }
	});
	restart.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		screenManager.getScreen(GameScreen.class).reload();
	    }
	});
	settings.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		screenManager.hideScreen(GameMenuScreen.class);
		screenManager.getScreen(SettingScreen.class).setBackScreen(GameMenuScreen.this);
		screenManager.showScreen(SettingScreen.class);
	    }
	});
	exit.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		screenManager.unregisterScreen(GameScreen.class);
		screenManager.registerScreen(new MainMenuScreen(screenManager, core));
	    }
	});

	menuPanel.defaults().width(200f).spaceBottom(10f);
	menuPanel.add(resume).row();
	menuPanel.add(restart).row();
	menuPanel.add(settings).row();
	menuPanel.add(exit).row();
	menuPanel.pad(50f, 25f, 50f, 25f);

	layout.add(menuPanel);
    }
}