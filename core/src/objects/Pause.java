package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Pause implements Screen{

	private OrthographicCamera camera;
	private Texture backgroundTexture;
	private Sprite backgroundSprite;
	private float screenWidth;
	private float screenHeight;
	private SpriteBatch batch;
	private Sprite continueButton;
	private Sprite menuButton;
	private Sprite restartButton;
	private Sprite muteButton;
	
	private TextureAtlas pauseScreen = new TextureAtlas("pauseScreen/pauseScreen.txt");
	
	private PostGame game;
	protected boolean pause;

	public Pause(PostGame game){
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		backgroundSprite = pauseScreen.createSprite("pauseScreen_BG");
		
//		ContinueButton
		continueButton =new Sprite(pauseScreen.createSprite("pauseScreen_Continue"));
		continueButton.setPosition( (screenWidth/2.5f),(screenHeight/4));
		
		menuButton = new Sprite(pauseScreen.createSprite("pauseScreen_MainMenu"));
		menuButton.setPosition( (screenWidth/5.17f),(screenHeight/4));
		
		
		restartButton = new Sprite(pauseScreen.createSprite("pauseScreen_Restart"));
		restartButton.setPosition( (screenWidth/1.584f),(screenHeight/4));
		
		muteButton = new Sprite(pauseScreen.createSprite("pauseScreen_Mute"));
		muteButton.setPosition( (screenWidth/1.24f),(screenHeight/1.352f));
		
		//Buttons
	    
	    batch = new SpriteBatch();
	    
	    InputMultiplexer inputMultiplexer = game.getMultiplexer();
		inputMultiplexer.addProcessor(init());
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		camera.update();
		
		batch.begin();
		backgroundSprite.draw(batch);
		continueButton.draw(batch);
		menuButton.draw(batch);
		muteButton.draw(batch);
		restartButton.draw(batch);
		batch.end();

	}
	
	private InputProcessor init(){
		InputProcessor inputProcessor = new InputAdapter() {

			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				
		    	if(screenX >= continueButton.getX() && screenX <= continueButton.getX() + continueButton.getWidth() && screenHeight-screenY >= continueButton.getY() && screenHeight-screenY <= continueButton.getY() + continueButton.getHeight()){
		    		game.resume();
		    		game.setScreen(game.getGameScreen());
			    }
		    	
		    	if(screenX >= menuButton.getX() && screenX <= menuButton.getX() + menuButton.getWidth() && screenHeight-screenY >= menuButton.getY() && screenHeight-screenY <= menuButton.getY() + menuButton.getHeight()){
		    		System.out.println("Meny");
			    }
		    	
		    	if(screenX >= restartButton.getX() && screenX <= restartButton.getX() + restartButton.getWidth() && screenHeight-screenY >= restartButton.getY() && screenHeight-screenY <= restartButton.getY() + restartButton.getHeight()){
		    		System.out.println("Restart");
			    }

		    	if(screenX >= muteButton.getX() && screenX <= muteButton.getX() + muteButton.getWidth() && screenHeight-screenY >= muteButton.getY() && screenHeight-screenY <= muteButton.getY() + muteButton.getHeight()){
		    		System.out.println("Mute"); 
		    	}
			    return false; 
		    }
		};
		return inputProcessor;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
	
}
