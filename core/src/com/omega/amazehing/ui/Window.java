package com.omega.amazehing.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class Window extends com.badlogic.gdx.scenes.scene2d.ui.Window {

    private ImageButton closeButton;

    public Window(String title, Skin skin) {
	this(title, skin, "default");
    }

    public Window(String title, Skin skin, String styleName) {
	this(title, skin.get(styleName, WindowStyle.class));
    }

    public Window(String title, WindowStyle style) {
	super(title, style);

	if (title == null) {
	    throw new IllegalArgumentException("title cannot be null.");
	}

	getTitleLabel().setAlignment(Align.center);

	closeButton = new ImageButton(style.closeButtonStyle);
	closeButton.addListener(new ClickListener() {

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		onClose();
	    }
	});

	Table _titleTable = getTitleTable();
	_titleTable.padTop(5f);
	_titleTable.add(closeButton).size(16f);
	padTop(50f);
    }

    // public Actor hit(float x, float y, boolean touchable) {
    // Actor hit = super.hit(x, y, touchable);
    // if (hit == null && isModal && (!touchable || getTouchable() == Touchable.enabled)) {
    // return this;
    // }
    // float height = getHeight();
    // if (hit == null || hit == this) {
    // return hit;
    // } else if (y <= height && y >= height - getPadTop() && x >= 0 && x <= getWidth()) {
    // // Hit the title bar, don't use the hit child if it is in the Window's table.
    // Actor current = hit;
    // while (current.getParent() != this) {
    // current = current.getParent();
    // }
    // if (getCell(current) != null) {
    // return this;
    // }
    // }
    // return hit;
    // }

    /**
     * Override to implements the behavior when the window is closed.<br />
     * Set the window invisible by default.
     */
    public void onClose() {
	setVisible(false);
    }

    public static class WindowStyle extends com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle {

	public ImageButtonStyle closeButtonStyle;
    }
}
