package com.omega.amazehing.setting.event.controls;

import com.gdx.extension.ui.input.ControlBindList;
import com.gdx.extension.ui.input.InputArray;
import com.gdx.extension.ui.input.InputCatcher;
import com.omega.amazehing.setting.SettingEvent;

public class InputMappingSettingEvent implements SettingEvent<InputArray> {

    private ControlBindList bindList;

    public InputMappingSettingEvent(ControlBindList bindList) {
	this.bindList = bindList;
    }

    @Override
    public void apply(String key, InputArray value) {
    }

    @Override
    public void reset(String key, InputArray oldValue) {
	InputCatcher _inputCatcher = bindList.getInputCatcher(key);
	_inputCatcher.setInputs(oldValue);
    }
}