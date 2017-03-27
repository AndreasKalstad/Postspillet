package com.glow.sortit.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.glow.sortit.objects.Letter;

import java.util.Random;

public class LetterActor extends Image {

	private Stage stage;
	private Letter letter;
	
    public LetterActor(Letter letter, Stage stage) {
    	super(letter.getTextureRegion());
    	this.letter = letter;
    	this.stage = stage;
        Random ran = new Random();
        setSize(Gdx.graphics.getWidth() / 5.5f, Gdx.graphics.getHeight() / 5.9f);
        setPosition(ran.nextInt((int) (Gdx.graphics.getWidth() - getWidth())), ran.nextInt((int) (Gdx.graphics.getHeight() / 2 - getHeight() / 2)));
    }
    
    @Override
    public Stage getStage(){
    	return stage;
    }
    
    public Letter getLetter(){
    	return letter;
    }
}