package com.omega.amazehing.util;

import com.badlogic.gdx.utils.Array;

public class SortedArray<T> extends Array<T> {

    @Override
    public void add(T value) {
        super.add(value);
        sort();
    }

}
