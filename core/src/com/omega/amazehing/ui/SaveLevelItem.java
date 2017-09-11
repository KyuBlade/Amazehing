package com.omega.amazehing.ui;

import java.io.File;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.extension.ui.list.ListRow;

public class SaveLevelItem extends ListRow {

    private FileHandle file;
    private Label levelTitle;

    public SaveLevelItem(File file, Skin skin) {
	this(new FileHandle(file), skin);
    }

    public SaveLevelItem(FileHandle file, Skin skin) {
	super(skin);

	this.file = file;
	levelTitle = new Label(file.nameWithoutExtension(), skin);
	add(levelTitle);
	left();
    }

    public FileHandle getFile() {
	return file;
    }

}
