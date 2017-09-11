package com.omega.amazehing.console;

import com.badlogic.gdx.utils.Array;
import com.gdx.extension.screen.ScreenManager;
import com.gdx.extension.ui.Console;
import com.gdx.extension.ui.Console.Command;
import com.gdx.extension.ui.Console.Command.Parameter.Value;
import com.omega.amazehing.screen.DebugScreen;

public class ShowDebugCommand extends Command {

    private ScreenManager screenManager;

    public ShowDebugCommand(ScreenManager screenManager) {
	super("showDebug");

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
	    screenManager.showScreen(DebugScreen.class);
	    console.addEntry("Debug showed");
	} else {
	    screenManager.hideScreen(DebugScreen.class);
	    console.addEntry("Debug hidden");
	}
    }
}