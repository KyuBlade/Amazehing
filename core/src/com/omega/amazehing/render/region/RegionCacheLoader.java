package com.omega.amazehing.render.region;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;
import com.omega.amazehing.util.Utils;

public class RegionCacheLoader {

    private byte atlasId;
    private int packedId;

    private TextureAtlas atlas;
    private IntMap<TextureRegion> cache;

    public RegionCacheLoader(AssetManager assetManager, IntMap<TextureRegion> cache, byte atlasId,
	    AssetDescriptor<TextureAtlas> descriptor) throws Exception {
	this.cache = cache;
	this.atlasId = atlasId;

	atlas = assetManager.get(descriptor);
	if (atlas == null) {
	    throw new Exception("Atlas " + descriptor + " not found");
	}
    }

    public void bind(int id, String name) throws Exception {
	AtlasRegion _region = atlas.findRegion(name);
	if (_region == null) {
	    throw new Exception("The region named " + name + " was not found");
	}

	packedId = Utils.packTextureId(atlasId, id);
	IndexedTextureRegion _indexedTexture = new IndexedTextureRegion(packedId, _region);
	cache.put(packedId, _indexedTexture);
    }
}