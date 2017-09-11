package com.omega.amazehing.game.input.action.interaction;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.omega.amazehing.Assets;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.entity.ProcessingFactory;
import com.omega.amazehing.factory.entity.RenderFactory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.DelayComponent;
import com.omega.amazehing.game.entity.component.event.CallbackComponent.Callback;
import com.omega.amazehing.game.entity.component.movement.SpeedComponent;
import com.omega.amazehing.game.entity.component.movement.VelocityComponent;
import com.omega.amazehing.game.entity.component.render.ColorComponent;
import com.omega.amazehing.game.entity.component.transform.PositionComponent;

public class GatheringInteraction extends AbstractInteraction {

    private static final float GATHER_INTERVAL = 0.5f;

    private EntityEngine engine;
    private RenderFactory renderFactory;
    private ProcessingFactory processingFactory;

    private Entity delayedEntity;

    private static final ComponentMapper<PositionComponent> posMapper = ComponentMapperHandler
	    .getPositionMapper();

    public GatheringInteraction(EntityEngine engine, FactoryManager factoryManager) {
	this.engine = engine;

	renderFactory = factoryManager.getFactory(RenderFactory.class);
	processingFactory = factoryManager.getFactory(ProcessingFactory.class);

    }

    @Override
    public void onOver(Entity entity, boolean isOvering) {
	Gdx.app.debug("Overing", isOvering + "");
	if (isOvering) {
	    entity.add(engine.createComponent(ColorComponent.class).setColor(Color.RED));
	} else {
	    entity.remove(ColorComponent.class);
	}
    }

    @Override
    public void onLeftCLick(final Entity entity, boolean isDown) {
	if (isDown) {
	    delayedEntity = engine.createEntity();
	    delayedEntity
		    .add(engine.createComponent(DelayComponent.class).setRunnable(new Runnable() {

			@Override
			public void run() {
			    final int _quantity = MathUtils.random(2, 5);

			    processingFactory.createInventoryAdd(8, _quantity, new Callback() {

				@Override
				public void call() throws Exception {
				    PositionComponent _posComp = posMapper.get(entity);
				    Vector2 _position = _posComp.getPosition();

				    final Entity _textEntity = renderFactory.createText(
					    String.valueOf(_quantity), Assets.Fonts.ARIAL,
					    Color.GREEN, _position.x, _position.y, 3f, 3f, 0);
				    _textEntity
					    .add(engine.createComponent(VelocityComponent.class)
						    .setY(-1f))
					    .add(engine.createComponent(SpeedComponent.class)
						    .setSpeed(3f));

				    Entity _delayedTextEntity = engine.createEntity();
				    _delayedTextEntity
					    .add(engine.createComponent(DelayComponent.class)
						    .setDelay(1f).setRunnable(new Runnable() {

					@Override
					public void run() {
					    engine.removeEntity(_textEntity);
					}
				    }));
				    engine.addEntity(_delayedTextEntity);
				}
			    });
			}
		    }).setDelay(GATHER_INTERVAL).setContinuous(true));
	    engine.addEntity(delayedEntity);
	} else {
	    engine.removeEntity(delayedEntity);
	}

    }

    @Override
    public void onRightClick(Entity entity, boolean isDown) {

    }
}