package com.omega.amazehing.ui.state;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;
import com.gdx.extension.ui.ProgressBar;

public class StateBar extends Stack {

    private StringBuilder stringBuilder;
    private ProgressBar progressBar;
    private Label label;

    public StateBar(Skin skin, String styleName) {
	stringBuilder = new StringBuilder();

	progressBar = new ProgressBar(skin, styleName);
	label = new Label("0 / 0", skin);
	label.setAlignment(Align.center);

	add(progressBar);
	add(label);

	progressBar.setPercent(0.5f);
    }

    public void setState(int currentState, int maxState) {
	progressBar.setPercent((float) currentState / (float) maxState);

	stringBuilder.setLength(0);
	stringBuilder.append(currentState).append(" / ").append(maxState);
	label.setText(stringBuilder.toString());
    }
}