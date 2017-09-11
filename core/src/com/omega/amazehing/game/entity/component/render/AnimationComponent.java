package com.omega.amazehing.game.entity.component.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Pool.Poolable;

public class AnimationComponent implements Component, Poolable {

    private Animation animation;
    private float stateTime;

    public AnimationComponent() {
    }

    public Animation getAnimation() {
	return animation;
    }

    public AnimationComponent setAnimation(Animation animation) {
	this.animation = animation;

	return this;
    }

    public float getStateTime() {
	return stateTime;
    }

    public AnimationComponent addStateTime(float deltaTime) {
	stateTime += deltaTime;

	return this;
    }

    public AnimationComponent setStateTime(float stateTime) {
	this.stateTime = stateTime;

	return this;
    }

    @Override
    public void reset() {
	animation = null;
	stateTime = 0f;
    }
}