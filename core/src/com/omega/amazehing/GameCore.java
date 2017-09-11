package com.omega.amazehing;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gdx.extension.screen.ScreenManager;
import com.omega.amazehing.game.level.LevelSetting;
import com.omega.amazehing.game.level.generator.GenerationAlgorithm;
import com.omega.amazehing.impl.CompatibilityHandler;
import com.omega.amazehing.screen.DebugScreen;
import com.omega.amazehing.screen.SettingScreen;
import com.omega.amazehing.screen.game.LoadingScreen;
import com.omega.amazehing.setting.SettingManager;
import com.omega.amazehing.util.Utils;

public class GameCore extends ApplicationAdapter {

    private AssetManager assetManager;
    private ScreenManager screenManager;
    private SettingManager settingManager;
    private CompatibilityHandler compatiblityHandler;
    private I18NBundle i18n;

    public GameCore(Preferences settings, CompatibilityHandler compatHandler) {
	this.compatiblityHandler = compatHandler;

	settingManager = new SettingManager(settings);
    }

    @Override
    public void create() {
	Gdx.app.setLogLevel(Application.LOG_DEBUG);

	settingManager.load();

	assetManager = new AssetManager();

	// Load skin
	assetManager.load("skin/default.json", Skin.class,
		new SkinLoader.SkinParameter("skin/default.atlas"));
	assetManager.load("lang/locale", I18NBundle.class);
	assetManager.finishLoading();
	Skin _skin = assetManager.get("skin/default.json", Skin.class);
	i18n = assetManager.get("lang/locale", I18NBundle.class);
	I18NBundle.setExceptionOnMissingKey(false);

	Stage _stage = new Stage(new ScreenViewport());
	InputMultiplexer _inputProcessor = new InputMultiplexer();
	screenManager = new ScreenManager(_stage, _skin, _inputProcessor);
	Gdx.input.setInputProcessor(_inputProcessor);

	screenManager.registerScreen(new DebugScreen(screenManager));
	// screenManager.registerScreen(new MainMenuScreen(screenManager));
	screenManager.registerScreen(new SettingScreen(screenManager, i18n, settingManager), false);
	LoadingScreen _loadScreen = screenManager
		.registerScreen(new LoadingScreen(screenManager, this));

	boolean _generate = false;
	if (_generate) {
	    _loadScreen.generate(new LevelSetting("Test", GenerationAlgorithm.RECURSIVE_BACKTRACKER,
		    6811638682L /* 67684547L */, 10, 10, Constants.Generator.CELL_SIZE));
	} else {
	    _loadScreen.loadSave("Test");
	}
    }

    @Override
    public void render() {
	Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	screenManager.render(Gdx.graphics.getDeltaTime());

	Utils.processPreRunnables();
    }

    @Override
    public void resize(int width, int height) {
	super.resize(width, height);

	screenManager.resize(width, height);
    }

    @Override
    public void dispose() {
	screenManager.dispose();
	assetManager.dispose();
    }

    public AssetManager getAssetManager() {
	return assetManager;
    }

    public ScreenManager getScreenManager() {
	return screenManager;
    }

    public SettingManager getSettingManager() {
	return settingManager;
    }

    public I18NBundle getI18n() {
	return i18n;
    }
}