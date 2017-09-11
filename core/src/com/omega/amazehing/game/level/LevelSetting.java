package com.omega.amazehing.game.level;

import java.io.Serializable;

import com.omega.amazehing.game.level.generator.GenerationAlgorithm;

public class LevelSetting implements Serializable {

    private static final long serialVersionUID = 1933828046498997547L;

    private String name;
    private GenerationAlgorithm algo;
    private long seed;
    private int width;
    private int height;
    private int cellSize;

    public LevelSetting() {
    }

    public LevelSetting(String name, GenerationAlgorithm algo, long seed, int width, int height, int cellSize) {
	this.name = name;
	this.algo = algo;
	this.seed = seed;
	this.width = width;
	this.height = height;
	this.cellSize = cellSize;
    }

    public String getName() {
	return name;
    }

    public GenerationAlgorithm getAlgorithm() {
	return algo;
    }

    public long getSeed() {
	return seed;
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public int getCellSize() {
	return cellSize;
    }
}