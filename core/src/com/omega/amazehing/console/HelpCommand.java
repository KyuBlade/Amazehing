package com.omega.amazehing.console;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap.Values;
import com.gdx.extension.ui.Console;
import com.gdx.extension.ui.Console.Command;
import com.gdx.extension.ui.Console.Command.Parameter.Value;

public class HelpCommand extends Command {

    public HelpCommand() {
	super("help");

	addParameter(new Parameter("commandName", String.class, true));
    }

    @Override
    public void execute(Console console, Array<Value> args) {
	String _cmdName = null;
	if (args.size > 0) {
	    _cmdName = (String) args.get(0).getValue();
	}

	if (_cmdName != null) {
	    Command _command = console.getCommands().get(_cmdName);
	    if (_command != null) {
		console.addEntry(_command.toString());
	    } else {
		console.addEntry("Command " + _cmdName + " not found");
	    }
	} else {
	    console.addEntry("List of commands : ");
	    Values<Command> _commands = console.getCommands().values();
	    for (Command _command : _commands) {
		console.addEntry(_command.toString());
	    }
	}
    }

}
