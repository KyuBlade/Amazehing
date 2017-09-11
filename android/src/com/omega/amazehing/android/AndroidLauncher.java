package com.omega.amazehing.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.omega.amazehing.Constants;
import com.omega.amazehing.GameCore;

import android.os.Bundle;

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	Preferences _preferences = Gdx.app.getPreferences(Constants.Settings.FILE_NAME);
	GameCore _core = new GameCore(_preferences, new AndroidCompatibilityHandler());

	AndroidApplicationConfiguration _config = new AndroidApplicationConfiguration();
	initialize(_core, _config);
    }
}
