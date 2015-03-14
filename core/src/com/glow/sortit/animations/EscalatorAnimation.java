package com.glow.sortit.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Andreas on 12.03.2015.
 */

public class EscalatorAnimation extends Image {
    protected Animation animation = null;
    private float stateTime;

    public EscalatorAnimation(Animation animation, float screenWidth, float screenHeight) {
        super(animation.getKeyFrame(0));
        this.animation = animation;
        this.stateTime = 0;
        setSize(screenWidth, screenHeight / 3.5f);
        setPosition(0, screenHeight / 2.4f);
    }

    public void setEscalatorStateTime(float stateTime){
        this.stateTime = stateTime;
    }

    public float getEscalatorStateTime(){
        return stateTime;
    }

    public Animation getAnimation(){
        return animation;
    }

    @Override
    public void act(float delta) {
        ((TextureRegionDrawable)getDrawable()).setRegion(animation.getKeyFrame(stateTime, true));
        super.act(delta);
    }
}
