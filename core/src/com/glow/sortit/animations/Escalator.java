package com.glow.sortit.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Andreas on 12.03.2015.
 */

public class Escalator extends Image {
    protected Animation animation = null;
    private float stateTime;
    private Texture escalator;
    private Texture escalator2;
    private int FRAME_COLS_ESCALATOR = 2;
    private int FRAME_ROWS_ESCALATOR = 14;
    private int FRAME_COLS_ESCALATOR2 = 2;
    private int FRAME_ROWS_ESCALATOR2 = 6;
    private TextureRegion[] escalatorRegion;

    public Escalator(float screenWidth, float screenHeight) {
        createAnimation();
        setDrawable(new TextureRegionDrawable(animation.getKeyFrame(0)));
        ((TextureRegionDrawable) getDrawable()).setRegion(animation.getKeyFrame(0));
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
        ((TextureRegionDrawable) getDrawable()).setRegion(animation.getKeyFrame(stateTime, true));
        super.act(delta);
    }

    public void createAnimation(){
        //Escalator back animation
        escalator = new Texture(Gdx.files.internal("escalator/escalator0101.png"));
        escalator2 = new Texture(Gdx.files.internal("escalator/escalator0102.png"));

        TextureRegion[][] tmp = TextureRegion.split(escalator, escalator.getWidth() / FRAME_COLS_ESCALATOR, escalator.getHeight() / FRAME_ROWS_ESCALATOR);
        TextureRegion[][] tmp2 = TextureRegion.split(escalator2, escalator2.getWidth() / FRAME_COLS_ESCALATOR2, escalator2.getHeight() / FRAME_ROWS_ESCALATOR2);
        escalatorRegion = new TextureRegion[(FRAME_COLS_ESCALATOR * FRAME_ROWS_ESCALATOR) + (FRAME_COLS_ESCALATOR2 * FRAME_ROWS_ESCALATOR2)];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS_ESCALATOR; i++) {
            for (int j = 0; j < FRAME_COLS_ESCALATOR; j++) {
                escalatorRegion[index++] = tmp[i][j];
            }
        }
        for (int i = 0; i < FRAME_ROWS_ESCALATOR2; i++) {
            for (int j = 0; j < FRAME_COLS_ESCALATOR2; j++) {
                escalatorRegion[index++] = tmp2[i][j];
            }
        }
        Animation escalatorAnimation = new Animation(0.1f, escalatorRegion);
        animation = escalatorAnimation;
    }
}
