package com.omega.amazehing.setting.event.video;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.utils.Array;
import com.omega.amazehing.setting.SettingEvent;
import com.omega.amazehing.ui.tab.setting.video.DisplayData;
import com.omega.amazehing.ui.tab.setting.video.VideoResolutionItem;

public class ResolutionHeightSettingEvent implements SettingEvent<Integer> {

    private SelectBox<VideoResolutionItem> selectBox;
    private DisplayData displayData;

    public ResolutionHeightSettingEvent(SelectBox<VideoResolutionItem> selectBox,
	    DisplayData displayData) {
	this.selectBox = selectBox;
	this.displayData = displayData;
    }

    @Override
    public void apply(String key, Integer value) {
	displayData.setHeight(value);
	displayData.setHasChanged(true);
    }

    @Override
    public void reset(String key, Integer oldValue) {
	Array<VideoResolutionItem> _resolutionItems = selectBox.getItems();
	for (VideoResolutionItem resolutionItem : _resolutionItems) {
	    if (resolutionItem.equals(Gdx.graphics.getWidth(), oldValue)) {
		selectBox.setSelected(resolutionItem);

		break;
	    }
	}
    }
}