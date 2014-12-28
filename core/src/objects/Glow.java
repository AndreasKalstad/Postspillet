package objects;

import java.util.ArrayList;

import actors.BagActor;
import actors.LetterActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import draganddrop.BagTarget;
import draganddrop.LetterSource;
import user.User;

public class Glow implements Screen {
	private Array<LetterActor> letters;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Stage stage;
	private DragAndDrop dragAndDrop;
	
	private ArrayList<BagActor> bags;
	private double[] bagValues;
	private BagActor bagActor;
	
	private long lastBagTime;
	private long lastLetterTime;
	
	private Animation escalatorAnimation;
	private Texture escalator;
	private TextureRegion[] escalatorRegion;
	private TextureRegion escalatorFrame;
	private float escalatorStateTime = 0f;
	private static final int FRAME_COLS_ESCALATOR = 2;
	private static final int FRAME_ROWS_ESCALATOR = 20;
	private float escalatorMoveTime = 0f;
	private boolean escalatorMoved;
	
	private long theTime;
	private float deltaTime;
	
	private User user;
	
	private Texture backgroundTexture;
	private Sprite backgroundSprite;
	
	private boolean spawnBag;
	private Texture BagSpawn;
	private TextureRegion[] bagSpawnRegion;
	private Animation bagSpawnAnimation;
	private float bagSpawnStateTime = 0f;
	private TextureRegion bagSpawnFrame;
	private float bagSpawnMoveTime = 0f;
	private static final int FRAME_COLS_BAG_SPAWN = 10;
	private static final int FRAME_ROWS_BAG_SPAWN = 5;
	private static final float BAG_SPEED = 10f;
	
	private static final int FRAME_COLS_BAG_PICKUP = 13;
	private static final int FRAME_ROWS_BAG_PICKUP = 3;
	private float screenWidth;
	private float screenHeight;
	
	private Texture BagDestroy;
	private TextureRegion[] bagDestroyRegion;
	private Animation bagDestroyAnimation;
	private TextureRegion bagDestroyFrame;
	private float bagDestroyMoveTime;
	private float bagDestroyStateTime;
	private boolean destroyBag = true;
	
	private Texture bagPickUpTexture;
	private TextureRegion[] bagPickUpRegion;
	private Animation bagPickUpAnimation;
	private TextureRegion bagPickUpFrame;
	private float bagPickUpMoveTime;
	private float bagPickUpStateTime;
	private boolean bagPickUp = false;
	private boolean destroyBag2;
	
	private BitmapFont font;
	private float startTime = 0;
	private Actor splashActor;
	private ArrayList<BagTarget> targets;
	private int pickUpIndex;
	
	private float bagSize;
	private boolean pause;
	private ImageButton pauseButton;
	private float pausedelta = 0;
	private long pauseTime = 0;

	private PostGame game;
	private InputMultiplexer inputMultiplexer;
	int counter;
	
