package objects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Letter {

	private String nationality;
	private final String[] nationalities = {"norway","sweden", "denmark"};
	private Texture texture;
	private String[] textures = {"Brev1.png","Brev1.png", "Brev1.png"};
	
    public Letter() {
    	giveNationality();
    }
	
    public String getNationality() {
    	return nationality;
    }
    
    public Texture getTexture(){
    	return texture;
    }
	
    public void giveNationality(){
    	Random ran = new Random();
    	int ranInt = ran.nextInt(2);
    	nationality = nationalities[ranInt];
    	texture = new Texture(Gdx.files.internal(textures[ranInt]));
    }
}
