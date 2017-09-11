package com.omega.amazehing.game.entity.system.paging;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.omega.amazehing.factory.entity.RenderFactory;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.ActiveComponent;
import com.omega.amazehing.game.entity.component.DebugComponent;
import com.omega.amazehing.game.entity.component.DebugComponent.DebugType;
import com.omega.amazehing.game.entity.component.ViewerCategoryComponent.ViewerCategory;

public class DebugListener implements PagingListener {

    private EntityEngine engine;
    private RenderFactory renderFactory;

    public DebugListener(EntityEngine engine, RenderFactory renderFactory) {
	this.engine = engine;
	this.renderFactory = renderFactory;
    }

    @Override
    public void onAdded(PagingSystem system, PagingPatch patch) {
	float _patchesSize = system.getPatchesSize();

	Entity _patch = renderFactory.createRectangle(ViewerCategory.PAGING_DEBUG, Color.BLUE,
		patch.getPosition().x * _patchesSize, patch.getPosition().y * _patchesSize,
		_patchesSize, _patchesSize, false, 0, false);
	_patch.add(engine.createComponent(DebugComponent.class).setType(DebugType.PAGING))
		.add(engine.createComponent(ActiveComponent.class));
	patch.setUserData(_patch);
    }

    @Override
    public void onRemoved(PagingSystem system, PagingPatch patch) {
	Entity _entity = (Entity) patch.getUserData();
	engine.removeEntity(_entity);
    }
}