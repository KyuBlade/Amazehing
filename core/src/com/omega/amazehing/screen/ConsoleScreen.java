package com.omega.amazehing.screen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.gdx.extension.screen.BaseScreen;
import com.gdx.extension.screen.ScreenManager;
import com.gdx.extension.ui.Console;
import com.omega.amazehing.console.AddItemCommand;
import com.omega.amazehing.console.CreateAnimationCommand;
import com.omega.amazehing.console.CreateParticleCommand;
import com.omega.amazehing.console.GCCommand;
import com.omega.amazehing.console.GetLevelInfo;
import com.omega.amazehing.console.HelpCommand;
import com.omega.amazehing.console.RemoveItemCommand;
import com.omega.amazehing.console.ShowDebugCommand;
import com.omega.amazehing.console.ShowInterfaceCommand;
import com.omega.amazehing.factory.FactoryManager;
import com.omega.amazehing.factory.entity.ProcessingFactory;
import com.omega.amazehing.factory.entity.RenderFactory;
import com.omega.amazehing.game.level.LevelSetting;
import com.omega.amazehing.screen.game.GameScreen;

public class ConsoleScreen extends BaseScreen {

    private Console console;

    public ConsoleScreen(final ScreenManager screenManager, FactoryManager factoryManager) {
	super(screenManager, 90);

	console = new Console(10, skin);
	layout.left().top();
	layout.add(console).width(600f).height(300f);

	console.addCaptureListener(new InputListener() {

	    @Override
	    public boolean keyDown(InputEvent event, int keycode) {
		if (keycode == Keys.F1) {
		    screenManager.hideScreen(ConsoleScreen.this);
		    return true;
		}
		return false;
	    }
	});

	GameScreen _gameScreen = screenManager.getScreen(GameScreen.class);
	RenderFactory _renderFactory = factoryManager.getFactory(RenderFactory.class);
	ProcessingFactory _processingFactory = factoryManager.getFactory(ProcessingFactory.class);
	LevelSetting _levelSetting = _gameScreen.getLevelSetting();

	console.registerCommand(new AddItemCommand(_processingFactory));
	console.registerCommand(new RemoveItemCommand(_processingFactory));
	console.registerCommand(new GetLevelInfo(_levelSetting));
	console.registerCommand(new ShowInterfaceCommand(screenManager));
	console.registerCommand(new ShowDebugCommand(screenManager));
	console.registerCommand(new HelpCommand());
	console.registerCommand(new GCCommand());
	console.registerCommand(new CreateAnimationCommand(_renderFactory));
	console.registerCommand(new CreateParticleCommand(_renderFactory));
    }

    @Override
    protected void show() {
	super.show();

	console.setFocus();
    }
}