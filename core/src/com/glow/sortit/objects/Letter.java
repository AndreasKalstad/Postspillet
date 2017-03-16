package com.glow.sortit.objects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Letter {

	private CountryEvaluator.Nationality nationality;
	private TextureRegion texture;
	private final CountryEvaluator.Nationality[] nationalities = {CountryEvaluator.Nationality.denmark, CountryEvaluator.Nationality.norway, CountryEvaluator.Nationality.sweden,
			CountryEvaluator.Nationality.finland, CountryEvaluator.Nationality.deutschland};
	private Array<TextureRegion[]> all;
	private static final int FRAME_COLS_LETTER = 5;
	private static final int FRAME_ROWS_LETTER = 1;
	private Random ran;
	private CountryEvaluator ce;

	public Letter() {
		all = new Array<TextureRegion[]>();
		makeTextures("brev/brevMangeland.png");
		ran = new Random();
		ce = new CountryEvaluator();
		giveNationality();
	}

	public String getNationality() {
		return nationality.name();
	}

	public TextureRegion getTextureRegion(){
		return texture;
	}

	public void giveNationality(){
		int n = ran.nextInt(5);
		nationality = nationalities[n];
		texture = all.get(n)[0];
		ce.addLetter(nationality);
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
