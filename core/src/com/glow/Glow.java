package com.glow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Glow implements ApplicationListener {
	   private Array<Letter> letters;
	   private Sound dropSound;
	   private Music rainMusic;
	   private OrthographicCamera camera;
	   private SpriteBatch batch;
	   private ArrayList<Bag> bags;
	   private long lastBagTime;
	   private long lastLetterTime;
	   private int[] bagValues;
	   private Animation escalatorAnimation;
	   private Texture escalator;
	   private TextureRegion[] escalatorRegion;
	   private TextureRegion currentFrame;
	   private float stateTime;
	   private static final int	FRAME_COLS = 5;
	   private static final int	FRAME_ROWS = 4;
	   private float escalatorMoveTime = 0;

	   @Override
	   public void create() {
		  letters = new Array<Letter>();
		  letters.add(new Letter(randomRectangle(), randomTexture()));
		
		  dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		  rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		
		  //rainMusic.setLooping(true);
		  //rainMusic.play();
		  
		  camera = new OrthographicCamera();
		  camera.setToOrtho(false, 800, 480);
		  
		  batch = new SpriteBatch();
		  
		  bagValues = new int[]{0, 100,200,300,400,500};
		  bags = new ArrayList<Bag>(6);
		  
		  escalator = new Texture(Gdx.files.internal("SS_Rullebond_Ver001.png"));
		  
			TextureRegion[][] tmp = TextureRegion.split(escalator, escalator.getWidth()/FRAME_COLS, escalator.getHeight()/FRAME_ROWS);              // #10
			escalatorRegion = new TextureRegion[FRAME_COLS * FRAME_ROWS];
			int index = 0;
			for (int i = 0; i < FRAME_ROWS; i++) {
			    for (int j = 0; j < FRAME_COLS; j++) {
			    	escalatorRegion[index++] = tmp[i][j];
			    }
			}
			
			escalatorAnimation = new Animation(0.1f, escalatorRegion);
			stateTime = 0f;
	   }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.4f, 0.2f, 0.2f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	    camera.update();
	    batch.setProjectionMatrix(camera.combined);
	    batch.begin();
	    batch.draw(letters.get(0).getTexture(), letters.get(0).getX(), letters.get(0).getY());
	    batch.end();
	    if(Gdx.input.isTouched()) {
	        Vector3 touchPos = new Vector3();
	        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	        camera.unproject(touchPos);
	        letters.get(0).setX(touchPos.x - 64/2);
	        letters.get(0).setY(touchPos.y - 64/2);
	     }

	    if(letters.get(0).getX() < 0) letters.get(0).setX(0);
	    if(letters.get(0).getX() > 800 - 64) letters.get(0).setX(800 - 64);
	    
	    newBag();
	    newLetters();
	    
	    Iterator<Bag> iter = bags.iterator();
	    while(iter.hasNext()) {
	        Bag bag = iter.next();
		    if(letters.get(0).getRectangle().overlaps(bag.getRectangle())) {
	            //dropSound.play();
	            int counter = bag.getSize();
	            bag.setSize(counter++);
	            bag.upgrade();
	            letters.get(0).getTexture().dispose();
	         }
	    }
        
	    batch.begin();
        if(escalatorMoveTime < 500){
    	    stateTime += Gdx.graphics.getDeltaTime();       
            currentFrame = escalatorAnimation.getKeyFrame(stateTime, true);
        	escalatorMoveTime += Gdx.graphics.getDeltaTime() * 500;
        }
	    batch.draw(currentFrame, 0, 280);
	    batch.draw(letters.get(0).getTexture(), letters.get(0).getX(), letters.get(0).getY());
	    for(Bag bag: bags) {
	       batch.draw(bag.getTexture(), bag.getX(), bag.getRectangle().y);
	    } 
	    batch.end();
	}
	
	private Rectangle randomRectangle(){
		  Rectangle rectangle = new Rectangle(randInt(100, 600), randInt(100, 400), 64,64);
		  return rectangle;
	}
	
	private Texture randomTexture(){
		  Texture texture = new Texture(Gdx.files.internal("bucket.png"));
		  return texture;
	}
	
	private void newBag(){
	    if(TimeUtils.millis() - lastBagTime > 15000){
	    	spawnBag();
	    	escalatorMoveTime = 0;
	    	for(int i = 0; i<bags.size(); i++){
	    		bags.get(i).setPosition(i);
	    	}
	    }
	    moveBags();
	}
	
	private void newLetters(){
	    if(TimeUtils.millis() - lastLetterTime > 5000){
	    	spawnLetters();
	    }
	}
	
	private void moveBags(){
	    for(int i = 0; i<bags.size(); i++){
			if(bags.get(i).getPosition() != 6){
				if(bags.get(i).getX() <= bagValues[bags.get(i).getPosition()]){
					float bagX = bags.get(i).getX();
					float x = bagX += 100 * Gdx.graphics.getDeltaTime();
					bags.get(bags.get(i).getPosition()).setX(x);
				}
			}	else {	
				bags.remove(i);
			}
		}
	} 
	
	public int randInt(int min, int max) {
		Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	private void spawnLetters() {
	      Letter letter = new Letter(randomRectangle(), randomTexture());
	      letters.add(letter);
	      lastLetterTime = TimeUtils.millis();
	}
	
	private void spawnBag() {
	      Bag bag = new Bag(new Rectangle(10,350,64,64), 0, new Texture(Gdx.files.internal("droplet.png")));
	      bags.add(0,bag);
	      lastBagTime = TimeUtils.millis();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		letters.get(0).getTexture().dispose();
	    dropSound.dispose();
	    rainMusic.dispose();
	    batch.dispose();
	}
}
