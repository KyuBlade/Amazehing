package com.omega.amazehing.screen;

import java.io.File;
import java.io.FileFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.gdx.extension.screen.BaseScreen;
import com.gdx.extension.screen.ScreenManager;
import com.gdx.extension.ui.list.AdvancedList;
import com.gdx.extension.ui.panel.Panel;
import com.omega.amazehing.Constants;
import com.omega.amazehing.GameCore;
import com.omega.amazehing.screen.game.LoadingScreen;
import com.omega.amazehing.ui.SaveLevelItem;

public class LevelSelectionScreen extends BaseScreen {

    private Panel screenTitlePanel;
    private Label screenTitleLabel;

    private Panel levelPanel;
    private AdvancedList<SaveLevelItem> levelList;
    private ScrollPane scroll;

    private TextButton backButton;
    private TextButton selectButton;

    private InputAdapter debugInput;

    private float minWidth = 400f;
    private float maxWidth = 600f;

    public LevelSelectionScreen(final ScreenManager screenManager, final GameCore core) {
	super(screenManager, 1);

	I18NBundle _i18n = core.getI18n();

	debugInput = new InputAdapter() {

	    @Override
	    public boolean keyUp(int keycode) {
		switch (keycode) {
		    case Keys.F5:
			screenManager.unregisterScreen(LevelSelectionScreen.this);
			screenManager.registerScreen(new LevelSelectionScreen(screenManager, core));

			break;
		}

		return true;
	    }
	};

	screenTitlePanel = new Panel(skin);
	screenTitleLabel = new Label(_i18n.get("level.selection.title"), skin);

	screenTitlePanel.add(screenTitleLabel);
	layout.add(screenTitlePanel).colspan(2).spaceBottom(10f).minWidth(minWidth)
		.maxWidth(maxWidth).prefWidth(Value.percentWidth(0.5f, layout)).row();

	levelPanel = new Panel(skin);
	levelList = new AdvancedList<SaveLevelItem>();

	scroll = new ScrollPane(levelList, skin);
	scroll.setFadeScrollBars(false);
	scroll.setForceScroll(false, false);

	levelPanel.add(scroll).expand().fill();
	layout.add(levelPanel).minWidth(minWidth).maxWidth(maxWidth)
		.prefWidth(Value.percentWidth(0.5f, layout))
		.height(Value.percentHeight(0.5f, layout)).colspan(2).spaceBottom(15f).row();

	backButton = new TextButton(_i18n.get("common.back"), skin);
	backButton.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		screenManager.hideScreen(LevelSelectionScreen.this);
		screenManager.showScreen(MainMenuScreen.class);
	    }
	});

	selectButton = new TextButton(_i18n.get("level.selection.play"), skin);
	selectButton.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		try {
		    final SaveLevelItem _item = levelList.getSelection().get(0);
		    screenManager.unregisterScreen(MainMenuScreen.class);
		    LoadingScreen _loadScreen = screenManager
			    .registerScreen(new LoadingScreen(screenManager, core));
		    _loadScreen.loadSave(_item.getFile().nameWithoutExtension());
		} catch (IndexOutOfBoundsException e) {
		    return;
		}
	    }
	});

	layout.defaults().width(150f);
	layout.add(backButton).left();
	layout.add(selectButton).right();
    }

    private final void populate() {
	FileHandle _saveDir = Gdx.files.local(Constants.Save.DIRECTORY);
	if (!_saveDir.exists()) {
	    return;
	}

	FileHandle[] _saveFiles = _saveDir.list(new FileFilter() {

	    @Override
	    public boolean accept(File pathname) {
		if (pathname.isDirectory()) {
		    return false;
		}

		if (pathname.getPath().endsWith(Constants.Save.EXTENSION)) {
		    return true;
		}

		return false;
	    }
	});

	for (FileHandle saveFile : _saveFiles) {
	    levelList.addItem(new SaveLevelItem(saveFile, skin));
	}
    }

    @Override
    protected void show() {
	super.show();

	populate();
	screenManager.getInputProcessor().addProcessor(debugInput);
    }

    @Override
    protected void hide() {
	super.hide();

	levelList.clearChildren();
	screenManager.getInputProcessor().removeProcessor(debugInput);
    }
}