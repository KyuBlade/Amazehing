package com.omega.amazehing.factory.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omega.amazehing.factory.Factory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent.ViewerCategory;
import com.omega.amazehing.game.entity.component.ViewerNameComponent;
import com.omega.amazehing.game.entity.component.paging.PagedComponent;
import com.omega.amazehing.game.entity.component.render.AnimationComponent;
import com.omega.amazehing.game.entity.component.render.BlendComponent;
import com.omega.amazehing.game.entity.component.render.ColorComponent;
import com.omega.amazehing.game.entity.component.render.FlipComponent;
import com.omega.amazehing.game.entity.component.render.ShapeRenderComponent;
import com.omega.amazehing.game.entity.component.render.ShapeRenderComponent.Shape;
import com.omega.amazehing.game.entity.component.render.TextureComponent;
import com.omega.amazehing.game.entity.component.render.VerticeComponent;
import com.omega.amazehing.game.entity.component.render.ZIndexComponent;
import com.omega.amazehing.game.entity.component.render.particle.ParticleComponent;
import com.omega.amazehing.game.entity.component.render.text.FontComponent;
import com.omega.amazehing.game.entity.component.render.text.TextComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.entity.component.transform.RadiusComponent;
import com.omega.amazehing.game.entity.component.transform.RotationComponent;
import com.omega.amazehing.game.entity.component.transform.SizeComponent;
import com.omega.amazehing.render.ParticleManager;
import com.omega.amazehing.render.RegionManager;
import com.omega.amazehing.util.Utils;

public class RenderFactory implements Factory {

    private static final Logger logger = LoggerFactory.getLogger(RenderFactory.class);

    private AssetManager assetManager;
    private EntityEngine engine;
    private RegionManager regionManager;

    public RenderFactory(AssetManager assetManager, EntityEngine engine) throws Exception {
	this.assetManager = assetManager;
	this.engine = engine;

	regionManager = new RegionManager(assetManager);
    }

    public Entity createSprite(int textureId, ViewerCategory category, float x, float y,
	    float width, float height, int zindex, boolean isBlending) throws Exception {
	int[] _ids = Utils.unpackTextureId(textureId);

	return createSprite(_ids[0], _ids[1], category, x, y, width, height, 0f, false, false,
		zindex, isBlending, true);
    }

    public Entity createSprite(int atlasId, int regionId, ViewerCategory category, float x, float y,
	    float width, float height, int zindex, boolean isBlending) throws Exception {
	return createSprite(atlasId, regionId, category, x, y, width, height, 0f, false, false,
		zindex, isBlending, true);
    }

    public Entity createSprite(int atlasId, int regionId, ViewerCategory category, float x, float y,
	    float width, float height, float rotation, boolean isFlipX, boolean isFlipY, int zindex,
	    boolean isBlending, boolean add) throws Exception {
	TextureRegion _region;
	try {
	    _region = regionManager.get(atlasId, regionId);
	} catch (Exception e) {
	    _region = regionManager.getFailOverRegion(atlasId);
	}

	Entity _sprite = engine.createEntity();
	_sprite.add(engine.createComponent(ViewerCategoryComponent.class).setCategory(category))
		.add(engine.createComponent(TextureComponent.class).setTexture(_region))
		.add(engine.createComponent(PositionComponent.class).setX(x).setY(y))
		.add(engine.createComponent(SizeComponent.class).setWidth(width).setHeight(height))
		.add(engine.createComponent(RotationComponent.class).setRotation(rotation))
		.add(engine.createComponent(FlipComponent.class).setFlipX(isFlipX)
			.setFlipY(isFlipY))
		.add(engine.createComponent(ZIndexComponent.class).setZindex(zindex))
		.add(engine.createComponent(BlendComponent.class).setBlending(isBlending))
		.add(engine.createComponent(PagedComponent.class));

	if (add) {
	    engine.addEntity(_sprite);
	}

	return _sprite;
    }

