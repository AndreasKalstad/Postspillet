package objects;

import java.util.Random;
import user.User;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Country {
	
	private String[] countries = {"norway","sweden", "denmark"};
	private Array<String[]> all;
	private User user;
	
	public Country(){
		this.user = new User();
		String[] norway = {"Bag/Bag_st0.png","Bag/Bag_st2.png", "Bag/Bag_st3.png","Bag/Bag_st4.png"};
		String[] sweden = {"Bag/Bag_st0.png","Bag/Bag_st2.png", "Bag/Bag_st3.png","Bag/Bag_st4.png"};
		String[] denmark = {"Bag/Bag_st0.png","Bag/Bag_st2.png", "Bag/Bag_st3.png","Bag/Bag_st4.png"};
		String[] iceland = {"Bag/Bag_st0.png","Bag/Bag_st2.png", "Bag/Bag_st3.png","Bag/Bag_st4.png"};
		//----------- Old code --------------<<
		//String[] iceland = {"bag1.jpg","bag2.png", "droplet.png","Bag001.png"};
		// ------------------
		
		all = new Array<String[]>();
		all.add(norway);
		all.add(sweden);
		all.add(denmark);
		all.add(iceland);
	}
	
    public void giveCountry(Bag bag){
    	Random ran = new Random();
    	int ranInt = ran.nextInt(user.getRange());
    	bag.setCountry(countries[ranInt]);
    	bag.setTexture(new Texture(Gdx.files.internal(all.get(ranInt)[bag.getLevel()])));
    }
    
    public void updateLevel(Bag bag){
    	int index = 0;
    	for(int i = 0; i<countries.length; i++){
    		if(bag.getCountry().equals(countries[i])){
    			index = i;
    		}
    	}
    	if(bag.getLevel() < 4){
    		bag.setTexture(new Texture(Gdx.files.internal(all.get(index)[bag.getLevel()])));
    	}
    }
}
