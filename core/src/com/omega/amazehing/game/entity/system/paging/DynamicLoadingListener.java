package com.omega.amazehing.game.entity.system.paging;

import com.badlogic.gdx.Gdx;
import com.omega.amazehing.database.SaveDatabaseHandler;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.entity.component.event.CallbackComponent.Callback;
import com.omega.amazehing.game.entity.system.SaveSystem;

public class DynamicLoadingListener implements PagingListener {

    private EntityEngine engine;
    private PagingSystem pagingSystem;
    private SaveDatabaseHandler saveHandler;

    public DynamicLoadingListener(EntityEngine engine) {
	this.engine = engine;
	this.pagingSystem = engine.getSystem(PagingSystem.class);
    }

    @Override
    public void onAdded(PagingSystem system, final PagingPatch patch) {
	if (saveHandler == null) {
	    SaveSystem _saveSystem = engine.getSystem(SaveSystem.class);
	    saveHandler = _saveSystem.getDatabase();
	}

	try {
	    saveHandler.loadTilesFromPatch(patch.getPosition(), system.getPatchesSize(),
		    new Callback() {

			@Override
			public void call() throws Exception {
			    patch.setLoaded(true);
			}
		    });
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void onRemoved(PagingSystem system, PagingPatch patch) {
	pagingSystem.unloadPatch(patch);
    }
}