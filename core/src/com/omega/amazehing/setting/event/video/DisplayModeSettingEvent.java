package com.omega.amazehing.setting.event.video;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.utils.Array;
import com.omega.amazehing.setting.SettingEvent;
import com.omega.amazehing.ui.tab.setting.video.DisplayData;
import com.omega.amazehing.ui.tab.setting.video.DisplayModeItem;
import com.omega.amazehing.ui.tab.setting.video.DisplayModeItem.DisplayModeType;

public class DisplayModeSettingEvent implements SettingEvent<DisplayModeType> {

    private SelectBox<DisplayModeItem> selectBox;
    private DisplayData displayData;

    public DisplayModeSettingEvent(SelectBox<DisplayModeItem> selectBox, DisplayData displayData) {
	this.selectBox = selectBox;
	this.displayData = displayData;
    }

    @Override
    public void apply(String key, DisplayModeType value) {
	displayData.setFullscreen(value.equals(DisplayModeType.FULLSCREEN));
	displayData.setHasChanged(true);
    }

    @Override
    public void reset(String key, DisplayModeType oldValue) {
	Array<DisplayModeItem> _displayModeItems = selectBox.getItems();
	for (DisplayModeItem displayModeItem : _displayModeItems) {
	    if (displayModeItem.getDisplayModeType().equals(oldValue)) {
		selectBox.setSelected(displayModeItem);

		break;
	    }
	}
    }
}