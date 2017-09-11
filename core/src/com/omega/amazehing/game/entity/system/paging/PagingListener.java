package com.omega.amazehing.game.entity.system.paging;

public interface PagingListener {

    /**
     * Called when the patch come active.
     * 
     * @param system
     * @param patch
     */
    public void onAdded(PagingSystem system, PagingPatch patch);

    /**
     * Called when the patch come inactive.
     * 
     * @param system
     * @param patch
     */
    public void onRemoved(PagingSystem system, PagingPatch patch);
}