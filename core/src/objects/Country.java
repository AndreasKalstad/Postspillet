package objects;

import java.util.Random;
import user.User;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

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
	
    public void giveCountry(Bag bag){
    	Random ran = new Random();
    	int ranInt = ran.nextInt(user.getRange());
    	bag.setCountry(countries[ranInt]);
    	bag.setTextureRegion(all.get(ranInt)[0]);
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
