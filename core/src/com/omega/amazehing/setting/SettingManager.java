package com.omega.amazehing.setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.gdx.extension.ui.input.InputArray;
import com.omega.amazehing.Constants;
import com.omega.amazehing.ui.tab.setting.video.DisplayModeItem.DisplayModeType;

public class SettingManager {

    private static final Logger logger = LoggerFactory.getLogger(SettingManager.class);

    private Preferences settingFile;
    private ObjectMap<String, SettingEntry<?>> settings;

    public SettingManager(Preferences preferences) {
	settingFile = preferences;
	settings = new ObjectMap<String, SettingEntry<?>>();

	preload();
    }

    public void preload() {
	int _screenWidth = settingFile.getInteger(Constants.Settings.Video.TAG_SCREEN_WIDTH,
		Constants.Settings.Video.DEFAULT_SCREEN_WIDTH);
	int _screenHeight = settingFile.getInteger(Constants.Settings.Video.TAG_SCREEN_HEIGHT,
		Constants.Settings.Video.DEFAULT_SCREEN_HEIGHT);
	putInt(Constants.Settings.Video.TAG_SCREEN_WIDTH, _screenWidth);
	putInt(Constants.Settings.Video.TAG_SCREEN_HEIGHT, _screenHeight);

	DisplayModeType _displayModeType = null;
	String _displayModeString = settingFile
		.getString(Constants.Settings.Video.TAG_DISPLAY_MODE);
	if (_displayModeString != null) {
	    try {
		_displayModeType = DisplayModeType.valueOf(_displayModeString.toUpperCase());
	    } catch (IllegalArgumentException e) {
		_displayModeType = Constants.Settings.Video.DEFAULT_DISPLAY_MODE;
	    }
	}
	putDisplayModeType(Constants.Settings.Video.TAG_DISPLAY_MODE, _displayModeType);

	boolean _vSync = settingFile.getBoolean(Constants.Settings.Video.TAG_VERTICAL_SYNC,
		Constants.Settings.Video.DEFAULT_VERTICAL_SYNC);
	putBoolean(Constants.Settings.Video.TAG_VERTICAL_SYNC, _vSync);
    }

    public void load() {
	loadVideo();
	loadControls();

	flush();
    }

    private void loadVideo() {

    }

