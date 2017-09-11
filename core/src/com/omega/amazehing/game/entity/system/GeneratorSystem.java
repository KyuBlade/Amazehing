package com.omega.amazehing.game.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.level.LevelSetting;
import com.omega.amazehing.game.level.generator.AbstractMazeGenerator;
import com.omega.amazehing.game.level.generator.GenerationAlgorithm;
import com.omega.amazehing.game.level.generator.RecursiveBacktrackerMazeGenerator;
import com.omega.amazehing.util.BenchmarkUtil;
import com.omega.amazehing.util.Utils;

public class GeneratorSystem extends EntitySystem {

    private EntityEngine engine;
    private FactoryManager factoryManager;
    private LevelSetting settings;
    private AbstractMazeGenerator generator;

    public GeneratorSystem(LevelSetting settings, FactoryManager factoryManager) {
	this.settings = settings;
	this.factoryManager = factoryManager;

	this.settings = new LevelSetting("", GenerationAlgorithm.RECURSIVE_BACKTRACKER,
		MathUtils.random(Long.SIZE), 100, 100, 5);
    }

    @Override
    public void addedToEngine(Engine engine) {
	this.engine = (EntityEngine) engine;
    }

    public void generate() throws Exception {
	GenerationAlgorithm _algo = settings.getAlgorithm();
	switch (_algo) {
	    case RECURSIVE_BACKTRACKER:
		long _benchId = BenchmarkUtil.start();
		generator = new RecursiveBacktrackerMazeGenerator((EntityEngine) engine,
			factoryManager, settings);
		Gdx.app.debug("Generating time", BenchmarkUtil.end(_benchId) + "ms");

		break;
	    default:
		Utils.reportFatalException(
			new IllegalArgumentException("Generation algorithm not found : " + _algo));
	}
    }

    public AbstractMazeGenerator getGenerator() {
	return generator;
    }
}