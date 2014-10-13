package actors;

import objects.Bag;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;


public class BagActor extends Image{

	private Bag bag;
	
    public BagActor(Bag bag, Stage stage) {
    	super(new SpriteDrawable(new Sprite(bag.getTexture())));
    	this.bag = bag;
    }
    
    public Bag getBag(){
    	return bag;
    }
    
    @Override
    public void act(float delta){
    	this.setDrawable(new SpriteDrawable(new Sprite(bag.getTexture())));
    }
}