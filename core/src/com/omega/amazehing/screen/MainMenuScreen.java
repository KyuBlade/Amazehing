package com.omega.amazehing.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.gdx.extension.screen.BaseScreen;
import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.GameCore;

public class MainMenuScreen extends BaseScreen {

    protected TextButton newButton;
    protected TextButton continueButton;
    protected TextButton settingButton;
    protected TextButton exitButton;

    private InputAdapter debugInput;

    public MainMenuScreen(final ScreenManager screenManager, final GameCore core) {
	super(screenManager, 1);

	I18NBundle _i18n = core.getI18n();

	screenManager.registerScreen(new LevelSelectionScreen(screenManager, core), false);
	screenManager.registerScreen(new LevelCreationScreen(screenManager, core), false);

	debugInput = new InputAdapter() {

	    @Override
	    public boolean keyUp(int keycode) {
		switch (keycode) {
		    case Keys.F5:
			screenManager.unregisterScreen(MainMenuScreen.this);
			screenManager.registerScreen(new MainMenuScreen(screenManager, core));

			break;
		}

		return true;
	    }
	};

	layout.defaults().spaceBottom(10f).size(250f, 25f);

	newButton = new TextButton(_i18n.get("menu.main.newgame"), skin);
	newButton.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		screenManager.showScreen(LevelCreationScreen.class);
		screenManager.hideScreen(MainMenuScreen.class);
	    }
	});
	layout.add(newButton).size(250f, 25f).row();

	continueButton = new TextButton(_i18n.get("menu.main.continue"), skin);
	continueButton.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		screenManager.hideScreen(MainMenuScreen.class);
		screenManager.showScreen(LevelSelectionScreen.class);
	    }
	});
	layout.add(continueButton).row();

	settingButton = new TextButton(_i18n.get("menu.settings"), skin);
	settingButton.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		hide();
		screenManager.getScreen(SettingScreen.class).setBackScreen(MainMenuScreen.this);
		screenManager.showScreen(SettingScreen.class);
	    }
	});
	layout.add(settingButton).row();

	exitButton = new TextButton(_i18n.get("menu.main.exit"), skin);
	exitButton.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		Gdx.app.exit();
	    }
	});
	layout.add(exitButton).row();
    }

    @Override
    protected void show() {
	super.show();

	screenManager.getInputProcessor().addProcessor(debugInput);
    }

    @Override
    protected void hide() {
	super.hide();

	screenManager.getInputProcessor().removeProcessor(debugInput);
    }

    @Override
    public void dispose() {
	screenManager.getInputProcessor().removeProcessor(debugInput);
	
	screenManager.unregisterScreen(LevelCreationScreen.class);
	screenManager.unregisterScreen(LevelSelectionScreen.class);
    }
}