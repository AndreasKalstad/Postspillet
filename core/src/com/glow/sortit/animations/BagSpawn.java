package com.glow.sortit.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Andreas on 13.03.2015.
 */
public class BagSpawn extends Image {
    protected Animation animation = null;
    private float stateTime;
    private TextureAtlas bagSpawn;
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

    public void setBagSpawnStateTime(float stateTime){
        this.stateTime = stateTime;
    }

    public float getBagSpawnStateTime(){
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

    //Bagspawn back animation
    private void createAnimation(){
        bagSpawn = new TextureAtlas("bagSpawn/BagSpawnFinal.txt");
        bagSpawn.findRegion("BagSpawnFinal.png");

        //Anim-------------------------------------
        TextureAtlas.AtlasRegion[] trAni = new TextureAtlas.AtlasRegion[30];
        for(int i = 0; i < 50; i++)
        {
            if(i < 10) {
                trAni[i] = bagSpawn.findRegion("bagSpawn000" + i + " copy");
            } else {
                trAni[i] = bagSpawn.findRegion("bagSpawn00" + i + " copy");
            }
        }
        animation = new Animation(0.04f, trAni);
    }
}
