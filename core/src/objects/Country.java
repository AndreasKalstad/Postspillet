package objects;

import java.util.Random;
import user.User;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Country {
	
	private String[] countries = {"norway"};
	private Array<TextureRegion[]> all;
	private User user;
	private static final int FRAME_COLS_BAG_SPAWN = 4;
	private static final int FRAME_ROWS_BAG_SPAWN = 1;
	
	public Country(){
		this.user = new User();
		
		TextureRegion[] norway = makeTextures("Bag/bagSpriteNorway.png");
		all = new Array<TextureRegion[]>();
		all.add(norway);
		
/*		String[] norway = {"Bag/Bag001.png","Bag/Bag002.png", "Bag/Bag003.png","Bag/Bag004.png"};
		String[] sweden = {"Bag/Bag001.png","Bag/Bag002.png", "Bag/Bag003.png","Bag/Bag004.png"};
		String[] denmark = {"Bag/Bag001.png","Bag/Bag002.png", "Bag/Bag003.png","Bag/Bag004.png"};
		String[] iceland = {"Bag/Bag001.png","Bag/Bag002.png", "Bag/Bag003.png","Bag/Bag004.png"};
		//----------- Old code --------------<<
		//String[] iceland = {"bag1.jpg","bag2.png", "droplet.png","Bag001.png"};
		// ------------------
		
		all = new Array<String[]>();
		all.add(norway);
		all.add(sweden);
		all.add(denmark);
		all.add(iceland); */
	}
	
    public void giveCountry(Bag bag){
    	Random ran = new Random();
    	int ranInt = ran.nextInt(user.getRange());
    	bag.setCountry(countries[ranInt]);
    	bag.setTextureRegion(all.get(0)[0]);
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
    
    private TextureRegion[] makeTextures(String path){
    	Texture BagSpawn = new Texture(Gdx.files.internal(path));

		TextureRegion[][] bs = TextureRegion.split(BagSpawn, BagSpawn.getWidth() / FRAME_COLS_BAG_SPAWN, BagSpawn.getHeight() / FRAME_ROWS_BAG_SPAWN);
		TextureRegion[] bagSpawnRegion = new TextureRegion[FRAME_COLS_BAG_SPAWN * FRAME_ROWS_BAG_SPAWN];
		int in = 0;
		for (int i = 0; i < FRAME_ROWS_BAG_SPAWN; i++) {
			for (int j = 0; j < FRAME_COLS_BAG_SPAWN; j++) {
				bagSpawnRegion[in++] = bs[i][j];
			}
		}
		return bagSpawnRegion;
    }
}
