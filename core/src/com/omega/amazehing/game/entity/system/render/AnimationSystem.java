package com.omega.amazehing.game.entity.system.render;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omega.amazehing.Constants;
import com.omega.amazehing.game.entity.component.ComponentMapperHandler;
import com.omega.amazehing.game.entity.component.render.AnimationComponent;
import com.omega.amazehing.game.entity.component.render.TextureComponent;

public class AnimationSystem extends IteratingSystem {

    private static ComponentMapper<AnimationComponent> animMapper = ComponentMapperHandler
	    .getAnimationMapper();
    private static ComponentMapper<TextureComponent> textureMapper = ComponentMapperHandler
	    .getTextureMapper();

    public AnimationSystem() {
	super(Family.all(AnimationComponent.class).get(),
		Constants.Game.System.ANIMATION_SYSTEM_PRIORITY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
	AnimationComponent _animComp = animMapper.get(entity);
	Animation _anim = _animComp.getAnimation();

	TextureRegion _texture = _anim.getKeyFrame(_animComp.getStateTime());

	float _duration = _animComp.getAnimation().getAnimationDuration();
	float _stateTime = _animComp.getStateTime();
	if (_stateTime >= _duration) {
	    _animComp.setStateTime(_stateTime - _duration);
	}
	_animComp.addStateTime(deltaTime);

	TextureComponent _textureComp = textureMapper.get(entity);
	_textureComp.setTexture(_texture);
    }
}