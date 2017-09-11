package com.omega.amazehing.console;

import com.badlogic.gdx.utils.Array;
import com.gdx.extension.ui.Console;
import com.gdx.extension.ui.Console.Command;
import com.gdx.extension.ui.Console.Command.Parameter.Value;

public class GCCommand extends Command {

    public GCCommand() {
	super("gc");
    }

    @Override
    public void execute(Console console, Array<Value> args) {
	System.gc();
    }
}
