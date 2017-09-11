package com.omega.amazehing.console;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.gdx.extension.ui.Console;
import com.gdx.extension.ui.Console.Command;
import com.gdx.extension.ui.Console.Command.Parameter.Value;
import com.omega.amazehing.factory.entity.RenderFactory;

public class CreateAnimationCommand extends Command {

    private RenderFactory renderFactory;

    public CreateAnimationCommand(RenderFactory renderFactory) {
	super("createAnim");

	this.renderFactory = renderFactory;

	addParameter(new Parameter("animation", String.class, false));
	addParameter(new Parameter("x", Float.class, false));
	addParameter(new Parameter("y", Float.class, false));
	addParameter(new Parameter("width", Float.class, true));
	addParameter(new Parameter("height", Float.class, true));
    }

    @Override
    public void execute(Console console, Array<Value> args) {
	String _animName = (String) args.get(0).getValue();
	float _x = (Float) args.get(1).getValue();
	float _y = (Float) args.get(2).getValue();
	float _width = 1f;
	float _height = 1f;
	try {
	    _width = (Float) args.get(3).getValue();
	    _height = (Float) args.get(4).getValue();
	} catch (Exception e) {
	}

	Entity _anim = renderFactory.createAnimation("animations/" + _animName, _x, _y, _width,
		_height, 0f, false, true, 0, true);
	if (_anim == null) {
	    console.addEntry("Animation not found");
	} else {
	    console.addEntry("Animation " + _animName + " added");
	}
    }
}