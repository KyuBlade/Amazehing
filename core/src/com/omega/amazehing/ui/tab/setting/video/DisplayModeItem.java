package com.omega.amazehing.ui.tab.setting.video;

public class DisplayModeItem {

    private DisplayModeType displayModeType;
    private String name;

    public DisplayModeItem(DisplayModeType displayModeType, String name) {
	this.displayModeType = displayModeType;
	this.name = name;
    }

    public DisplayModeType getDisplayModeType() {
	return displayModeType;
    }

    public String getName() {
	return name;
    }

    public enum DisplayModeType {
	WINDOWED("WINDOWED"), FULLSCREEN("FULLSCREEN");

	private String name;

	private DisplayModeType(String name) {
	    this.name = name;
	}

	public String getName() {
	    return name;
	}
    }

    @Override
    public String toString() {
	return name;
    }

    @Override
    public int hashCode() {
	return name.hashCode();
    }
}