	public Glow(PostGame game){
		this.game = game;
		user = new User(); 
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.setScale(2f);
		
		letters = new Array<LetterActor>(); 

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		pauseTime = TimeUtils.millis();
		System.out.println("lol");
		theTime = pauseTime;
		
		pauseButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("brev/brev1.png")))));
		pauseButton.setScale(0.5f);
		pauseButton.setPosition(50, 50);

		batch = new SpriteBatch();
		 
		backgroundTexture = new Texture(Gdx.files.internal("NewG/Bakgrunn6.png"));
		backgroundTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    TextureRegion region = new TextureRegion(backgroundTexture, 0, 0, (int) screenWidth, (int) screenHeight);
	    backgroundSprite = new Sprite(region);

		bags = new ArrayList<BagActor>(6);
		targets = new ArrayList<BagTarget>(6);
		
		//Bag pickup
		bagPickUpTexture = new Texture(Gdx.files.internal("bagPickup/bagPickup002.png"));

		TextureRegion[][] bpr = TextureRegion.split(bagPickUpTexture, bagPickUpTexture.getWidth() / FRAME_COLS_BAG_PICKUP, bagPickUpTexture.getHeight() / FRAME_ROWS_BAG_PICKUP);
		bagPickUpRegion = new TextureRegion[FRAME_COLS_BAG_PICKUP * FRAME_ROWS_BAG_PICKUP];
		int entry = 0;
		for (int i = 0; i < FRAME_ROWS_BAG_PICKUP; i++) {
			for (int j = 0; j < FRAME_COLS_BAG_PICKUP; j++) {
				bagPickUpRegion[entry++] = bpr[i][j];
			}
		}

		bagPickUpAnimation = new Animation(0.1f, bagPickUpRegion);

		//Escalator back animation
		escalator = new Texture(Gdx.files.internal("NewG/escalator009.png"));

		TextureRegion[][] tmp = TextureRegion.split(escalator, escalator.getWidth() / FRAME_COLS_ESCALATOR, escalator.getHeight() / FRAME_ROWS_ESCALATOR);
		escalatorRegion = new TextureRegion[FRAME_COLS_ESCALATOR * FRAME_ROWS_ESCALATOR];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS_ESCALATOR; i++) {
			for (int j = 0; j < FRAME_COLS_ESCALATOR; j++) {
				escalatorRegion[index++] = tmp[i][j];
			}
		}

		escalatorAnimation = new Animation(0.1f, escalatorRegion);
		
		//Escalator back animation
		BagSpawn = new Texture(Gdx.files.internal("bagSpawn/bagSpawn004.png"));
		
		TextureRegion[][] bs = TextureRegion.split(BagSpawn, BagSpawn.getWidth() / FRAME_COLS_BAG_SPAWN, BagSpawn.getHeight() / FRAME_ROWS_BAG_SPAWN);
		bagSpawnRegion = new TextureRegion[FRAME_COLS_BAG_SPAWN * FRAME_ROWS_BAG_SPAWN];
		int in = 0;
		for (int i = 0; i < FRAME_ROWS_BAG_SPAWN; i++) {
			for (int j = 0; j < FRAME_COLS_BAG_SPAWN; j++) {
				bagSpawnRegion[in++] = bs[i][j];
			}
		}

		bagSpawnAnimation = new Animation(0.1f, bagSpawnRegion);
		
		//Escalator back animation
		BagDestroy = new Texture(Gdx.files.internal("bagDestroy/bagDestroy004.png"));

		TextureRegion[][] bd = TextureRegion.split(BagDestroy, BagDestroy.getWidth() / FRAME_COLS_BAG_SPAWN, BagDestroy.getHeight() / FRAME_ROWS_BAG_SPAWN);
		bagDestroyRegion = new TextureRegion[FRAME_COLS_BAG_SPAWN * FRAME_ROWS_BAG_SPAWN];
		int count = 0;
		for (int i = 0; i < FRAME_ROWS_BAG_SPAWN; i++) {
			for (int j = 0; j < FRAME_COLS_BAG_SPAWN; j++) {
				bagDestroyRegion[count++] = bd[i][j];
			}
		}

		bagDestroyAnimation = new Animation(0.1f, bagDestroyRegion);
		
		stage = new Stage(new StretchViewport(screenWidth, screenHeight));
		
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(initPause());
		Gdx.input.setInputProcessor(inputMultiplexer);
		game.setMultiplexer(inputMultiplexer);
		
		Bag exampleBag = new Bag();
		BagActor bagActorExample = new BagActor(exampleBag, stage,0);
		bagSize = bagActorExample.getBag().getTextureRegion().getRegionWidth();

		dragAndDrop = new DragAndDrop();
		dragAndDrop.setDragActorPosition(-(Gdx.graphics.getWidth()/6.4f)/2, (Gdx.graphics.getHeight()/6.4f)/2);
		
		splashActor = new Actor();
		Image splashSprite = new Image(new Texture(Gdx.files.internal("NewG/GetRdy.png")));
		splashActor = splashSprite;
		splashActor.setPosition(300, 300);
		splashActor.setScale(0.5f);
		stage.addActor(splashActor);
		
		stage.addActor(pauseButton);
	}
	
	private InputProcessor initPause(){
		InputProcessor inputProcessor = new InputAdapter() {
		    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		    	if(screenX >= pauseButton.getX() && screenX <= pauseButton.getX() + pauseButton.getWidth() && screenHeight-screenY >= pauseButton.getY() && screenHeight-screenY <= pauseButton.getY() + pauseButton.getHeight()){
			        if(pause == false) { 
			            pause();
			            return true;
			        }
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
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		camera.update();
		
		deltaTime = delta;
		theTime += 18;
		
		if(pause){
			pauseTime = theTime;
			game.setScreen(game.getPauseScreen());
		}
		
		startTime += delta;
		if(startTime > 6f){
			stage.getRoot().removeActor(splashActor);
		}
		
		if (bagPickUpMoveTime < bagPickUpAnimation.getAnimationDuration()) {
			// Speed of bag spawn
			if(!pause){
				bagPickUpStateTime += delta; 
				bagPickUpMoveTime += delta;
			}
			
			bagPickUpFrame = bagPickUpAnimation.getKeyFrame(bagPickUpStateTime, false);
		}
		
		if (bagSpawnMoveTime < bagSpawnAnimation.getAnimationDuration()) {
			// Speed of bag spawn
			if(!pause){
				bagSpawnStateTime += delta*2;
				bagSpawnMoveTime += delta;
			}
			
			bagSpawnFrame = bagSpawnAnimation.getKeyFrame(bagSpawnStateTime, false);
		}
		
		if (bagDestroyMoveTime < bagDestroyAnimation.getAnimationDuration()) {
			// Speed of bag destroy
			if(!pause){
				bagDestroyStateTime += delta*2;
				bagDestroyMoveTime += delta;
			}
			bagDestroyFrame = bagDestroyAnimation.getKeyFrame(bagDestroyStateTime, false);
		}
		
		if (escalatorMoveTime < escalatorAnimation.getAnimationDuration()/1.8) {
			if(bags.size() > 1){
				// Speed of escalator
				if(!pause){
					escalatorStateTime += delta*4.4;
				}
			}
			if(!pause){
				// Duration of escalator movement
				escalatorMoveTime += delta*1.8;
			}
			escalatorFrame = escalatorAnimation.getKeyFrame(escalatorStateTime, true);
		}
		
		// Set scaled positions for the bags
		if(bagValues == null){
			double start = bagSpawnFrame.getRegionWidth()/3;
			double bagArea = screenWidth-bagSpawnFrame.getRegionWidth()/3-bagDestroyFrame.getRegionWidth()/3;
			double ratio = bagArea/7;
			bagValues = new double[] {ratio+start, ratio*2+start, ratio*3+start, ratio*4+start, ratio*5+start, ratio*6+start, ratio*7+start};
		}
		
		batch.begin();
		backgroundSprite.draw(batch);
		// Draw escalator
		batch.draw(escalatorFrame, 0, screenHeight/1.90f);
		batch.end();
		
		stage.act(delta);
		stage.draw();
		
		for(int i = 0; i<bags.size(); i++){
			if(bags.get(i).getBag().isFull()){
				pickUpIndex = i;
				if(!bagPickUp){
					bagPickUpStateTime = 0;
					bagPickUpMoveTime = 0;
					bagPickUp = true;
				}
				if(bagSpawnAnimation.getKeyFrameIndex(bagPickUpStateTime) == 20){ 
					bags.get(i).remove();
					bags.remove(i);
				}
				if(bagPickUpMoveTime > bagPickUpAnimation.getAnimationDuration()){
					bagPickUp = false;
				}
			}
		}
		
		batch.begin();
		batch.draw(bagSpawnFrame, 0, screenHeight-bagSpawnFrame.getRegionHeight());
		batch.draw(bagDestroyFrame, screenWidth-bagDestroyFrame.getRegionWidth(), screenHeight-bagDestroyFrame.getRegionHeight());
		if(pickUpIndex != 0){
			batch.draw(bagPickUpFrame, (int) bagValues[pickUpIndex-1]+(bagSize/2)-(bagPickUpFrame.getRegionWidth()/2), screenHeight-bagPickUpFrame.getRegionHeight());
		}
		font.draw(batch, user.getPoints(), 400, 200);
		batch.end(); 
		
		if(lastBagTime == 0){
			lastBagTime = theTime;
		}

		newBag();
		newLetters();
	}

	private void newBag() {
		long time = theTime - lastBagTime;
		System.out.println(time);
		if (theTime - lastBagTime > 4000) {
			escalatorMoved = true;
			spawnBag = true;
			lastBagTime = theTime;
			escalatorMoveTime = 0f;
			spawnBag();
		}
		
		if (escalatorMoveTime > escalatorAnimation.getAnimationDuration()/1.8 && escalatorMoved == true) { 
			escalatorMoved = false;
			bagSpawnStateTime = 0f;
			bagSpawnMoveTime = 0f;
		}
		
		if(bagSpawnAnimation.getKeyFrameIndex(bagSpawnStateTime) == 25 && spawnBag == true){ 
			addBagStage();
			spawnBag = false;
		}
		if(!pause){
			moveBags();
		}
	}

	private void newLetters() {
		if (theTime - lastLetterTime > 3000) {
			spawnLetters();
		}
	}

	private void moveBags() {
		for (int i = 1; i < bags.size(); i++) {
			if (bags.size() < 8) {
				if (bags.get(i).getX() <= bagValues[i-1]) {
					float bagX = bags.get(i).getX();
					float x = bagX += screenWidth / BAG_SPEED * deltaTime;
					bags.get(i).setX(x);
				}
			}
			if(bags.size() == 7){
				dragAndDrop.removeTarget(targets.get(6));
				if(destroyBag && escalatorMoveTime >= escalatorAnimation.getAnimationDuration()/1.8){
					bagDestroyStateTime = 0f;
					bagDestroyMoveTime = 0;
					destroyBag = false;
					destroyBag2 = false;
				}
			}
			destroyBag();
		}
	}
	
	private void destroyBag(){
		if(bagDestroyAnimation.getKeyFrameIndex(bagDestroyStateTime) == 25 && destroyBag2 == false){
			bags.get(bags.size()-1).remove();
			bags.remove(bags.size()-1);
			destroyBag = true;
			destroyBag2 = true;
		}
	}

	private void spawnLetters() {
		Letter letter = new Letter();
		LetterActor letterActor = new LetterActor(letter,stage,pause);
		dragAndDrop.addSource(new LetterSource(letterActor, user));
        stage.addActor(letterActor);
		letters.add(letterActor);
		lastLetterTime = theTime;
	}

	private void spawnBag() {
		Bag bag = new Bag();
        bagActor = new BagActor(bag, stage,bagSpawnFrame.getRegionWidth()/3);
		bags.add(0, bagActor);
		BagTarget bagTarget = new BagTarget(bagActor);
		dragAndDrop.addTarget(bagTarget);
		targets.add(0,bagTarget);
	} 
	
	private void addBagStage(){
        stage.addActor(bagActor);
	}

	@Override
	public void pause() {
		pause = true;
	}

	@Override
	public void resume() {
		theTime = pauseTime;
		pause = false;
	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
