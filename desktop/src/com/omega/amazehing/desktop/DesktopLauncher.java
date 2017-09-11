package com.omega.amazehing.desktop;

import java.io.File;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.backends.lwjgl.LwjglPreferences;
import com.omega.amazehing.Constants;
import com.omega.amazehing.GameCore;
import com.omega.amazehing.setting.SettingManager;
import com.omega.amazehing.ui.tab.setting.video.DisplayModeItem.DisplayModeType;

public class DesktopLauncher {

    public static void main(String[] arg) {
	Preferences _preferences = new LwjglPreferences(new LwjglFileHandle(new File(
		Constants.Settings.DIRECTORY, Constants.Settings.FILE_NAME), FileType.Local));
	GameCore _core = new GameCore(_preferences, new DesktopCompatibilityHandler());

	SettingManager _settingManager = _core.getSettingManager();
	LwjglApplicationConfiguration _config = new LwjglApplicationConfiguration();
	_config.title = Constants.GAME_NAME;
	_config.foregroundFPS = 0;
	_config.width = _settingManager.getInt(Constants.Settings.Video.TAG_SCREEN_WIDTH);
	_config.height = _settingManager.getInt(Constants.Settings.Video.TAG_SCREEN_HEIGHT);
	_config.fullscreen = _settingManager.getDisplayModeType(
		Constants.Settings.Video.TAG_DISPLAY_MODE).equals(DisplayModeType.FULLSCREEN);
	_config.vSyncEnabled = _settingManager.getBoolean(Constants.Settings.Video.TAG_VERTICAL_SYNC);
	new LwjglApplication(_core, _config);
    }
}
