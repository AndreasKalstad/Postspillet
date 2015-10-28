package com.glow.sortit.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class BagPickUp {
	private float bagPickUpMoveTime;
	private float bagPickUpStateTime;
	private Animation bagPickUpAnimation;
	private TextureAtlas bagPickUpTexture;
	private TextureRegion[] bagPickUpTextureRegion;
	
	public BagPickUp(float bagPickUpMoveTime, float bagPickUpStateTime){
		this.bagPickUpMoveTime = bagPickUpMoveTime;
		this.bagPickUpStateTime = bagPickUpStateTime;
		createAnimation();
	}
	
	public Animation getBagPickUpAnimation() {
		return bagPickUpAnimation;
	}

	public void setBagPickUpAnimation(Animation bagPickUpAnimation) {
		this.bagPickUpAnimation = bagPickUpAnimation;
	}

	public float getBagPickUpMoveTime() {
		return bagPickUpMoveTime;
	}

	public void setBagPickUpMoveTime(float bagPickUpMoveTime) {
		this.bagPickUpMoveTime = bagPickUpMoveTime;
	}

	public float getBagPickUpStateTime() {
		return bagPickUpStateTime;
	}

	public void setBagPickUpStateTime(float bagPickUpStateTime) {
		this.bagPickUpStateTime = bagPickUpStateTime;
	}

	public TextureRegion getBagPickUpFrame() {
		return bagPickUpAnimation.getKeyFrame(bagPickUpStateTime, true);
	}

	private void createAnimation(){
		bagPickUpTexture = new TextureAtlas("bagPickup/bagPickup002.txt");
		bagPickUpTexture.findRegion("bagPickup/bagPickup002.png");

        Array<TextureAtlas.AtlasRegion> test = new Array();
		//TextureAtlas.AtlasRegion[] trAni = new TextureAtlas.AtlasRegion[43];
		for(int i = 0; i < 43; i++) {
			if(i < 10) {
				if(bagPickUpTexture.findRegion("bagPickup000" + i) != null) {
					test.add(bagPickUpTexture.findRegion("bagPickup000" + i));
				}
			} else {
                test.add(bagPickUpTexture.findRegion("bagPickup00" + i));
			}
		}
		bagPickUpAnimation = new Animation(0.1f, test);
	}
}
