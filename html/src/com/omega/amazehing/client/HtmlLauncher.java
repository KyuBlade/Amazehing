package com.omega.amazehing.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
	return new GwtApplicationConfiguration(640, 480);
    }

    @Override
    public ApplicationListener getApplicationListener() {
	return null;
    }

}