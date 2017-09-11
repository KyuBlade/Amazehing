package com.omega.amazehing.screen;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.gdx.extension.screen.BaseScreen;
import com.gdx.extension.screen.ScreenManager;
import com.gdx.extension.ui.panel.Panel;
import com.gdx.extension.ui.tab.Tab;
import com.gdx.extension.ui.tab.TabPane;
import com.gdx.extension.ui.tab.TabPane.TabPosition;
import com.omega.amazehing.setting.SettingManager;
import com.omega.amazehing.ui.tab.setting.SettingContainer;
import com.omega.amazehing.ui.tab.setting.control.ControlSettingContainer;
import com.omega.amazehing.ui.tab.setting.video.VideoSettingContainer;

public class SettingScreen extends BaseScreen {

    private SettingManager settingManager;

    private VideoSettingContainer videoContainer;
    private ControlSettingContainer controlContainer;

    private Tab videoTab;
    private Tab controlTab;

    private float tabsWidth = 100f;

    private Panel settingPanel;
    private TabPane tabPane;
    private TextButton backButton;
    private TextButton applyButton;

    private BaseScreen backScreen;

    private ObjectMap<String, Object> changedSettings = new ObjectMap<String, Object>();

    public SettingScreen(final ScreenManager screenManager, I18NBundle i18n,
	    SettingManager settingManager) {
	super(screenManager, 11);

	this.settingManager = settingManager;

	settingPanel = new Panel(skin);
	tabPane = new TabPane(TabPosition.LEFT, skin);
	videoContainer = new VideoSettingContainer(settingManager, i18n, skin, changedSettings);
	controlContainer = new ControlSettingContainer(screenManager, settingManager, i18n, skin,
		changedSettings);

	Container<Label> _videoTabContainer = new Container<Label>(
		new Label(i18n.get("settings.video.tab"), skin));
	_videoTabContainer.fillX();
	videoTab = new Tab(_videoTabContainer, videoContainer, skin) {

	    @Override
	    public float getPrefWidth() {
		return tabsWidth;
	    }
	};
	Container<Label> _controlsTabContainer = new Container<Label>(
		new Label(i18n.get("settings.controls.tab"), skin));
	controlTab = new Tab(_controlsTabContainer, controlContainer, skin) {

	    @Override
	    public float getPrefWidth() {
		return tabsWidth;
	    }
	};
	controlTab.addListener(new ClickListener(Buttons.LEFT) {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		controlContainer.focus();
	    }
	});

	tabPane.addTab(videoTab);
	tabPane.addTab(controlTab);

	tabPane.setCurrentTab(videoTab);

	backButton = new TextButton(i18n.get("common.back"), skin);
	backButton.padLeft(10f).padRight(10f).padTop(5f).padBottom(5f);
	applyButton = new TextButton(i18n.get("common.apply"), skin);
	applyButton.padLeft(10f).padRight(10f).padTop(5f).padBottom(5f);

	backButton.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		screenManager.hideScreen(SettingScreen.class);
		screenManager.showScreen(backScreen.getClass());
		reset();
	    }
	});
	applyButton.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		apply();
	    }
	});

	settingPanel.add(tabPane).expand().fill().colspan(2).row();
	settingPanel.defaults().pad(10f);
	settingPanel.add(backButton).left();
	settingPanel.add(applyButton).right();

	layout.add(settingPanel).width(700f).height(Value.percentHeight(0.70f, layout));

	Array<Tab> _tabs = tabPane.getTabs();
	for (int i = 0; i < _tabs.size; i++) {
	    Tab _tab = _tabs.get(i);
	    ((SettingContainer) _tab.getContainer()).initialize();
	}
    }

    private void reset() {
	for (String key : changedSettings.keys()) {
	    settingManager.reset(key);
	}
	changedSettings.clear();
    }

    private void apply() {
	for (Entry<String, Object> setting : changedSettings.entries()) {
	    settingManager.put(setting.key, setting.value);
	}
	changedSettings.clear();

	videoContainer.updateDisplayMode();

	settingManager.flush();
    }

    public SettingScreen setBackScreen(BaseScreen screen) {
	backScreen = screen;

	return this;
    }
}