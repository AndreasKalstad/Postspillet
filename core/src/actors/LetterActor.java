package actors;

import objects.Letter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class LetterActor extends Image{

	private Stage stage;
	
    public LetterActor(Letter letter, Stage stage) {
    	super(letter.getTexture());
    	this.stage = stage;
    }
    
    @Override
    public Stage getStage(){
    	return stage;
    }
}