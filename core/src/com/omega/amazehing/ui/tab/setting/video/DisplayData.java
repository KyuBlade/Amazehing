package com.omega.amazehing.ui.tab.setting.video;

public class DisplayData {

    private int width;
    private int height;
    private boolean fullscreen;
    private boolean hasChanged;

    public DisplayData() {
    }

    public int getWidth() {
	return width;
    }

    public void setWidth(int width) {
	this.width = width;
    }

    public int getHeight() {
	return height;
    }

    public void setHeight(int height) {
	this.height = height;
    }

    public boolean isFullscreen() {
	return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
	this.fullscreen = fullscreen;
    }

    public boolean hasChanged() {
	return hasChanged;
    }

    public void setHasChanged(boolean hasChanged) {
	this.hasChanged = hasChanged;
    }
}