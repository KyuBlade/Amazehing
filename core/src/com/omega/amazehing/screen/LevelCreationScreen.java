package com.omega.amazehing.screen;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.gdx.extension.screen.BaseScreen;
import com.gdx.extension.screen.ScreenManager;
import com.gdx.extension.ui.panel.Panel;
import com.omega.amazehing.Constants;
import com.omega.amazehing.GameCore;
import com.omega.amazehing.game.level.LevelSetting;
import com.omega.amazehing.game.level.generator.GenerationAlgorithm;
import com.omega.amazehing.screen.game.LoadingScreen;
import com.omega.amazehing.util.Utils;

public class LevelCreationScreen extends BaseScreen {

    private Panel screenTitlePanel;
    private Label screenTitleLabel;

    private Panel settingPanel;

    private Label nameLabel;
    private TextField nameValue;

    private Label seedLabel;
    private TextField seedValue;
    private ImageButton randSeed;

    private Label algoLabel;
    private SelectBox<GenerationAlgorithm> algoValue;

    private Label widthLabel;
    private Slider widthSlider;
    private TextField widthValue;

    private Label heightLabel;
    private Slider heightSlider;
    private TextField heightValue;

    private TextButton backButton;
    private TextButton createButton;

    private InputAdapter debugInput;

    public LevelCreationScreen(final ScreenManager screenManager, final GameCore core) {
	super(screenManager, 1);

	I18NBundle _i18n = core.getI18n();

	debugInput = new InputAdapter() {

	    @Override
	    public boolean keyUp(int keycode) {
		switch (keycode) {
		    case Keys.F5:
			screenManager.unregisterScreen(LevelCreationScreen.this);
			screenManager.registerScreen(new LevelCreationScreen(screenManager, core));

			break;
		}

		return true;
	    }
	};

	screenTitlePanel = new Panel(skin);
	screenTitleLabel = new Label(_i18n.get("level.creation.title"), skin);

	screenTitlePanel.add(screenTitleLabel);
	layout.add(screenTitlePanel).fillX().colspan(2).spaceBottom(10f).row();

	settingPanel = new Panel(skin);
	settingPanel.defaults().left().spaceBottom(5f).spaceRight(10f);
	settingPanel.pad(10f);

	// Name
	nameLabel = new Label(_i18n.get("level.creation.name"), skin);
	nameValue = new TextField("", skin);
	nameValue.setTextFieldFilter(new TextFieldFilter() {

	    @Override
	    public boolean acceptChar(TextField textField, char c) {
		char[] _acceptedChars = Constants.acceptedChars;
		for (int i = 0; i < _acceptedChars.length; i++) {
		    if (_acceptedChars[i] == c)
			return true;
		}
		return false;
	    }
	});

	settingPanel.add(nameLabel);
	settingPanel.add(nameValue).width(200f).row();

	// Seed
	seedLabel = new Label("Seed : ", skin);
	seedValue = new TextField(Long.toString(Utils.randomSeed()), skin);
	randSeed = new ImageButton(skin, "refresh");
	randSeed.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		seedValue.setText(Long.toString(Utils.randomSeed()));
	    }
	});

	settingPanel.add(seedLabel);
	settingPanel.add(seedValue).width(200f);
	settingPanel.add(randSeed).size(32f, 32f).row();

	// Algorithm
	algoLabel = new Label(_i18n.get("level.creation.algo"), skin);
	settingPanel.add(algoLabel);

	algoValue = new SelectBox<GenerationAlgorithm>(skin);
	algoValue.setItems(GenerationAlgorithm.values());
	settingPanel.add(algoValue).width(200f).row();

	// Level width
	widthLabel = new Label(_i18n.get("level.creation.width"), skin);
	settingPanel.add(widthLabel);

	widthSlider = new Slider(20f, 100f, 1f, false, skin);
	widthSlider.addListener(new ChangeListener() {

	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		widthValue.setText(Integer.toString((int) widthSlider.getValue()));
	    }
	});
	settingPanel.add(widthSlider).fillX();

	widthValue = new TextField("", skin);
	widthValue.addListener(new InputListener() {

	    @Override
	    public boolean keyUp(InputEvent event, int keycode) {
		if (keycode == Keys.ENTER) {
		    try {
			widthSlider.setValue(Integer.valueOf(widthValue.getText()));
		    } catch (NumberFormatException e) {

		    }
		    return true;
		}

		return false;
	    }
	});
	settingPanel.add(widthValue).maxWidth(50f).row();
	widthSlider.fire(new ChangeEvent());

	// Level height
	heightLabel = new Label(_i18n.get("level.creation.height"), skin);
	settingPanel.add(heightLabel);

	heightSlider = new Slider(20f, 100f, 1f, false, skin);
	heightSlider.addListener(new ChangeListener() {

	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		heightValue.setText(Integer.toString((int) heightSlider.getValue()));
	    }
	});
	settingPanel.add(heightSlider).fillX();

	heightValue = new TextField("", skin);
	heightValue.addListener(new InputListener() {

	    @Override
	    public boolean keyUp(InputEvent event, int keycode) {
		if (keycode == Keys.ENTER) {
		    try {
			heightSlider.setValue(Integer.valueOf(heightValue.getText()));
		    } catch (NumberFormatException e) {

		    }
		    return true;
		}

		return false;
	    }
	});
	settingPanel.add(heightValue).maxWidth(50f).row();
	heightSlider.fire(new ChangeEvent());

	layout.add(settingPanel).colspan(2).spaceBottom(15f).fillX().row();

	// Add bottom buttons
	backButton = new TextButton(_i18n.get("common.back"), skin);
	backButton.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {

		screenManager.showScreen(MainMenuScreen.class);
		screenManager.hideScreen(LevelCreationScreen.this);
	    }
	});

	createButton = new TextButton(_i18n.get("level.creation.create"), skin);
	createButton.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		if (validateForm()) {
		    final LevelSetting _setting = new LevelSetting(nameValue.getText(),
			    algoValue.getSelected(), Long.valueOf(seedValue.getText()),
			    Integer.valueOf(widthValue.getText()),
			    Integer.valueOf(heightValue.getText()), Constants.Generator.CELL_SIZE);

		    screenManager.unregisterScreen(LevelSelectionScreen.class);
		    screenManager.unregisterScreen(LevelCreationScreen.this);
		    screenManager.unregisterScreen(MainMenuScreen.class);

		    LoadingScreen _loadScreen = screenManager
			    .registerScreen(new LoadingScreen(screenManager, core));
		    _loadScreen.generate(_setting);
		}
	    }
	});

	layout.defaults().width(150f);
	layout.add(backButton).left().spaceRight(100f);
	layout.add(createButton).right();

	// layout.debugAll();
    }

    private boolean validateForm() {
	// Name
	String _name = nameValue.getText();
	if (_name.isEmpty()) {
	    // showPopup(GameCore.getI18n().get("common.error"),
	    // GameCore.getI18n().get("level.creation.error.name.empty"));
	    return false;
	} else {
	    FileHandle _file = Gdx.files.internal(
		    Constants.Save.DIRECTORY + File.separator + _name + Constants.Save.EXTENSION);
	    if (!_file.isDirectory() && _file.exists()) {
		// showPopup(GameCore.getI18n().get("common.error"),
		// GameCore.getI18n().get("level.creation.error.name.used"));
		return false;
	    }
	}

	// Seed
	try {
	    Long.valueOf(seedValue.getText());
	} catch (NumberFormatException e) {
	    // showPopup(GameCore.getI18n().get("common.error"),
	    // GameCore.getI18n().get("level.creation.error.seed.invalid"));
	    return false;
	}

	// Width
	int _width = 0;
	try {
	    _width = Integer.valueOf(widthValue.getText());
	} catch (NumberFormatException e) {
	    // showPopup(GameCore.getI18n().get("common.error"),
	    // GameCore.getI18n().get("level.creation.error.width.invalid"));
	    return false;
	}
	if (_width < Constants.Generator.MIN_LEVEL_WIDTH) {
	    // showPopup(
	    // GameCore.getI18n().get("common.error"),
	    // GameCore.getI18n().format("level.creation.error.width.min",
	    // Constants.Generator.MIN_LEVEL_WIDTH));
	    return false;
	} else if (_width > Constants.Generator.MAX_LEVEL_WIDTH) {
	    // showPopup(
	    // GameCore.getI18n().get("common.error"),
	    // GameCore.getI18n().format("level.creation.error.width.max",
	    // Constants.Generator.MAX_LEVEL_WIDTH));
	    return false;
	}

	// Height
	int _height = 0;
	try {
	    _height = Integer.valueOf(heightValue.getText());
	} catch (NumberFormatException e) {
	    // showPopup(GameCore.getI18n().get("common.error"),
	    // GameCore.getI18n().get("level.creation.error.height.invalid"));
	    return false;
	}
	if (_height < Constants.Generator.MIN_LEVEL_HEIGHT) {
	    // showPopup(
	    // GameCore.getI18n().get("common.error"),
	    // GameCore.getI18n().format("level.creation.error.height.min",
	    // Constants.Generator.MIN_LEVEL_HEIGHT));
	    return false;
	} else if (_height > Constants.Generator.MAX_LEVEL_HEIGHT) {
	    // showPopup(
	    // GameCore.getI18n().get("common.error"),
	    // GameCore.getI18n().format("level.creation.error.height.max",
	    // Constants.Generator.MAX_LEVEL_HEIGHT));
	    return false;
	}

	return true;
    }

    @Override
    protected void show() {
	super.show();

	DateFormat _dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
	Calendar _calendar = Calendar.getInstance();
	nameValue.setText(_dateFormat.format(_calendar.getTime()));

	screenManager.getInputProcessor().addProcessor(debugInput);
    }

    @Override
    protected void hide() {
	super.hide();

	screenManager.getInputProcessor().removeProcessor(debugInput);
    }
}