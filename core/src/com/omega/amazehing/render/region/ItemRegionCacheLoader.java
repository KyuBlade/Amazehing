package com.omega.amazehing.render.region;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;
import com.omega.amazehing.Assets;

public class ItemRegionCacheLoader extends RegionCacheLoader {

    public ItemRegionCacheLoader(AssetManager assetManager, IntMap<TextureRegion> cache)
	    throws Exception {
	super(assetManager, cache, Assets.Atlases.ITEMS_ID, Assets.Atlases.ITEMS);

	bind(0, "unknow");
	bind(1, "10");
	bind(2, "2");
	bind(3, "book");
	bind(4, "coins");
	bind(5, "consumables/potions/haste_potion");
    }
}