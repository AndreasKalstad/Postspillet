package com.glow.sortit.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.glow.sortit.objects.Letter;

public class LetterActor extends Image{

	private Stage stage;
	private Letter letter;
	private boolean pause;
	
    public LetterActor(Letter letter, Stage stage, boolean pause) {
    	super(letter.getTextureRegion());
    	this.letter = letter;
    	this.stage = stage;
    	this.pause = pause;
    }
    
    @Override
    public Stage getStage(){
    	return stage;
    }
    
    public Letter getLetter(){
    	return letter;
    }
    
    public boolean isPaused(){
    	return pause;
    }
}