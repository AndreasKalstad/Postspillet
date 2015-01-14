package com.glow.sortit.objects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Letter {

	private String nationality;
	private TextureRegion texture;
	private final String[] nationalities = {"denmark","norway", "sweden", "deutschland", "finland"};
	private Array<TextureRegion[]> all;
	private static final int FRAME_COLS_LETTER = 5;
	private static final int FRAME_ROWS_LETTER = 1;
	private Random ran;
	
    public Letter() {
    	all = new Array<TextureRegion[]>();
    	makeTextures("brev/brevMangeland.png");
		ran = new Random();
    	giveNationality();
    }
	
    public String getNationality() {
    	return nationality;
    }
    
    public TextureRegion getTextureRegion(){
    	return texture;
    }
	
    public void giveNationality(){
    	int n = 0;
    	n = ran.nextInt(5);
    	nationality = nationalities[n];
    	texture = all.get(n)[0];
    }
    
    private void makeTextures(String path){
    	Texture letterTexture = new Texture(Gdx.files.internal(path));

		TextureRegion[][] lt = TextureRegion.split(letterTexture, letterTexture.getWidth() / FRAME_COLS_LETTER, letterTexture.getHeight() / FRAME_ROWS_LETTER);
		TextureRegion[] letterRegion;
		int in = 0;
		for (int i = 0; i < FRAME_COLS_LETTER; i++) {
			letterRegion = new TextureRegion[FRAME_COLS_LETTER];
			for (int j = 0; j < FRAME_ROWS_LETTER; j++) {
				letterRegion[in++] = lt[j][i];
			}
			in = 0;
			all.add(letterRegion);
		}
    }
}
