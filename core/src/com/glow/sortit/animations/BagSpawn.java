package com.glow.sortit.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Andreas on 13.03.2015.
 */
public class BagSpawn extends Image {
    protected Animation animation = null;
    private float stateTime;
    private Texture BagSpawn;
    private TextureRegion[] bagSpawnRegion;
    private static final int FRAME_COLS_BAG_SPAWN = 2;
    private static final int FRAME_ROWS_BAG_SPAWN = 25;

    public BagSpawn(float screenWidth, float screenHeight) {
        createAnimation();
        setDrawable(new TextureRegionDrawable(animation.getKeyFrame(0)));
        ((TextureRegionDrawable) getDrawable()).setRegion(animation.getKeyFrame(0));
        this.stateTime = 0;
        setSize(screenWidth/8f, screenHeight/6.5f);
        setPosition(0, screenHeight/1.65f);
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

    //Escalator back animation
    private void createAnimation(){
        BagSpawn = new Texture(Gdx.files.internal("bagSpawn/bagSpawn.png"));

        TextureRegion[][] bs = TextureRegion.split(BagSpawn, BagSpawn.getWidth() / FRAME_COLS_BAG_SPAWN, BagSpawn.getHeight() / FRAME_ROWS_BAG_SPAWN);
        bagSpawnRegion = new TextureRegion[FRAME_COLS_BAG_SPAWN * FRAME_ROWS_BAG_SPAWN];
        int in = 0;
        for (int i = 0; i < FRAME_ROWS_BAG_SPAWN; i++) {
            for (int j = 0; j < FRAME_COLS_BAG_SPAWN; j++) {
                bagSpawnRegion[in++] = bs[i][j];
            }
        }
        Animation bagSpawnAnimation = new Animation(0.1f, bagSpawnRegion);
        animation = bagSpawnAnimation;
    }
}