    public Entity createAnimation(String anim, float x, float y, float width, float height,
	    float rotation, boolean isFlipX, boolean isFlipY, int zindex, boolean isBlending) {
	try {
	    Animation _anim = assetManager.get(anim);

	    return createAnimation(_anim, x, y, width, height, rotation, isFlipX, isFlipY, zindex,
		    isBlending);
	} catch (Exception e) {
	    logger.warn("Unable to create animation", e);
	}

	return null;
    }

    public Entity createAnimation(AssetDescriptor<Animation> anim, float x, float y, float width,
	    float height, float rotation, boolean isFlipX, boolean isFlipY, int zindex,
	    boolean isBlending) {
	try {
	    Animation _anim = assetManager.get(anim);

	    return createAnimation(_anim, x, y, width, height, rotation, isFlipX, isFlipY, zindex,
		    isBlending);
	} catch (Exception e) {
	    logger.warn("Unable to create animation", e);
	}

	return null;
    }

    public Entity createAnimation(Animation anim, float x, float y, float width, float height,
	    float rotation, boolean isFlipX, boolean isFlipY, int zindex, boolean isBlending) {
	Entity _entity = engine.createEntity();
	_entity.add(engine.createComponent(AnimationComponent.class).setAnimation(anim))
		.add(engine.createComponent(TextureComponent.class))
		.add(engine.createComponent(PositionComponent.class).setX(x).setY(y))
		.add(engine.createComponent(SizeComponent.class).setWidth(width).setHeight(height))
		.add(engine.createComponent(RotationComponent.class).setRotation(rotation))
		.add(engine.createComponent(FlipComponent.class).setFlipX(isFlipX)
			.setFlipY(isFlipY))
		.add(engine.createComponent(ZIndexComponent.class).setZindex(zindex))
		.add(engine.createComponent(BlendComponent.class).setBlending(isBlending));
	engine.addEntity(_entity);

	return _entity;
    }

    public Entity createParticle(AssetDescriptor<ParticleEffect> particle, float x, float y,
	    float width, float height, float rotation, boolean isFlipX, boolean isFlipY, int zindex,
	    boolean isBlending) {
	return createParticle(particle.file.nameWithoutExtension(), x, y, width, height, rotation,
		isFlipX, isFlipY, zindex, isBlending);
    }

    public Entity createParticle(String particle, float x, float y, float width, float height,
	    float rotation, boolean isFlipX, boolean isFlipY, int zindex, boolean isBlending) {
	try {
	    PooledEffect _effect = ParticleManager.getInstance().getParticle(particle);
	    Entity _entity = engine.createEntity();
	    _entity.add(engine.createComponent(ParticleComponent.class).setEffect(_effect))
		    .add(engine.createComponent(TextureComponent.class))
		    .add(engine.createComponent(PositionComponent.class).setX(x).setY(y))
		    .add(engine.createComponent(SizeComponent.class).setWidth(width)
			    .setHeight(height))
		    .add(engine.createComponent(RotationComponent.class).setRotation(rotation))
		    .add(engine.createComponent(FlipComponent.class).setFlipX(isFlipX)
			    .setFlipY(isFlipY))
		    .add(engine.createComponent(ZIndexComponent.class).setZindex(zindex))
		    .add(engine.createComponent(BlendComponent.class).setBlending(isBlending));
	    engine.addEntity(_entity);

	    return _entity;
	} catch (Exception e) {
	    logger.warn("Unable to create particle", e);
	}

	return null;
    }

    public Entity createText(String text, AssetDescriptor<BitmapFont> font, Color color, float x,
	    float y, float width, float height, int zindex) {
	BitmapFont _font = assetManager.get(font);
	Entity _entity = engine.createEntity();
	_entity.add(engine.createComponent(TextComponent.class).setText(text))
		.add(engine.createComponent(FontComponent.class).setFont(_font))
		.add(engine.createComponent(ColorComponent.class).setColor(color))
		.add(engine.createComponent(PositionComponent.class).setX(x).setY(y))
		.add(engine.createComponent(SizeComponent.class).setWidth(width).setHeight(height))
		.add(engine.createComponent(RotationComponent.class).setRotation(0f))
		.add(engine.createComponent(ZIndexComponent.class).setZindex(zindex))
		.add(engine.createComponent(BlendComponent.class).setBlending(false));
	engine.addEntity(_entity);

	return _entity;
    }

