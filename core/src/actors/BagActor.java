package actors;

import objects.Bag;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;


public class BagActor extends Image{

	private Bag bag;
	private int startX;
	
    public BagActor(Bag bag, Stage stage, int startX) {
    	super(new SpriteDrawable(new Sprite(bag.getTextureRegion())));
    	this.bag = bag;
    	this.startX = startX;
    }
    
    public Bag getBag(){
    	return bag;
    }
    
    public int getStartX(){
    	return startX;
    }
    
    @Override
    public void act(float delta){
    	this.setDrawable(new SpriteDrawable(new Sprite(bag.getTextureRegion())));
    }
}