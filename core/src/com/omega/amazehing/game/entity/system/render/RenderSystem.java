package com.omega.amazehing.game.entity.system.render;

import java.util.Comparator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.omega.amazehing.Constants;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.DebugComponent;
import com.omega.amazehing.game.entity.component.DebugComponent.DebugType;
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
import com.omega.amazehing.game.entity.component.transform.OriginComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;
import com.omega.amazehing.game.entity.component.transform.RadiusComponent;
import com.omega.amazehing.game.entity.component.transform.RotationComponent;
import com.omega.amazehing.game.entity.component.transform.SizeComponent;
import com.omega.amazehing.render.ParticleManager;
import com.omega.amazehing.util.DebugManager;

public class RenderSystem extends SortedIteratingSystem implements Disposable {

    private EntityEngine engine;

    private Camera mainCamera;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Box2DDebugRenderer physicsDebug;
    private World world;
    private Sprite drawSprite;

    private static final ComponentMapper<TextureComponent> textureMapper = ComponentMapperHandler
	    .getTextureMapper();
    private static final ComponentMapper<FontComponent> fontMapper = ComponentMapperHandler
	    .getFontMapper();
    private static final ComponentMapper<ShapeRenderComponent> shapeMapper = ComponentMapperHandler
	    .getShapeRenderMapper();
    private static final ComponentMapper<DebugComponent> debugMapper = ComponentMapperHandler
	    .getDebugMapper();

    private static final ComponentMapper<PositionComponent> positionMapper = ComponentMapperHandler
	    .getPositionMapper();
    private static final ComponentMapper<OriginComponent> originMapper = ComponentMapperHandler
	    .getOriginMapper();
    private static final ComponentMapper<RotationComponent> rotationMapper = ComponentMapperHandler
	    .getRotationMapper();
    private static final ComponentMapper<SizeComponent> scaleMapper = ComponentMapperHandler
	    .getSizeMapper();
    private static final ComponentMapper<RadiusComponent> radiusMapper = ComponentMapperHandler
	    .getRadiusMapper();
    private static final ComponentMapper<ZIndexComponent> zindexMapper = ComponentMapperHandler
	    .getZIndexMapper();
    private static final ComponentMapper<FlipComponent> flipMapper = ComponentMapperHandler
	    .getFlipMapper();
    private static final ComponentMapper<ColorComponent> colorMapper = ComponentMapperHandler
	    .getColorMapper();
    private static final ComponentMapper<VerticeComponent> verticeMapper = ComponentMapperHandler
	    .getVerticeMapper();
    private static final ComponentMapper<BlendComponent> blendMapper = ComponentMapperHandler
	    .getBlendMapper();
    private static final ComponentMapper<TextComponent> textMapper = ComponentMapperHandler
	    .getTextMapper();
    private static final ComponentMapper<ParticleComponent> particleMapper = ComponentMapperHandler
	    .getParticleMapper();

    public RenderSystem(World world, Batch batch) {
	super(Family.all(PositionComponent.class, ZIndexComponent.class, BlendComponent.class)
		.one(FontComponent.class, TextureComponent.class, ParticleComponent.class,
			ShapeRenderComponent.class)
		.get(), new Comparator<Entity>() {

		    @Override
		    public int compare(Entity entityA, Entity entityB) {
			ZIndexComponent _zindexCompA = zindexMapper.get(entityA);
			ZIndexComponent _zindexCompB = zindexMapper.get(entityB);

			int _zindexA = _zindexCompA.getZindex();
			int _zindexB = _zindexCompB.getZindex();

			if (_zindexA < _zindexB) {
			    return 1;
			} else if (_zindexA > _zindexB) {
			    return -1;
			}

			return 0;
		    }
		}, Constants.Game.System.RENDER_SYSTEM_PRIORITY);

	this.world = world;
	this.batch = (SpriteBatch) batch;
	shapeRenderer = new ShapeRenderer();
	shapeRenderer.setAutoShapeType(true);
	drawSprite = new Sprite();
	drawSprite.setScale(1 + Constants.Level.WORLD_SCALE_FACTOR);

	physicsDebug = new Box2DDebugRenderer();
    }

    @Override
    public void addedToEngine(Engine engine) {
	super.addedToEngine(engine);

	this.engine = (EntityEngine) engine;

	CameraSystem _cameraSystem = engine.getSystem(CameraSystem.class);
	mainCamera = _cameraSystem.getMainCamera();
    }

