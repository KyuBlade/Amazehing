package com.omega.amazehing.render.region;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;
import com.omega.amazehing.Assets;

public class LevelRegionCacheLoader extends RegionCacheLoader {

    public LevelRegionCacheLoader(AssetManager assetManager, IntMap<TextureRegion> cache)
	    throws Exception {
	super(assetManager, cache, Assets.Atlases.LEVEL_ID, Assets.Atlases.LEVEL);

	bind(0, "unknow");
	bind(1, "floor/concrete");
	bind(2, "floor/grass");
	bind(3, "floor/grass_1");
	bind(4, "wall/stone");
    }
}