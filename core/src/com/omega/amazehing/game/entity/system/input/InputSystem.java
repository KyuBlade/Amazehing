package com.omega.amazehing.game.entity.system.input;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.gdx.extension.ui.input.InputArray;
import com.gdx.extension.ui.input.InputHolder;
import com.gdx.extension.ui.input.InputType;
import com.omega.amazehing.game.entity.EntityEngine;
import com.omega.amazehing.game.input.action.InputAction;
import com.omega.amazehing.setting.SettingManager;

public class InputSystem extends EntitySystem implements InputProcessor {

    protected EntityEngine engine;
    protected SettingManager settingManager;
    protected InputMultiplexer inputProcessor;

    protected final InputArray tmpInputArray = new InputArray();
    protected InputArray comboInputs;
    protected Pool<InputHolder> inputHolderPool;
    protected ObjectMap<String, InputAction> controlMapping;

    public InputSystem(SettingManager settingManager, InputMultiplexer inputProcessor) {
	this.settingManager = settingManager;
	this.inputProcessor = inputProcessor;

	comboInputs = new InputArray();
	inputHolderPool = Pools.get(InputHolder.class);
	controlMapping = new ObjectMap<String, InputAction>();
    }

    @Override
    public void addedToEngine(Engine engine) {
	this.engine = (EntityEngine) engine;

	Gdx.app.debug("InputProcessor", "Add " + this);
	inputProcessor.addProcessor(this);
    }

    @Override
    public void removedFromEngine(Engine engine) {
	Gdx.app.debug("InputProcessor", "Remove " + this);
	inputProcessor.removeProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
	boolean _processed = false;
	InputHolder _inputHolder = inputHolderPool.obtain();
	_inputHolder.setType(InputType.Keyboard);
	_inputHolder.setInput(keycode);

	comboInputs.add(_inputHolder);

	String _controlTag = settingManager.getTag(comboInputs);
	if (_controlTag != null) {
	    InputAction _action = controlMapping.get(_controlTag);
	    if (_action != null) {
		_action.onDown();
		_processed = true;
	    }
	} else {
	    tmpInputArray.add(_inputHolder);
	    _controlTag = settingManager.getTag(tmpInputArray);
	    if (_controlTag != null) {
		InputAction _action = controlMapping.get(_controlTag);
		if (_action != null) {
		    _action.onDown();
		    _processed = true;
		}
	    }
	    tmpInputArray.clear();
	}

	return _processed;
    }

    @Override
    public boolean keyUp(int keycode) {
	boolean _processed = false;
	InputHolder _inputHolder = inputHolderPool.obtain();
	_inputHolder.setType(InputType.Keyboard);
	_inputHolder.setInput(keycode);

	comboInputs.removeValue(_inputHolder, false);

	tmpInputArray.add(_inputHolder);
	String _controlTag = settingManager.getTag(tmpInputArray);
	if (_controlTag != null) {
	    InputAction _action = controlMapping.get(_controlTag);
	    if (_action != null) {
		_action.onUp();
		_processed = true;
	    }
	} else {
	    _controlTag = settingManager.getTag(tmpInputArray);
	    if (_controlTag != null) {
		InputAction _action = controlMapping.get(_controlTag);
		if (_action != null) {
		    _action.onUp();
		    _processed = true;
		}
	    }
	}
	tmpInputArray.clear();
	inputHolderPool.free(_inputHolder);

	return _processed;
    }

    @Override
    public boolean keyTyped(char character) {
	return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	boolean _processed = false;
	InputHolder _inputHolder = inputHolderPool.obtain();
	_inputHolder.setType(InputType.Mouse);
	_inputHolder.setInput(button);

	comboInputs.add(_inputHolder);

	String _controlTag = settingManager.getTag(comboInputs);
	if (_controlTag != null) {
	    InputAction _action = controlMapping.get(_controlTag);
	    if (_action != null) {
		_action.onDown();
		_processed = true;
	    }
	} else {
	    tmpInputArray.add(_inputHolder);
	    _controlTag = settingManager.getTag(tmpInputArray);
	    if (_controlTag != null) {
		InputAction _action = controlMapping.get(_controlTag);
		if (_action != null) {
		    _action.onDown();
		    _processed = true;
		}
	    }
	    tmpInputArray.clear();
	}

	return _processed;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
	boolean _processed = false;
	InputHolder _inputHolder = inputHolderPool.obtain();
	_inputHolder.setType(InputType.Mouse);
	_inputHolder.setInput(button);

	comboInputs.removeValue(_inputHolder, false);

	tmpInputArray.add(_inputHolder);
	String _controlTag = settingManager.getTag(tmpInputArray);
	if (_controlTag != null) {
	    InputAction _action = controlMapping.get(_controlTag);
	    if (_action != null) {
		_action.onUp();
		_processed = true;
	    }
	} else {
	    _controlTag = settingManager.getTag(tmpInputArray);
	    if (_controlTag != null) {
		InputAction _action = controlMapping.get(_controlTag);
		if (_action != null) {
		    _action.onUp();
		    _processed = true;
		}
	    }
	}
	tmpInputArray.clear();
	inputHolderPool.free(_inputHolder);

	return _processed;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
	return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
	return false;
    }

    @Override
    public boolean scrolled(int amount) {
	return false;
    }

    @Override
    public void setProcessing(boolean processing) {
	super.setProcessing(processing);

	if (processing) {
	    inputProcessor.addProcessor(this);
	} else {
	    tmpInputArray.clear();
	    comboInputs.clear();
	    inputProcessor.removeProcessor(this);
	}
    }
}