package com.omega.amazehing.render.region;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class IndexedTextureRegion extends TextureRegion {

    private int regionId;

    public IndexedTextureRegion(int regionId, TextureRegion region) {
	super(region);

	this.regionId = regionId;
    }

    public int getRegionId() {
	return regionId;
    }
}