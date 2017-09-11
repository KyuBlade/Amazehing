package com.omega.amazehing.exception;

import java.lang.Thread.UncaughtExceptionHandler;

import com.badlogic.gdx.Gdx;

public class GameUncaughtExceptionHandler implements UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
	Gdx.app.error("Uncaught exception",
		"Thread " + t.getName() + " throw an uncaught exception", e);
	t.interrupt();
	Gdx.app.exit();
    }
}