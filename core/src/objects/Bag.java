package objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bag {

	private String countryString;
	private int level;
	private TextureRegion texture;
	private Country country;
	
    public Bag() {
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