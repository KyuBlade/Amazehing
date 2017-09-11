package com.omega.amazehing.game.level.generator;

public enum GenerationAlgorithm {

    RECURSIVE_BACKTRACKER("Recursive Backtracker", RecursiveBacktrackerMazeGenerator.class);

    private String formatedName;
    private Class<? extends AbstractMazeGenerator> generatorClass;

    private GenerationAlgorithm(String formatedName,
	    Class<? extends AbstractMazeGenerator> generatorClass) {
	this.formatedName = formatedName;
	this.generatorClass = generatorClass;
    }

    public Class<? extends AbstractMazeGenerator> getGeneratorClass() {
	return generatorClass;
    }

    @Override
    public String toString() {
	return formatedName;
    }
}