    @Override
    public void update(float deltaTime) {
	batch.setProjectionMatrix(mainCamera.combined);
	shapeRenderer.setProjectionMatrix(mainCamera.combined);

	super.update(deltaTime);

	if (batch.isDrawing()) {
	    batch.end();
	}
	if (shapeRenderer.isDrawing()) {
	    shapeRenderer.end();
	}

	if (DebugManager.getInstance().get(DebugType.PHYS_BODY)) {
	    physicsDebug.render(world, mainCamera.combined);
	}
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
	DebugComponent _debugComp = debugMapper.get(entity);
	if (_debugComp != null) {
	    DebugType _type = _debugComp.getType();
	    if (!DebugManager.getInstance().get(_type)) {
		return;
	    }
	}

	if (particleMapper.has(entity)) { // Particles rendering
	    renderParticles(entity, deltaTime);
	} else if (textureMapper.has(entity)) { // Texture rendering
	    renderSprite(entity);
	} else if (fontMapper.has(entity)) {// Text rendering
	    renderText(entity);
	} else if (shapeMapper.has(entity)) {
	    boolean _isBlending = blendMapper.get(entity).isBlending();
	    if (_isBlending) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    } else {
		Gdx.gl.glDisable(GL20.GL_BLEND);
	    }
	    renderShape(entity);
	}
    }

    private void renderSprite(Entity entity) {
	TextureComponent _textureComp = textureMapper.get(entity);
	PositionComponent _posComp = positionMapper.get(entity);
	ColorComponent _colorComp = colorMapper.get(entity);
	SizeComponent _scaleComp = scaleMapper.get(entity);
	OriginComponent _originComp = originMapper.get(entity);
	RotationComponent _rotComp = rotationMapper.get(entity);
	FlipComponent _flipComp = flipMapper.get(entity);

	Vector2 _position = _posComp.getPosition();
	Color _color = (_colorComp != null) ? _colorComp.getColor() : Color.WHITE;

	drawSprite.setRegion(_textureComp.getTexture());
	drawSprite.setColor(_color);
	if (_flipComp != null) {
	    drawSprite.setFlip(_flipComp.isFlipX(), _flipComp.isFlipY());
	} else {
	    drawSprite.setFlip(false, false);
	}
	drawSprite.setRotation(_rotComp.getRotation());
	drawSprite.setSize(_scaleComp.getWidth(), _scaleComp.getHeight());
	drawSprite.setCenter(_position.x, _position.y);
	if (_originComp != null) {
	    drawSprite.setOrigin(_originComp.getOrigin().x, _originComp.getOrigin().y);
	} else {
	    drawSprite.setOriginCenter();
	}

	if (shapeRenderer.isDrawing()) {
	    shapeRenderer.end();
	}
	if (!batch.isDrawing()) {
	    batch.begin();
	}
	drawSprite.draw(batch);
    }

    private final void renderParticles(Entity entity, float delta) {
	ParticleComponent _particleComp = particleMapper.get(entity);
	PositionComponent _posComp = positionMapper.get(entity);
	Vector2 _position = _posComp.getPosition();
	PooledEffect _effect = _particleComp.getEffect();

	if (shapeRenderer.isDrawing()) {
	    shapeRenderer.end();
	}
	if (!batch.isDrawing()) {
	    batch.begin();
	}

	_effect.setPosition(_position.x, _position.y);
	_effect.draw(batch, delta);

	if (_effect.isComplete()) {
	    _effect.free();
	    engine.removeEntity(entity);
	}
    }

    private final void renderText(Entity entity) {
	FontComponent _fontComp = fontMapper.get(entity);
	TextComponent _textComp = textMapper.get(entity);
	PositionComponent _posComp = positionMapper.get(entity);
	SizeComponent _scaleComp = scaleMapper.get(entity);
	ColorComponent _colorComp = colorMapper.get(entity);
	Vector2 _position = _posComp.getPosition();
	BitmapFont _font = _fontComp.getFont();
	Color _color = (_colorComp != null) ? _colorComp.getColor() : Color.WHITE;
	String _text = _textComp.getText();

	float _scaleFactor = Constants.Level.WORLD_SCALE_FACTOR;
	_font.getData().setScale(_scaleComp.getWidth() * _scaleFactor,
		_scaleComp.getHeight() * _scaleFactor);
	_font.setColor(_color);

	if (shapeRenderer.isDrawing()) {
	    shapeRenderer.end();
	}
	if (!batch.isDrawing()) {
	    batch.begin();
	}
	_font.draw(batch, _text, _position.x, _position.y);
    }

    private final void renderShape(Entity entity) {
	ShapeRenderComponent _shapeComp = shapeMapper.get(entity);
	ColorComponent _colorComp = colorMapper.get(entity);

	Shape _shape = _shapeComp.getShape();
	Color _color = _colorComp.getColor();
	if (_color == null) {
	    _color = Color.WHITE;
	}

	if (batch.isDrawing()) {
	    batch.end();
	}
	if (!shapeRenderer.isDrawing()) {
	    shapeRenderer.begin();
	}

	shapeRenderer.set((_shapeComp.isFilled()) ? ShapeType.Filled : ShapeType.Line);
	shapeRenderer.setColor(_color);
	if (_shape.equals(Shape.RECTANGLE)) {
	    PositionComponent _posComp = positionMapper.get(entity);
	    SizeComponent _scaleComp = scaleMapper.get(entity);
	    Vector2 _position = _posComp.getPosition();
	    shapeRenderer.rect(_position.x, _position.y, _scaleComp.getWidth(),
		    _scaleComp.getHeight());
	} else if (_shape.equals(Shape.CIRCLE)) {
	    PositionComponent _posComp = positionMapper.get(entity);
	    RadiusComponent _radiusComp = radiusMapper.get(entity);
	    Vector2 _position = _posComp.getPosition();
	    shapeRenderer.circle(_position.x, _position.y, _radiusComp.getRadius());
	} else if (_shape.equals(Shape.LINE)) {
	    VerticeComponent _vertComp = verticeMapper.get(entity);
	    float[] _vertices = _vertComp.getVertices();
	    if (_vertices.length >= 2) {
		shapeRenderer.polyline(_vertices);
	    }
	}
    }

    @Override
    public void dispose() {
	shapeRenderer.dispose();
	physicsDebug.dispose();

	ParticleManager.getInstance().dispose();
    }
}