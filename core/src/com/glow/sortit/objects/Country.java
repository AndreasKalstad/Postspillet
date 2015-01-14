package com.glow.sortit.objects;

import java.util.ArrayList;
import java.util.Random;
import com.glow.sortit.actors.BagActor;
import com.glow.sortit.actors.LetterActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.glow.sortit.user.User;

public class Country {
	
	private String[] countries = {"denmark", "finland", "norway", "sweden","deutschland"};
	private Array<TextureRegion[]> all;
	private User user;
	private static final int FRAME_COLS_BAG_SPAWN = 5;
	private static final int FRAME_ROWS_BAG_SPAWN = 5;
	
	public Country(){
		this.user = new User();

		all = new Array<TextureRegion[]>();
		makeTextures("Bag/bagMangeland.png");
	}
	
    public void giveCountry(Bag bag, ArrayList<BagActor> bags, ArrayList<LetterActor> letters){
    	Random ran = new Random();
    	int ranInt = ran.nextInt(user.getRange());
    	bag.setCountry(countries[ranInt]);
    	bag.setTextureRegion(all.get(ranInt)[0]);
    }
    
    private int nextBag(ArrayList<BagActor> bags, ArrayList<LetterActor> letters){
    	int index = 0;
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
    		if(bags.get(i).getBag().getCountry().equals("norway")){
    			levelBags[0] = bags.get(i).getBag().getLevel();
    			posCon[1] = i;
    			int r = numCon[1];
    			r++;
    			numCon[1] = r;
    		}
    		if(bags.get(i).getBag().getCountry().equals("sweden")){
    			levelBags[0] = bags.get(i).getBag().getLevel();
    			posCon[2] = i;
    			int r = numCon[2];
    			r++;
    			numCon[2] = r;
    		}
    		if(bags.get(i).getBag().getCountry().equals("deutschland")){
    			posCon[3] = i;
    			levelBags[3] = bags.get(i).getBag().getLevel();
    			int r = numCon[3];
    			r++;
    			numCon[3] = r;
    		}
    		if(bags.get(i).getBag().getCountry().equals("finland")){
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
    		if(letters.get(i).getLetter().getNationality().equals("norway")){
    			int r = numNat[1];
    			r++;
    			numNat[1] = r;
    		}
    		if(letters.get(i).getLetter().getNationality().equals("sweden")){
    			int r = numNat[2];
    			r++;
    			numNat[2] = r;
    		}
    		if(letters.get(i).getLetter().getNationality().equals("deutschland")){
    			int r = numNat[3];
    			r++;
    			numNat[3] = r;
    		}
    		if(letters.get(i).getLetter().getNationality().equals("finland")){
    			int r = numNat[4];
    			r++;
    			numNat[4] = r;
    		}
    	}
    	return index;
    }
    
    public void updateLevel(Bag bag){
    	int index = 0;
    	for(int i = 0; i<countries.length; i++){
    		if(bag.getCountry().equals(countries[i])){
    			index = i;
    		}
    	}
    	if(bag.getLevel() < 4){
    		bag.setTextureRegion(all.get(index)[bag.getLevel()]);
    	}
    }
    
    private void makeTextures(String path){
    	Texture BagSpawn = new Texture(Gdx.files.internal(path));

		TextureRegion[][] bs = TextureRegion.split(BagSpawn, BagSpawn.getWidth() / FRAME_COLS_BAG_SPAWN, BagSpawn.getHeight() / FRAME_ROWS_BAG_SPAWN);
		TextureRegion[] bagSpawnRegion;
		int in = 0;
		for (int i = 0; i < FRAME_ROWS_BAG_SPAWN; i++) {
			bagSpawnRegion = new TextureRegion[FRAME_COLS_BAG_SPAWN];
			for (int j = 0; j < FRAME_COLS_BAG_SPAWN; j++) {
				bagSpawnRegion[in++] = bs[i][j];
			}
			in = 0;
			all.add(bagSpawnRegion);
		}
    }
}
