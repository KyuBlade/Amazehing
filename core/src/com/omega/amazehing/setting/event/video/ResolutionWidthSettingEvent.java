package com.omega.amazehing.setting.event.video;

import com.omega.amazehing.setting.SettingEvent;
import com.omega.amazehing.ui.tab.setting.video.DisplayData;

public class ResolutionWidthSettingEvent implements SettingEvent<Integer> {

    private DisplayData displayData;

    public ResolutionWidthSettingEvent(DisplayData displayData) {
	this.displayData = displayData;
    }

    @Override
    public void apply(String key, Integer value) {
	displayData.setWidth(value);
	displayData.setHasChanged(true);
    }

    @Override
    public void reset(String key, Integer oldValue) {
    }
}