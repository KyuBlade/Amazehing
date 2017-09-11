package com.omega.amazehing.util;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.omega.amazehing.game.level.generator.cell.Direction;

public class Utils {

    private static final Random rand = new Random();
    private static final Array<Runnable> runnables = new Array<Runnable>();

    public static final long randomSeed() {
	return rand.nextLong();
    }

    public static final void shuffleArray(Random rand, Direction[] array) {
	int _index;
	Direction _temp;
	for (int i = array.length - 1; i > 0; i--) {
	    _index = rand.nextInt(i + 1);
	    _temp = array[_index];
	    array[_index] = array[i];
	    array[i] = _temp;
	}
    }

    public static final String toString(Direction[] array) {
	StringBuilder _sBuild = new StringBuilder();
	_sBuild.append("{ ");
	for (int i = 0; i < array.length; i++) {
	    _sBuild.append(array[i]);
	    if (i + 1 < array.length) {
		_sBuild.append(", ");
	    }
	}
	_sBuild.append(" }");

	return _sBuild.toString();
    }

    /**
     * Report an exception and exit the game.
     * 
     * @param e the exception to report
     */
    public static void reportFatalException(Exception e) {
	Gdx.app.error("Fatal Exception", "A fatal exception has occured.", e);
	System.exit(-1);
    }

    public static int packTextureId(int atlasId, int regionId) {
	return atlasId << 24 | regionId;
    }

    public static int[] unpackTextureId(int packedId) {
	int[] _ids = new int[2];
	_ids[0] = packedId >> 24;
	_ids[1] = packedId & 0xFFFFFF;

	return _ids;
    }

    public static final void preRunnable(Runnable runnable) {
	runnables.add(runnable);
    }

    public static final void processPreRunnables() {
	for (Runnable runnable : runnables) {
	    runnable.run();
	}
	runnables.clear();
    }
}
