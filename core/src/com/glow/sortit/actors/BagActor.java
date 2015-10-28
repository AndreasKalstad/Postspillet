package com.glow.sortit.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.glow.sortit.objects.Bag;

public class BagActor extends Image{

	private Bag bag;
	
    public BagActor(Bag bag, float startX, float screenWidth, float screenHeight) {
        this.bag = bag;
        this.setDrawable(new SpriteDrawable(new Sprite(bag.getTextureRegion())));
        setBounds(startX, screenHeight / 1.75f, screenWidth / 9.5f, screenHeight / 4f);
    }
    
    public Bag getBag(){
    	return bag;
    }

    @Override
    public void act(float delta) {
        if (bag.isLevelIncreased()){
            this.setDrawable(new SpriteDrawable(new Sprite(bag.getTextureRegion())));
            bag.setLevelIncreased();
        }
        super.act(delta);
    }
}