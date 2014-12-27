package actors;

import objects.Letter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class LetterActor extends Image{

	private Stage stage;
	private Letter letter;
	
    public LetterActor(Letter letter, Stage stage) {
    	super(letter.getTextureRegion());
    	this.letter = letter;
    	this.stage = stage;
    }
    
    @Override
    public Stage getStage(){
    	return stage;
    }
    
    public Letter getLetter(){
    	return letter;
    }
}