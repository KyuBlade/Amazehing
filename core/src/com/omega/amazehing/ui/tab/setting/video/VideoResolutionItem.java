package com.omega.amazehing.ui.tab.setting.video;

public class VideoResolutionItem implements Comparable<VideoResolutionItem> {

    private int width;
    private int height;

    public VideoResolutionItem(int width, int height) {
	this.width = width;
	this.height = height;
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public boolean equals(int width, int height) {
	return this.width == width && this.height == height;
    }

    @Override
    public String toString() {
	return width + "x" + height;
    }

    @Override
    public int hashCode() {
	return width + height * width;
    }

    @Override
    public int compareTo(VideoResolutionItem resolution) {
	if (resolution.width < width || resolution.height < height) {
	    return 1;
	} else if (resolution.width > width || resolution.height > height) {
	    return -1;
	}

	return 0;
    }
}