    public Entity createRectangle(ViewerCategory category, Color color, float x, float y,
	    float width, float height, boolean isFilled, int zindex, boolean isBlending) {
	Entity _shape = engine.createEntity();
	_shape.add(engine.createComponent(ViewerCategoryComponent.class).setCategory(category))
		.add(engine.createComponent(ShapeRenderComponent.class).setShape(Shape.RECTANGLE)
			.setFilled(isFilled))
		.add(engine.createComponent(ColorComponent.class).setColor(color))
		.add(engine.createComponent(PositionComponent.class).setX(x).setY(y))
		.add(engine.createComponent(SizeComponent.class).setWidth(width).setHeight(height))
		.add(engine.createComponent(ZIndexComponent.class).setZindex(zindex))
		.add(engine.createComponent(BlendComponent.class).setBlending(isBlending))
		.add(engine.createComponent(PagedComponent.class));
	engine.addEntity(_shape);

	return _shape;
    }

    public Entity createCircle(ViewerCategory category, String name, Color color, float x, float y,
	    float radius, boolean isFilled, int zindex, boolean isBlending) {
	Entity _entity = createCircle(category, color, x, y, radius, isFilled, zindex, isBlending);
	_entity.add(engine.createComponent(ViewerNameComponent.class).setName(name));

	return _entity;
    }

    public Entity createCircle(ViewerCategory category, Color color, float x, float y, float radius,
	    boolean isFilled, int zindex, boolean isBlending) {
	Entity _shape = engine.createEntity();
	_shape.add(engine.createComponent(ViewerCategoryComponent.class).setCategory(category))

	.add(engine.createComponent(ShapeRenderComponent.class).setShape(Shape.CIRCLE)
		.setFilled(isFilled))
		.add(engine.createComponent(ColorComponent.class).setColor(color))
		.add(engine.createComponent(PositionComponent.class).setX(x).setY(y))
		.add(engine.createComponent(RadiusComponent.class).setRadius(radius))
		.add(engine.createComponent(ZIndexComponent.class).setZindex(zindex))
		.add(engine.createComponent(BlendComponent.class).setBlending(isBlending));
	engine.addEntity(_shape);

	return _shape;
    }

    public Entity createLine(ViewerCategory category, Color color, float x1, float y1, float x2,
	    float y2, int zindex) {
	return createPolyline(category, color, new float[] { x1, y1, x2, y2 }, zindex);
    }

    public Entity createPolyline(ViewerCategory category, Color color, float[] vertices,
	    int zindex) {
	Entity _shape = engine.createEntity();
	_shape.add(engine.createComponent(ViewerCategoryComponent.class).setCategory(category))
		.add(engine.createComponent(PositionComponent.class))
		.add(engine.createComponent(ShapeRenderComponent.class).setShape(Shape.LINE)
			.setFilled(false))
		.add(engine.createComponent(ColorComponent.class).setColor(color))
		.add(engine.createComponent(VerticeComponent.class).setVertices(vertices))
		.add(engine.createComponent(ZIndexComponent.class).setZindex(zindex))
		.add(engine.createComponent(BlendComponent.class).setBlending(false));
	engine.addEntity(_shape);

	return _shape;
    }

    public Entity createPolyline(ViewerCategory category, String name, Color color,
	    float[] vertices, int zindex) {
	Entity _shape = createPolyline(category, color, vertices, zindex);
	_shape.add(engine.createComponent(ViewerNameComponent.class).setName(name));

	return _shape;
    }

    public EntityEngine getEngine() {
	return engine;
    }

    public RegionManager getRegionManager() {
	return regionManager;
    }
}