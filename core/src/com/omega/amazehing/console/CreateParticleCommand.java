package com.omega.amazehing.console;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.gdx.extension.ui.Console;
import com.gdx.extension.ui.Console.Command;
import com.gdx.extension.ui.Console.Command.Parameter.Value;
import com.omega.amazehing.factory.entity.RenderFactory;

public class CreateParticleCommand extends Command {

    private RenderFactory renderFactory;

    public CreateParticleCommand(RenderFactory renderFactory) {
	super("createParticle");

	this.renderFactory = renderFactory;

	addParameter(new Parameter("particle", String.class, false));
	addParameter(new Parameter("x", Float.class, false));
	addParameter(new Parameter("y", Float.class, false));
	addParameter(new Parameter("width", Float.class, true));
	addParameter(new Parameter("height", Float.class, true));
    }

    @Override
    public void execute(Console console, Array<Value> args) {
	String _particleName = (String) args.get(0).getValue();
	float _x = (Float) args.get(1).getValue();
	float _y = (Float) args.get(2).getValue();
	float _width = 1f;
	float _height = 1f;
	try {
	    _width = (Float) args.get(3).getValue();
	    _height = (Float) args.get(4).getValue();
	} catch (Exception e) {
	}

	Entity _particle = renderFactory.createParticle("particles/" + _particleName + ".p", _x,
		_y, _width, _height, 0f, false, false, 0, true);
	if (_particle == null) {
	    console.addEntry("Particle not found");
	} else {
	    console.addEntry("Particle " + _particleName + " added");
	}
    }
}