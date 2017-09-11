package com.omega.amazehing.ui.tab.setting.control;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.extension.screen.ScreenManager;
import com.gdx.extension.ui.input.ControlBindList;
import com.gdx.extension.ui.input.InputArray;
import com.gdx.extension.ui.input.InputCatcher.InputCatcherListener;
import com.omega.amazehing.Constants;
import com.omega.amazehing.setting.SettingManager;
import com.omega.amazehing.setting.event.controls.InputMappingSettingEvent;
import com.omega.amazehing.setting.event.controls.MovementInputSettingEvent;
import com.omega.amazehing.ui.tab.setting.SettingContainer;

public class ControlSettingContainer extends SettingContainer {

    private ScreenManager screenManager;

    private Label movementLabel;
    private ButtonGroup<ImageTextButton> movementGroup;
    private ImageTextButton absoluteButton;
    private ImageTextButton relativeButton;
    private Label bindingsLabel;
    private ScrollPane scrollPane;
    private ControlBindList controlBindList;

    public ControlSettingContainer(ScreenManager screenManager, SettingManager settingManager,
	    I18NBundle i18n, Skin skin, final ObjectMap<String, Object> settingChanged) {
	super(settingManager, i18n, skin, settingChanged);

	this.screenManager = screenManager;

	movementLabel = new Label(i18n.get("settings.movement.label"), skin);

	movementGroup = new ButtonGroup<ImageTextButton>();

	absoluteButton = new ImageTextButton(i18n.get("settings.movement.absolute"), skin);
	relativeButton = new ImageTextButton(i18n.get("settings.movement.relative"), skin);

	absoluteButton.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		settingChanged.put(Constants.Settings.Controls.TAG_MOVEMENT_TYPE, "absolute");
	    }
	});
	relativeButton.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		settingChanged.put(Constants.Settings.Controls.TAG_MOVEMENT_TYPE, "relative");
	    }
	});

	movementGroup.add(absoluteButton, relativeButton);

	pad(10f).top().left().setFillParent(true);
	add(movementLabel).left().spaceBottom(10f).colspan(2).row();
	add(absoluteButton).padLeft(20f).spaceRight(50f);
	add(relativeButton).row();

	bindingsLabel = new Label(i18n.get("settings.controls.bindings"), skin);
	add(bindingsLabel).left().spaceTop(20f).spaceBottom(10f).colspan(2).row();

	controlBindList = new ControlBindList("Catching ...", skin);
	controlBindList.addControlListener(new InputCatcherListener() {

	    @Override
	    public void onControlChanged(String tag, String name, InputArray inputs) {
		settingChanged.put(tag, inputs);
	    }
	});
	scrollPane = new ScrollPane(controlBindList, skin);
	scrollPane.setFadeScrollBars(false);
	scrollPane.setScrollbarsOnTop(false);
	scrollPane.setScrollingDisabled(true, false);
	scrollPane.setOverscroll(false, false);
	add(scrollPane).expand().fillX().colspan(2);

	debug();
    }

    @Override
    public void initialize() {
	settingManager.setEvent(Constants.Settings.Controls.TAG_MOVEMENT_TYPE,
		new MovementInputSettingEvent(screenManager, absoluteButton, relativeButton));

	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_FORWARD);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_BACKWARD);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_LEFT);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_RIGHT);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_INVENTORY);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_CHARACTER);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_SKILLS);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_1);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_2);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_3);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_4);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_5);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_6);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_7);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_8);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_9);
	addInputCatcher(Constants.Settings.Controls.Mapping.TAG_SHORTCUT_10);
    }

    private void addInputCatcher(String tag) {
	controlBindList.addInputCatchRow(tag, i18n.get("settings." + tag),
		settingManager.getInputArray(tag));
	settingManager.setEvent(tag, new InputMappingSettingEvent(controlBindList));
    }

    public void focus() {
	screenManager.getStage().setScrollFocus(scrollPane);
    }
}