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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Pause implements Screen{

	private OrthographicCamera camera;
	private Texture backgroundTexture;
	private Sprite backgroundSprite;
	private int screenWidth;
	private int screenHeight;
	private SpriteBatch batch;
	private ImageButton pauseButton;
	
	private PostGame game;
	private Stage stage;
	protected boolean pause;

	public Pause(PostGame game){
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		stage = new Stage(new StretchViewport(screenWidth, screenHeight));
		
		pauseButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("brev/brev1.png")))));
		pauseButton.setScale(0.5f);
		pauseButton.setPosition(150, 150);
		
		stage.addActor(pauseButton);
		
		backgroundTexture = new Texture(Gdx.files.internal("Play_Pause/pauseScreen.png"));
		backgroundTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    TextureRegion region = new TextureRegion(backgroundTexture, 0, 0, (int) screenWidth, (int) screenHeight);
	    backgroundSprite = new Sprite(region);
	    
	    batch = new SpriteBatch();
	    
	    InputMultiplexer inputMultiplexer = game.getMultiplexer();
		inputMultiplexer.addProcessor(stage);
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
		batch.end();
		
		stage.act(delta);
		stage.draw();
	}
	
	private InputProcessor init(){
		InputProcessor inputProcessor = new InputAdapter() {

			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		    	if(screenX >= pauseButton.getX() && screenX <= pauseButton.getX() + pauseButton.getWidth() && screenHeight-screenY >= pauseButton.getY() && screenHeight-screenY <= pauseButton.getY() + pauseButton.getHeight()){
		    		game.resume();
		    		game.setScreen(game.getGameScreen());
			    }
			    return false;
		    }
		};
		return inputProcessor;
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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
