package com.omega.amazehing;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader.TextureAtlasParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.omega.amazehing.game.level.load.AnimationLoader.AnimationParameter;

public final class Assets {

    public static final class Atlases {

	public static final byte LEVEL_ID = 0x1;
	public static final byte ITEMS_ID = 0x2;
	public static final byte SKILLS_ID = 0x3;

	public static final AssetDescriptor<TextureAtlas> LEVEL = new AssetDescriptor<TextureAtlas>(
		"level.atlas", TextureAtlas.class, new TextureAtlasParameter());
	public static final AssetDescriptor<TextureAtlas> ITEMS = new AssetDescriptor<TextureAtlas>(
		"items.atlas", TextureAtlas.class, new TextureAtlasParameter());
	public static final AssetDescriptor<TextureAtlas> SKILLS = new AssetDescriptor<TextureAtlas>(
		"skills.atlas", TextureAtlas.class, new TextureAtlasParameter());
    }

    public static final class Fonts {

	private static final FreeTypeFontLoaderParameter _fontParams = new FreeTypeFontLoaderParameter();

	static {
	    _fontParams.fontFileName = "fonts/arial.ttf";
	    _fontParams.fontParameters.size = 10;
	    _fontParams.fontParameters.flip = true;
	    _fontParams.fontParameters.minFilter = Texture.TextureFilter.Linear;
	    _fontParams.fontParameters.magFilter = Texture.TextureFilter.MipMapLinearNearest;
	}

	public static final AssetDescriptor<BitmapFont> ARIAL = new AssetDescriptor<BitmapFont>("arial12.ttf",
		BitmapFont.class, _fontParams);
    }

    public static final class Animations {

	public static final class Player {

	    public static final AssetDescriptor<Animation> STAND = new AssetDescriptor<Animation>(
		    "animations/player/stand", Animation.class,
		    new AnimationParameter(1f / 10f, PlayMode.LOOP));
	}

	public static final AssetDescriptor<Animation> BLUE_SMOKE = new AssetDescriptor<Animation>(
		"animations/blue_flame", Animation.class,
		new AnimationParameter(1f / 30f, PlayMode.LOOP));
	public static final AssetDescriptor<Animation> BREACH = new AssetDescriptor<Animation>(
		"animations/breach", Animation.class,
		new AnimationParameter(1f / 10f, PlayMode.LOOP));
    }

    public static final class Particles {

	public static final AssetDescriptor<ParticleEffect> RING_FIRE = new AssetDescriptor<ParticleEffect>(
		"particles/ring_fire.p", ParticleEffect.class, new ParticleEffectParameter());
    }

    public static final class BehaviorTrees {

	public static final String CLOSE_ENCOUTER = "btree/close_encounter.tree";
    }
}