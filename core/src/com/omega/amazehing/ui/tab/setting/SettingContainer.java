package com.omega.amazehing.ui.tab.setting;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.extension.ui.tab.TabContainer;
import com.omega.amazehing.setting.SettingManager;

public abstract class SettingContainer extends TabContainer {

    protected SettingManager settingManager;
    protected I18NBundle i18n;
    protected ObjectMap<String, Object> changedSettings;

    public SettingContainer(SettingManager settingManager, I18NBundle i18n, Skin skin,
	    ObjectMap<String, Object> changedSettings) {
	super(skin);

	this.settingManager = settingManager;
	this.i18n = i18n;
	this.changedSettings = changedSettings;
    }

    public abstract void initialize();
}