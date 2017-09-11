package com.omega.amazehing.render.region;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;
import com.omega.amazehing.Assets;

public class SkillRegionCacheLoader extends RegionCacheLoader {

    public SkillRegionCacheLoader(AssetManager assetManager, IntMap<TextureRegion> cache)
	    throws Exception {
	super(assetManager, cache, Assets.Atlases.SKILLS_ID, Assets.Atlases.SKILLS);

	bind(0, "unknow");
    }
}