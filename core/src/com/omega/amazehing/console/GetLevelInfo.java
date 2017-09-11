package com.omega.amazehing.console;

import com.badlogic.gdx.utils.Array;
import com.gdx.extension.ui.Console;
import com.gdx.extension.ui.Console.Command;
import com.gdx.extension.ui.Console.Command.Parameter.Value;
import com.omega.amazehing.game.level.LevelSetting;

public class GetLevelInfo extends Command {

    private LevelSetting levelSetting;

    public GetLevelInfo(LevelSetting levelSetting) {
	super("getLevelInfo");

	this.levelSetting = levelSetting;
    }

    @Override
    public void execute(Console console, Array<Value> args) {
	console.addEntry("Name : " + levelSetting.getName());
	console.addEntry("Seed : " + levelSetting.getSeed());
	console.addEntry("Algorithm : " + levelSetting.getAlgorithm());
	console.addEntry("Width : " + levelSetting.getWidth());
	console.addEntry("Height : " + levelSetting.getHeight());
	console.addEntry("Cell size : " + levelSetting.getCellSize());
    }
}