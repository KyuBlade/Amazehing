package com.omega.amazehing.console;

import com.badlogic.gdx.utils.Array;
import com.gdx.extension.screen.ScreenManager;
import com.gdx.extension.ui.Console;
import com.gdx.extension.ui.Console.Command;
import com.gdx.extension.ui.Console.Command.Parameter.Value;
import com.omega.amazehing.screen.game.GameInterfaceScreen;

public class ShowInterfaceCommand extends Command {

    private ScreenManager screenManager;

    public ShowInterfaceCommand(ScreenManager screenManager) {
	super("showInterface");

	this.screenManager = screenManager;

	addParameter(new Parameter("show", Boolean.class, true));
    }

    @Override
    public void execute(Console console, Array<Value> args) {
	boolean _isShow = true;
	if (args.size > 0) {
	    _isShow = (Boolean) args.get(0).getValue();
	}

	if (_isShow) {
	    screenManager.showScreen(GameInterfaceScreen.class);
	    console.addEntry("Hud showed");
	} else {
	    screenManager.hideScreen(GameInterfaceScreen.class);
	    console.addEntry("Hud hidden");
	}
    }
}