package objects;

import java.util.ArrayList;
import java.util.Random;

import actors.BagActor;
import actors.LetterActor;

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
	
    public Letter(ArrayList<BagActor> bags, ArrayList<LetterActor> letters) {
    	all = new Array<TextureRegion[]>();
    	makeTextures("brev/brevMangeland.png");
		ran = new Random();
    	giveNationality(bags, letters);
    }
	
    public String getNationality() {
    	return nationality;
    }
    
    public TextureRegion getTextureRegion(){
    	return texture;
    }
	
    public void giveNationality(ArrayList<BagActor> bags, ArrayList<LetterActor> letters){
    	int n = 0;
    	if(bags.size() == 0){
    		n = ran.nextInt(5);
    	} else {
	    	int[] levelBags = new int[5];
	    	int[] numCon = new int[5];
	    	int[] posCon = new int[5];
	    	for(int i = 0; i<bags.size(); i++){
	    		if(bags.get(i).getBag().getCountry().equals("denmark")){
	        		levelBags[0] = bags.get(i).getBag().getLevel();
	        		posCon[0] = i;
	    			int r = numCon[0];
	    			r++;
	    			numCon[0] = r;
	    		}
	    		if(bags.get(i).getBag().getCountry().equals("finland")){
	    			levelBags[0] = bags.get(i).getBag().getLevel();
	    			posCon[1] = i;
	    			int r = numCon[1];
	    			r++;
	    			numCon[1] = r;
	    		}
	    		if(bags.get(i).getBag().getCountry().equals("norway")){
	    			levelBags[0] = bags.get(i).getBag().getLevel();
	    			posCon[2] = i;
	    			int r = numCon[2];
	    			r++;
	    			numCon[2] = r;
	    		}
	    		if(bags.get(i).getBag().getCountry().equals("sweden")){
	    			posCon[3] = i;
	    			levelBags[3] = bags.get(i).getBag().getLevel();
	    			int r = numCon[3];
	    			r++;
	    			numCon[3] = r;
	    		}
	    		if(bags.get(i).getBag().getCountry().equals("deutschland")){
	    			posCon[4] = i;
	    			levelBags[4] = bags.get(i).getBag().getLevel();
	    			int r = numCon[4];
	    			r++;
	    			numCon[4] = r;
	    		}
	    	}
	    	int[] numNat = new int[5];
	    	for(int i = 0; i<letters.size(); i++){
	    		if(letters.get(i).getLetter().getNationality().equals("denmark")){
	    			int r = numNat[0];
	    			r++;
	    			numNat[0] = r;
	    		}
	    		if(letters.get(i).getLetter().getNationality().equals("finland")){
	    			int r = numNat[1];
	    			r++;
	    			numNat[1] = r;
	    		}
	    		if(letters.get(i).getLetter().getNationality().equals("norway")){
	    			int r = numNat[2];
	    			r++;
	    			numNat[2] = r;
	    		}
	    		if(letters.get(i).getLetter().getNationality().equals("sweden")){
	    			int r = numNat[3];
	    			r++;
	    			numNat[3] = r;
	    		}
	    		if(letters.get(i).getLetter().getNationality().equals("deutschland")){
	    			int r = numNat[4];
	    			r++;
	    			numNat[4] = r;
	    		}
	    	}
	    	n = formula(levelBags, numCon, numNat, posCon);
    	}
    	nationality = nationalities[n];
    	texture = all.get(n)[0];
    }
    
    public int formula(int[] bagLevel, int[] numCon, int[] numNat, int[] posCon){
    	double[] score = new double[5];
    	for(int i = 0; i<5; i++){
    		if(numCon[i] != 0){
    			int level = bagLevel[i];
    			int numLet = numNat[i];
    			int numBag = numCon[i];
    			score[i] = ((((numBag*4))/((numLet+1)+(level+1)))*posCon[i])+(ran.nextInt(5))+ran.nextDouble();
    		} else {
    			score[i] = (double)ran.nextInt(5)+ran.nextDouble();
    		}
    		System.out.println(i + ": " + score[i]);
    	}
		double balance = 0;
		int result = 0;
    	for(int i = 0; i<5; i++){
    		if(score[i] > balance){
    			balance = score[i];
    			result = i;
    		}
    	}
    	return result;
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
