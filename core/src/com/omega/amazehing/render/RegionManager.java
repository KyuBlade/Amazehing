package com.omega.amazehing.render;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;
import com.omega.amazehing.render.region.ItemRegionCacheLoader;
import com.omega.amazehing.render.region.LevelRegionCacheLoader;
import com.omega.amazehing.render.region.SkillRegionCacheLoader;
import com.omega.amazehing.util.Utils;

public class RegionManager {

    private AssetManager assetManager;
    private IntMap<TextureRegion> cache;

    public RegionManager(AssetManager assetManager) throws Exception {
	this.assetManager = assetManager;

	cache = new IntMap<TextureRegion>();

	loadCache();
    }

    /**
     * Get the region from the packed id.
     * 
     * @param id the packed id of the region (atlas id & region id)
     * @return the texture region
     * @throws Exception if region was not found
     */
    public TextureRegion get(int packedId) throws Exception {
	TextureRegion _region = cache.get(packedId);
	if (_region == null) {
	    int[] _ids = Utils.unpackTextureId(packedId);
	    throw new Exception("Region id " + _ids[1] + " for atlas id " + _ids[0] + " not found");
	}

	return _region;
    }

    public TextureRegion get(int atlasId, int regionId) throws Exception {
	return get(Utils.packTextureId(atlasId, regionId));
    }

    /**
     * Get the region from assets (slower).
     * 
     * @param descriptor the atlas descriptor
     * @param name the region name
     * @return the region
     * @throws Exception if region was not found
     */
    public TextureRegion get(AssetDescriptor<TextureAtlas> descriptor, String name)
	    throws Exception {
	TextureAtlas _atlas = assetManager.get(descriptor);
	AtlasRegion _region = _atlas.findRegion(name);
	if (_region == null) {
	    throw new Exception("Region named " + name + " for atlas " + descriptor + " not found");
	}

	return _region;
    }

    public TextureRegion getFailOverRegion(int atlasId) throws Exception {
	return get(Utils.packTextureId(atlasId, 0));
    }

    private void loadCache() throws Exception {
	new ItemRegionCacheLoader(assetManager, cache);
	new LevelRegionCacheLoader(assetManager, cache);
	new SkillRegionCacheLoader(assetManager, cache);
    }
}