    private void loadControls() {
	String _movementType = settingFile.getString(Constants.Settings.Controls.TAG_MOVEMENT_TYPE);
	if (_movementType == null || !_movementType.equalsIgnoreCase("absolute") || !_movementType
		.equalsIgnoreCase("relative")) {
	    _movementType = Constants.Settings.Controls.DEFAULT_MOVEMENT_TYPE;
	}
	putString(Constants.Settings.Controls.TAG_MOVEMENT_TYPE, _movementType);

	loadInputControl(Constants.Settings.Controls.Mapping.TAG_FORWARD,
		Constants.Settings.Controls.Mapping.DEFAULT_FORWARD);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_BACKWARD,
		Constants.Settings.Controls.Mapping.DEFAULT_BACWKARD);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_LEFT,
		Constants.Settings.Controls.Mapping.DEFAULT_LEFT);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_RIGHT,
		Constants.Settings.Controls.Mapping.DEFAULT_RIGHT);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_INVENTORY,
		Constants.Settings.Controls.Mapping.DEFAULT_INVENTORY);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_CHARACTER,
		Constants.Settings.Controls.Mapping.DEFAULT_CHARACTER);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_SKILLS,
		Constants.Settings.Controls.Mapping.DEFAULT_SKILLS);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_1,
		Constants.Settings.Controls.Mapping.DEFAULT_SHORTCUT_1);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_2,
		Constants.Settings.Controls.Mapping.DEFAULT_SHORTCUT_2);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_3,
		Constants.Settings.Controls.Mapping.DEFAULT_SHORTCUT_3);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_4,
		Constants.Settings.Controls.Mapping.DEFAULT_SHORTCUT_4);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_5,
		Constants.Settings.Controls.Mapping.DEFAULT_SHORTCUT_5);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_6,
		Constants.Settings.Controls.Mapping.DEFAULT_SHORTCUT_6);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_7,
		Constants.Settings.Controls.Mapping.DEFAULT_SHORTCUT_7);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_8,
		Constants.Settings.Controls.Mapping.DEFAULT_SHORTCUT_8);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_9,
		Constants.Settings.Controls.Mapping.DEFAULT_SHORTCUT_9);
	loadInputControl(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_10,
		Constants.Settings.Controls.Mapping.DEFAULT_SHORTCUT_10);
    }

    private void loadInputControl(String tag, InputArray defaultValue) {
	String _inputLiterals = settingFile.getString(tag);

	InputArray _inputs = new InputArray(_inputLiterals);
	internalPut(tag, _inputs);
    }

    public boolean contains(String key) {
	return settings.containsKey(key);
    }

    public void putBoolean(String key, boolean value) {
	settingFile.putBoolean(key, value);
	internalPut(key, value);
    }

    public void putInt(String key, int value) {
	settingFile.putInteger(key, value);
	internalPut(key, value);
    }

    public void putLong(String key, long value) {
	settingFile.putLong(key, value);
	internalPut(key, value);
    }

    public void putFloat(String key, float value) {
	settingFile.putFloat(key, value);
	internalPut(key, value);
    }

    public void putString(String key, String value) {
	settingFile.putString(key, value);
	internalPut(key, value);
    }

    public void putInputArray(String key, InputArray value) {
	settingFile.putString(key, value.toString());
	internalPut(key, value);
    }

    public void putDisplayModeType(String key, DisplayModeType value) {
	settingFile.putString(key, value.getName());
	internalPut(key, value);
    }

    public void put(String key, Object value) {
	if (value instanceof Boolean) {
	    putBoolean(key, (Boolean) value);
	} else if (value instanceof Integer) {
	    putInt(key, (Integer) value);
	} else if (value instanceof Long) {
	    putLong(key, (Long) value);
	} else if (value instanceof Float) {
	    putFloat(key, (Float) value);
	} else if (value instanceof String) {
	    putString(key, (String) value);
	} else if (value instanceof InputArray) {
	    putInputArray(key, (InputArray) value);
	} else if (value instanceof DisplayModeType) {
	    putDisplayModeType(key, (DisplayModeType) value);
	} else {
	    throw new IllegalArgumentException("Unhandled object type provided.");
	}
    }

    @SuppressWarnings("unchecked")
    private <T> void internalPut(String key, T value) {
	SettingEntry<T> _entry = (SettingEntry<T>) settings.get(key);
	if (_entry == null) {
	    _entry = new SettingEntry<T>(value);
	    settings.put(key, _entry);
	} else {
	    _entry.setValue(value);
	}

	_entry.apply(key, value);
    }

    public boolean getBoolean(String key) {
	return settingFile.getBoolean(key);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
	return settingFile.getBoolean(key, defaultValue);
    }

    public int getInt(String key) {
	return settingFile.getInteger(key);
    }

    public int getInt(String key, int defaultValue) {
	return settingFile.getInteger(key, defaultValue);
    }

    public long getLong(String key) {
	return settingFile.getLong(key);
    }

    public long getLong(String key, long defaultValue) {
	return settingFile.getLong(key, defaultValue);
    }

    public float getFloat(String key) {
	return settingFile.getFloat(key);
    }

    public float getFloat(String key, float defaultValue) {
	return settingFile.getFloat(key, defaultValue);
    }

    public String getString(String key) {
	return settingFile.getString(key);
    }

    public String getString(String key, String defaultValue) {
	return settingFile.getString(key, defaultValue);
    }

    @SuppressWarnings("unchecked")
    public InputArray getInputArray(String key) {
	SettingEntry<InputArray> _entry = (SettingEntry<InputArray>) settings.get(key);
	if (_entry == null) {
	    return null;
	}

	return _entry.getValue();
    }

    @SuppressWarnings("unchecked")
    public DisplayModeType getDisplayModeType(String key) {
	SettingEntry<DisplayModeType> _entry = (SettingEntry<DisplayModeType>) settings.get(key);
	if (_entry == null) {
	    return null;
	}

	return _entry.getValue();
    }

    @SuppressWarnings("unchecked")
    public InputArray getInputArray(String key, InputArray defaultValue) {
	SettingEntry<InputArray> _entry = (SettingEntry<InputArray>) settings.get(key);
	if (_entry == null) {
	    return defaultValue;
	}

	return _entry.getValue();
    }

    public SettingEntry<?> get(String key) {
	return settings.get(key);
    }

    @SuppressWarnings("unchecked")
    public String getTag(InputArray inputs) {
	for (Entry<String, SettingEntry<?>> setting : settings.entries()) {
	    SettingEntry<Object> _entry = (SettingEntry<Object>) setting.value;
	    Object _value = _entry.getValue();
	    if (_value instanceof InputArray) {
		if (_value.equals(inputs)) {
		    return setting.key;
		}
	    }
	}

	return null;
    }

    @SuppressWarnings("unchecked")
    public void reset(String key) {
	SettingEntry<Object> _entry = (SettingEntry<Object>) get(key);
	_entry.reset(key, _entry.getValue());
    }

    @SuppressWarnings("unchecked")
    public <T> void setEvent(String key, SettingEvent<T> event) {
	SettingEntry<T> _entry = (SettingEntry<T>) settings.get(key);
	if (_entry == null) {
	    settings.put(key, new SettingEntry<T>(event));
	} else {
	    _entry.setEvent(event);
	}
    }

    public void flush() {
	settingFile.flush();
    }
}