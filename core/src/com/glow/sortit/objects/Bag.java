package com.glow.sortit.objects;

import java.util.ArrayList;

import com.glow.sortit.actors.BagActor;
import com.glow.sortit.actors.LetterActor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bag {

	private String countryString;
	private int level;
	private TextureRegion texture;
	private Country country;
	private boolean increasedLevel;

	public Bag(ArrayList<BagActor> bags, ArrayList<LetterActor> letters) {
		country = new Country();
		country.giveCountry(this);
		level = 0;
	}

	public void setCountry(String countryString) {
		this.countryString = countryString;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setTextureRegion(TextureRegion textureRegion) {
		this.texture = textureRegion;
	}

	public String getCountry() {
		return countryString;
	}

	public TextureRegion getTextureRegion(){
		return texture;
	}

	public void increaseLevel(){
		level++;
		country.updateLevel(this);
		increasedLevel = true;
	}

	public boolean isLevelIncreased(){
		return increasedLevel;
	}

	public void setLevelIncreased(){
		increasedLevel = false;
	}

	public int getLevel(){
		return level;
	}

	public boolean isFull(){
		if(level == 4){
			return true;
		}
		return false;
	}
}