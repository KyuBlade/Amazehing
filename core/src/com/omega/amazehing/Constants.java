package com.omega.amazehing;

import com.badlogic.gdx.Input.Keys;
import com.gdx.extension.ui.input.InputArray;
import com.gdx.extension.ui.input.InputHolder;
import com.gdx.extension.ui.input.InputType;
import com.omega.amazehing.ui.tab.setting.video.DisplayModeItem.DisplayModeType;

public class Constants {

    public static final String GAME_NAME = "A Maze Hing";

    public static final char[] acceptedChars = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
	    'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'y', 'z',
	    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
	    'R', 'R', 'T', 'U', 'V', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
	    '9', '-', '_' };

    public static class Save {

	public static final String DIRECTORY = "saves";
	public static final String EXTENSION = ".dat";
	public static final String STATIC_FILE_NAME = "level";
	public static final String DYNAMIC_FILE_NAME = "player";
    }

    public static class Settings {

	public static final String DIRECTORY = "settings/";
	public static final String FILE_NAME = "settings.cfg";

	public static class Video {

	    public static final String TAG_SCREEN_WIDTH = "video.screenWidth";
	    public static final String TAG_SCREEN_HEIGHT = "video.screenHeight";
	    public static final String TAG_VERTICAL_SYNC = "video.vSync";
	    public static final String TAG_DISPLAY_MODE = "video.displayMode";

	    public static final int DEFAULT_SCREEN_WIDTH = 800;
	    public static final int DEFAULT_SCREEN_HEIGHT = 600;
	    public static final boolean DEFAULT_VERTICAL_SYNC = false;
	    public static final DisplayModeType DEFAULT_DISPLAY_MODE = DisplayModeType.WINDOWED;
	}

	public static class Controls {

	    public static final String TAG_MOVEMENT_TYPE = "controls.movementType";
	    public static final String DEFAULT_MOVEMENT_TYPE = "absolute";

	    public static class Mapping {

		public static final String TAG_FORWARD = "controls.bind.forward";
		public static final String TAG_BACKWARD = "controls.bind.backward";
		public static final String TAG_LEFT = "controls.bind.left";
		public static final String TAG_RIGHT = "controls.bind.right";

		public static final String TAG_GAME_MENU = "controls.bind.gameMenu";
		public static final String TAG_DISPLAY_CONSOLE = "controls.bind.displayConsole";
		public static final String TAG_DISPLAY_DEBUG_SCREEN = "controls.bind.displayDebugScreen";
		public static final String TAG_DISPLAY_DEBUG = "controls.bind.displayDebug";
		public static final String TAG_RELOAD = "controls.bind.reload";
		public static final String TAG_ENTITY_VIEWER = "controls.bind.entityViewer";

		public static final String TAG_INVENTORY = "controls.bind.inventory";
		public static final String TAG_CHARACTER = "controls.bind.character";
		public static final String TAG_SKILLS = "controls.bind.skills";

		public static final String TAG_SHORTCUT_1 = "controls.bind.shortcuts.1";
		public static final String TAG_SHORTCUT_2 = "controls.bind.shortcuts.2";
		public static final String TAG_SHORTCUT_3 = "controls.bind.shortcuts.3";
		public static final String TAG_SHORTCUT_4 = "controls.bind.shortcuts.4";
		public static final String TAG_SHORTCUT_5 = "controls.bind.shortcuts.5";
		public static final String TAG_SHORTCUT_6 = "controls.bind.shortcuts.6";
		public static final String TAG_SHORTCUT_7 = "controls.bind.shortcuts.7";
		public static final String TAG_SHORTCUT_8 = "controls.bind.shortcuts.8";
		public static final String TAG_SHORTCUT_9 = "controls.bind.shortcuts.9";
		public static final String TAG_SHORTCUT_10 = "controls.bind.shortcuts.10";

		public static final InputArray DEFAULT_FORWARD = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.Z));
		public static final InputArray DEFAULT_BACWKARD = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.S));
		public static final InputArray DEFAULT_LEFT = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.Q));
		public static final InputArray DEFAULT_RIGHT = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.D));

		public static final InputArray DEFAULT_INVENTORY = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.I));
		public static final InputArray DEFAULT_CHARACTER = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.C));
		public static final InputArray DEFAULT_SKILLS = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.K));

		public static final InputArray DEFAULT_SHORTCUT_1 = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.NUM_1));
		public static final InputArray DEFAULT_SHORTCUT_2 = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.NUM_2));
		public static final InputArray DEFAULT_SHORTCUT_3 = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.NUM_3));
		public static final InputArray DEFAULT_SHORTCUT_4 = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.NUM_4));
		public static final InputArray DEFAULT_SHORTCUT_5 = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.NUM_5));
		public static final InputArray DEFAULT_SHORTCUT_6 = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.NUM_6));
		public static final InputArray DEFAULT_SHORTCUT_7 = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.NUM_7));
		public static final InputArray DEFAULT_SHORTCUT_8 = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.NUM_8));
		public static final InputArray DEFAULT_SHORTCUT_9 = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.NUM_9));
		public static final InputArray DEFAULT_SHORTCUT_10 = new InputArray(
			new InputHolder(InputType.Keyboard, Keys.NUM_0));
	    }
	}
    }

    public static class Level {

	public static final float UNIT_SCALE = 32f;
	public static final float WORLD_SCALE_FACTOR = 1f / UNIT_SCALE;
    }

    public static class Generator {

	public static final int MIN_LEVEL_WIDTH = 20;
	public static final int MIN_LEVEL_HEIGHT = 20;
	public static final int MAX_LEVEL_WIDTH = 100;
	public static final int MAX_LEVEL_HEIGHT = 100;
	public static final int MIN_ROOM_SIZE = 4;
	public static final int MAX_ROOM_SIZE = 8;
	public static final int CELL_SIZE = 6;
    }

    public static class Game {

	public static final float PAGING_PATCHES_SIZE = 20f;
	public static final int SPAWN_FAR_CHASE = 15;

	public static class System {

	    public static final int LOAD_BALANCER_SYSTEM_PRIORITY = 0;
	    public static final int MOVEMENT_SYSTEM_PRIORITY = 10;
	    public static final int PHYSICS_SYSTEM_PRIORITY = 11;
	    public static final int TRIGGER_SYSTEM_PRIORITY = 20;
	    public static final int AI_SYSTEM_PRIORITY = 30;
	    public static final int BEHAVIOR_TREE_SYSTEM_PRIORITY = 40;
	    public static final int STEERING_SYSTEM_PRIORITY = 41;
	    public static final int PATHFINDING_SYSTEM_PRIORITY = 45;
	    public static final int INTERACTIVE_OBJECT_SYSTEM_PRIORITY = 50;
	    public static final int DELAY_SYSTEM_PRIORITY = 60;

	    public static final int INVENTORY_SYSTEM_PRIORITY = 350;
	    public static final int HEALTH_SYSTEM_PRIORITY = 400;

	    public static final int CAMERA_SYSTEM_PRIORITY = 700;
	    public static final int VIEWPORT_SYSTEM_PRIORITY = 710;
	    public static final int PAGING_SYSTEM_PRIORITY = 800;
	    public static final int ANIMATION_SYSTEM_PRIORITY = 900;
	    public static final int PARTICLE_SYSTEM_PRIORITY = 901;
	    public static final int RENDER_SYSTEM_PRIORITY = 1000;
	    public static final int SAVE_SYSTEM_PRIORITY = 5000;
	    public static final int ENTITY_NOTIFIER_SYSTEM_PRIORITY = 10000;
	}
    }
}