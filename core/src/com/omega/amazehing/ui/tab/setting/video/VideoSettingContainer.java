package com.omega.amazehing.ui.tab.setting.video;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.ObjectMap;
import com.omega.amazehing.Constants;
import com.omega.amazehing.setting.SettingManager;
import com.omega.amazehing.setting.event.video.DisplayModeSettingEvent;
import com.omega.amazehing.setting.event.video.ResolutionHeightSettingEvent;
import com.omega.amazehing.setting.event.video.ResolutionWidthSettingEvent;
import com.omega.amazehing.ui.tab.setting.SettingContainer;
import com.omega.amazehing.ui.tab.setting.video.DisplayModeItem.DisplayModeType;

public class VideoSettingContainer extends SettingContainer {

    private DisplayData displayData;

    private Label resolutionLabel;
    private SelectBox<VideoResolutionItem> resolutionValue;

    private Label displayModeLabel;
    private SelectBox<DisplayModeItem> displayModeValue;

    public VideoSettingContainer(SettingManager settingManager, I18NBundle i18n, Skin skin,
	    final ObjectMap<String, Object> changedSettings) {
	super(settingManager, i18n, skin, changedSettings);

	displayData = new DisplayData();

	defaults().expandX().padRight(10f).padBottom(5f);
	if (Gdx.app.getType() == ApplicationType.Desktop) {
	    resolutionLabel = new Label(i18n.get("settings.video.resolution"), skin);
	    resolutionValue = new SelectBox<VideoResolutionItem>(skin);
	    resolutionValue.addListener(new ChangeListener() {

		@Override
		public void changed(ChangeEvent event, Actor actor) {
		    VideoResolutionItem _resolution = resolutionValue.getSelected();
		    changedSettings.put(Constants.Settings.Video.TAG_SCREEN_WIDTH,
			    _resolution.getWidth());
		    changedSettings.put(Constants.Settings.Video.TAG_SCREEN_HEIGHT,
			    _resolution.getHeight());
		}
	    });

	    pad(10f).top().left().setFillParent(true);
	    add(resolutionLabel).left();
	    add(resolutionValue).fillX().minWidth(100f).right().row();

	    displayModeLabel = new Label(i18n.get("settings.video.displaymode"), skin);
	    displayModeValue = new SelectBox<DisplayModeItem>(skin);
	    displayModeValue.addListener(new ChangeListener() {

		@Override
		public void changed(ChangeEvent event, Actor actor) {
		    DisplayModeItem _item = displayModeValue.getSelected();
		    changedSettings.put(Constants.Settings.Video.TAG_DISPLAY_MODE,
			    _item.getDisplayModeType());
		}
	    });

	    pad(10f).top().left().setFillParent(true);
	    add(displayModeLabel).left();
	    add(displayModeValue).fillX().minWidth(100f).right().row();
	}
    }

    @Override
    public void initialize() {
	// Resolutions
	Array<VideoResolutionItem> _resolutions = new Array<VideoResolutionItem>();
	DisplayMode[] _displayModes = Gdx.graphics.getDisplayModes();
	for (int i = 0; i < _displayModes.length; i++) {
	    DisplayMode _displayMode = _displayModes[i];

	    if (_displayMode.bitsPerPixel == 32 && _displayMode.refreshRate == 60) {
		_resolutions.add(new VideoResolutionItem(_displayMode.width, _displayMode.height));
	    }
	}
	_resolutions.sort();
	resolutionValue.setItems(_resolutions);
	settingManager.setEvent(Constants.Settings.Video.TAG_SCREEN_WIDTH,
		new ResolutionWidthSettingEvent(displayData));
	settingManager.setEvent(Constants.Settings.Video.TAG_SCREEN_HEIGHT,
		new ResolutionHeightSettingEvent(resolutionValue, displayData));

	int _screenWidth = settingManager.getInt(Constants.Settings.Video.TAG_SCREEN_WIDTH);
	int _screenHeight = settingManager.getInt(Constants.Settings.Video.TAG_SCREEN_HEIGHT);
	boolean _found = false;
	Array<VideoResolutionItem> _resolutionItems = resolutionValue.getItems();
	for (VideoResolutionItem resolutionItem : _resolutionItems) {
	    int _hashCode = _screenWidth + _screenHeight * _screenWidth;
	    if (resolutionItem.hashCode() == _hashCode) {
		resolutionValue.setSelected(resolutionItem);
		_found = true;

		break;
	    }
	}

	if (!_found) {
	    VideoResolutionItem _resolutionItem = new VideoResolutionItem(_screenWidth,
		    _screenHeight);
	    _resolutionItems.add(_resolutionItem);
	    resolutionValue.setItems(_resolutionItems);
	    resolutionValue.setSelected(_resolutionItem);
	}

	// Display mode
	displayModeValue.setItems(
		new DisplayModeItem(DisplayModeType.WINDOWED, i18n
			.get("settings.video.displaymode.windowed")),
		new DisplayModeItem(DisplayModeType.FULLSCREEN, i18n
			.get("settings.video.displaymode.fullscreen")));
	settingManager.setEvent(Constants.Settings.Video.TAG_DISPLAY_MODE,
		new DisplayModeSettingEvent(displayModeValue, displayData));

	DisplayModeType _displayMode = settingManager
		.getDisplayModeType(Constants.Settings.Video.TAG_DISPLAY_MODE);
	Array<DisplayModeItem> _displayModeItems = displayModeValue.getItems();
	for (DisplayModeItem displayModeItem : _displayModeItems) {
	    if (displayModeItem.getDisplayModeType().equals(_displayMode)) {
		displayModeValue.setSelected(displayModeItem);

		break;
	    }
	}
    }

    public void updateDisplayMode() {
	if (displayData.hasChanged()) {
	    Gdx.graphics.setDisplayMode(displayData.getWidth(), displayData.getHeight(),
		    displayData.isFullscreen());

	    displayData.setHasChanged(false);
	}
    }
}