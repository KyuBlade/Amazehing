package com.omega.amazehing.util;

import com.badlogic.gdx.utils.LongMap;

public class BenchmarkUtil {

    private static long id;
    private static final LongMap<Long> lookup = new LongMap<Long>(4);

    public static long start() {
	lookup.put(++id, System.nanoTime());

	return id;
    }

    public static long end(long id) {
	return (System.nanoTime() - lookup.remove(id)) / 100000;
    }
}