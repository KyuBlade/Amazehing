package com.omega.amazehing.game.level.load;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.omega.amazehing.game.level.load.AnimationLoader.AnimationParameter;

public class AnimationLoader extends SynchronousAssetLoader<Animation, AnimationParameter> {

    private AssetDescriptor<TextureAtlas> atlas;

    public AnimationLoader(FileHandleResolver resolver) {
	super(resolver);
    }

    @Override
    public Animation load(AssetManager assetManager, String fileName, FileHandle file,
	    AnimationParameter parameter) {
	TextureAtlas _atlas = assetManager.get(atlas);
	Animation _animation = new Animation(parameter.frameDuration, _atlas.getRegions(),
		parameter.playMode);

	return _animation;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file,
	    AnimationParameter parameter) {
	Array<AssetDescriptor> _dependencies = new Array<AssetDescriptor>(1);

	atlas = new AssetDescriptor(fileName + ".atlas", TextureAtlas.class);
	_dependencies.add(atlas);

	return _dependencies;
    }

    static public class AnimationParameter extends AssetLoaderParameters<Animation> {

	public float frameDuration;
	public PlayMode playMode;

	public AnimationParameter() {
	}

	public AnimationParameter(float frameDuration, PlayMode playMode) {
	    this.frameDuration = frameDuration;
	    this.playMode = playMode;
	}
    }
}