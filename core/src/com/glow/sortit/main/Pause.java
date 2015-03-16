package com.glow.sortit.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.glow.sortit.main.Glow;
import com.glow.sortit.main.PostGame;

public class Pause implements Screen {

	private OrthographicCamera camera;
	private Sprite backgroundSprite;
	private float screenWidth;
	private float screenHeight;
	private SpriteBatch batch;
	private Sprite continueButton;
	private Sprite menuButton;
	private Sprite restartButton;
	private Sprite muteButton;
	
	private TextureAtlas pauseScreen;
	
	private PostGame game;
	private Viewport viewport;
	private Sprite unmuteButton;
	private Sprite toggleMute;

	public Pause(PostGame game){
		this.game = game;
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenWidth, screenHeight);
		viewport = new StretchViewport(screenWidth, screenHeight, camera);
		
		pauseScreen = new TextureAtlas("pauseScreen/pauseScreen.txt");
		
		backgroundSprite = pauseScreen.createSprite("pauseScreen_BG");
		backgroundSprite.setSize(screenWidth, screenHeight);
		
//		ContinueButton
		continueButton =new Sprite(pauseScreen.createSprite("pauseScreen_Continue"));
		continueButton.setPosition( (screenWidth/2.5f),(screenHeight/4));
		continueButton.setSize(screenWidth/5.51f, screenHeight/3.31f);
		
		menuButton = new Sprite(pauseScreen.createSprite("pauseScreen_MainMenu"));
		menuButton.setPosition( (screenWidth/5.92f),(screenHeight/4));
		menuButton.setSize(screenWidth/5.51f, screenHeight/3.31f);
		
		restartButton = new Sprite(pauseScreen.createSprite("pauseScreen_Restart"));
		restartButton.setPosition( (screenWidth/1.584f),(screenHeight/4));
		restartButton.setSize(screenWidth/5.51f, screenHeight/3.31f);
		
		muteButton = new Sprite(pauseScreen.createSprite("pauseScreen_Mute"));
		muteButton.setPosition( (screenWidth/1.24f),(screenHeight/1.352f));
		muteButton.setSize(screenWidth/16, screenHeight/9.6f);
		
		unmuteButton = new Sprite(pauseScreen.createSprite("pauseScreen_Unmute"));
		unmuteButton.setPosition( (screenWidth/1.24f),(screenHeight/1.352f));
		unmuteButton.setSize(screenWidth/16, screenHeight/9.6f);
		
		toggleMute = new Sprite();
		toggleMute = muteButton;
	    
	    batch = new SpriteBatch();
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
		toggleMute.draw(batch);
		restartButton.draw(batch);
		batch.end();
	}
	
	private InputProcessor init(){
		InputProcessor inputProcessor = new InputAdapter() {
			private boolean muted;

			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if(screenX >= continueButton.getX() && screenX <= continueButton.getX() + continueButton.getWidth() && screenHeight-screenY >= continueButton.getY() && screenHeight-screenY <= continueButton.getY() + continueButton.getHeight()){
		    		game.resume();
		    		game.setScreen(game.getGameScreen());
			    }
		    	
		    	if(screenX >= menuButton.getX() && screenX <= menuButton.getX() + menuButton.getWidth() && screenHeight-screenY >= menuButton.getY() && screenHeight-screenY <= menuButton.getY() + menuButton.getHeight()){
		    		Gdx.app.exit();
			    }
		    	
		    	if(screenX >= restartButton.getX() && screenX <= restartButton.getX() + restartButton.getWidth() && screenHeight-screenY >= restartButton.getY() && screenHeight-screenY <= restartButton.getY() + restartButton.getHeight()){
		    		Glow glow = new Glow(game);
		    		game.newGame(glow);
		    		game.setScreen(glow);
			    }

		    	if(screenX >= muteButton.getX() && screenX <= muteButton.getX() + muteButton.getWidth() && screenHeight-screenY >= muteButton.getY() && screenHeight-screenY <= muteButton.getY() + muteButton.getHeight()){
		    		if(muted){
		    			toggleMute = muteButton;
		    			muted = false;
		    		} else {
		    			toggleMute = unmuteButton;
		    			muted = true;
		    		}
		    	}
			    return false; 
		    }
		};
		return inputProcessor;
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(init());
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
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
