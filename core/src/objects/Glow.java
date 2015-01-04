package objects;

import java.util.ArrayList;

import actors.BagActor;
import actors.LetterActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import draganddrop.BagTarget;
import draganddrop.LetterSource;
import user.User;

public class Glow implements Screen {
	private ArrayList<LetterActor> letters;
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
	
/*	private Texture BagDestroy;
	private TextureRegion[] bagDestroyRegion;
	private Animation bagDestroyAnimation;
	private TextureRegion bagDestroyFrame;
	private float bagDestroyMoveTime;
	private float bagDestroyStateTime; */
	private boolean destroyBag = true; 
	
	private Texture bagPickUpTexture;
	private TextureRegion[] bagPickUpRegion;
	private Animation bagPickUpAnimation;
	private TextureRegion bagPickUpFrame;
	private float bagPickUpMoveTime;
	private float bagPickUpStateTime;
	private boolean bagPickUp = false;
	private boolean destroyBag2 = true;
	
	private BitmapFont levelFont;
	private BitmapFont scoreFont;
	private float startTime = 0;
	private Actor splashActor;
	private ArrayList<BagTarget> targets;
	private int pickUpIndex;
	
	private float bagSize;
	private boolean pause;
	private ImageButton pauseButton;
	private long pauseTime = 0;

	private PostGame game;
	private InputMultiplexer inputMultiplexer;
	int counter;
	private boolean pickUp;
	private boolean removeBag;
	private StretchViewport viewport;
	private int spawnRate;
	
	public Glow(PostGame game){
		this.game = game;
		user = new User(); 
		
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.hideStatusBar = true;
		
		levelFont = new BitmapFont(Gdx.files.internal("fonts/showFont.fnt"), Gdx.files.internal("fonts/showFont_0.png"), false);
		levelFont.setColor(0.02f,1f,0.325f,1f);
		levelFont.setScale(0.3f);
		scoreFont = new BitmapFont(Gdx.files.internal("fonts/showFont.fnt"), Gdx.files.internal("fonts/showFont_0.png"), false);
		scoreFont.setColor(0.02f,1f,0.325f,1f);
		scoreFont.setScale(0.6f);
		
		letters = new ArrayList<LetterActor>(); 
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenWidth, screenHeight);
		viewport = new StretchViewport(screenWidth, screenHeight, camera);
		
		pauseTime = TimeUtils.millis();
		theTime = pauseTime;
		spawnRate = 8000;
		
		pauseButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("Play_Pause/BPause.png")))));
		pauseButton.setScale(0.5f);
		pauseButton.setPosition(screenWidth-pauseButton.getWidth(), screenHeight-pauseButton.getHeight());
		
		batch = new SpriteBatch();
		 
		backgroundTexture = new Texture(Gdx.files.internal("background/background8.png"));
	    TextureRegion region = new TextureRegion(backgroundTexture);
	    backgroundSprite = new Sprite(region);
	    backgroundSprite.setSize(screenWidth, screenHeight);

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
		
/*		//Escalator back animation
		BagDestroy = new Texture(Gdx.files.internal("bagDestroy/bagDestroy004.png"));

		TextureRegion[][] bd = TextureRegion.split(BagDestroy, BagDestroy.getWidth() / FRAME_COLS_BAG_SPAWN, BagDestroy.getHeight() / FRAME_ROWS_BAG_SPAWN);
		bagDestroyRegion = new TextureRegion[FRAME_COLS_BAG_SPAWN * FRAME_ROWS_BAG_SPAWN];
		int count = 0;
		for (int i = 0; i < FRAME_ROWS_BAG_SPAWN; i++) {
			for (int j = 0; j < FRAME_COLS_BAG_SPAWN; j++) {
				bagDestroyRegion[count++] = bd[i][j];
			}
		}

		bagDestroyAnimation = new Animation(0.1f, bagDestroyRegion); */
		
		stage = new Stage(new StretchViewport(screenWidth, screenHeight));
		
		inputMultiplexer = new InputMultiplexer();
		
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
		viewport.update(width, height);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		camera.update();

		deltaTime = delta;
		
		if((bagPickUpAnimation.getKeyFrameIndex(bagPickUpStateTime) == 38 || bagPickUpAnimation.getKeyFrameIndex(bagPickUpStateTime) == 0)){
			theTime += 18;
			pickUp = false;
		} else {
			pickUp = true;
		}
		
		if(pause){
			pauseTime = theTime;
			game.setScreen(game.getPauseScreen());
		}
		
		startTime += delta;
		if(startTime > 6f){
			stage.getRoot().removeActor(splashActor);
		}
		
		if (bagSpawnMoveTime < bagSpawnAnimation.getAnimationDuration()) {
			// Speed of bag spawn
			if(!pause && !pickUp){
				bagSpawnStateTime += delta*2;
				bagSpawnMoveTime += delta;
			}
			
			bagSpawnFrame = bagSpawnAnimation.getKeyFrame(bagSpawnStateTime, false);
		}
		
		/*if (bagDestroyMoveTime < bagDestroyAnimation.getAnimationDuration()) {
			// Speed of bag destroy
			if(!pause && !pickUp){
				bagDestroyStateTime += delta*2;
				bagDestroyMoveTime += delta;
			}
			bagDestroyFrame = bagDestroyAnimation.getKeyFrame(bagDestroyStateTime, false);
		} */
		
		if (escalatorMoveTime < escalatorAnimation.getAnimationDuration()/1.8) {
			if(bags.size() > 1){
				// Speed of escalator
				if(!pause && !pickUp){
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
			double bagArea = screenWidth-bagSpawnFrame.getRegionWidth()/3-bagSpawnFrame.getRegionWidth()/3;
			double ratio = bagArea/7;
			bagValues = new double[] {ratio+start, ratio*2+start, ratio*3+start, ratio*4+start, ratio*5+start, ratio*6+start, ratio*7+start};
		}
		
		batch.begin();
		backgroundSprite.draw(batch);
		// Draw escalator
		Sprite escalatorSpriteFrame = new Sprite(escalatorFrame);
		escalatorSpriteFrame.setSize(screenWidth, screenHeight/8);
		escalatorSpriteFrame.setPosition(0, screenHeight/1.90f);
		escalatorSpriteFrame.draw(batch);
		batch.end();
		
		stage.act(delta);
		stage.draw();
		
		for(int i = 0; i<bags.size(); i++){
			if(bags.get(i).getBag().isFull()){
				dragAndDrop.removeTarget(targets.get(i));
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
		levelFont.draw(batch, "Level "+user.getLevel(), screenWidth/2f, screenHeight/1.25f);
		float x = scoreFont.getBounds(user.getPoints()).width/2;
		scoreFont.draw(batch, user.getPoints(), screenWidth/2.1f-x, screenHeight/1.1f);
		
		Sprite bagSpriteSpawnFrame = new Sprite(bagSpawnFrame);
		bagSpriteSpawnFrame.setSize(screenWidth/4.85f, screenHeight/2.38f);
		bagSpriteSpawnFrame.setPosition(0, screenHeight-bagSpriteSpawnFrame.getHeight());
		bagSpriteSpawnFrame.draw(batch);
		//batch.draw(bagDestroyFrame, screenWidth-bagDestroyFrame.getRegionWidth(), screenHeight-bagDestroyFrame.getRegionHeight());
		if(pickUpIndex != 0){
			Sprite bagSpritePickUpFrame = new Sprite(bagPickUpFrame);
			bagSpritePickUpFrame.setSize(screenWidth/5.51f, screenHeight/2.487f);
			bagSpritePickUpFrame.setPosition((float) bagValues[pickUpIndex-1]+(bagSize/2)-(float)(bagSpritePickUpFrame.getWidth()/2), screenHeight-bagSpritePickUpFrame.getHeight());
			bagSpritePickUpFrame.draw(batch);
		}
		batch.end(); 
		
		if(lastBagTime == 0){
			lastBagTime = theTime;
		}

		newBag();
		newLetters();
		
		if (bagPickUpMoveTime < bagPickUpAnimation.getAnimationDuration()) {
			// Speed of bag spawn
			if(!pause && escalatorMoveTime > escalatorAnimation.getAnimationDuration()/1.8){
				bagPickUpStateTime += delta; 
				bagPickUpMoveTime += delta;
			}
			
			bagPickUpFrame = bagPickUpAnimation.getKeyFrame(bagPickUpStateTime, false);
		}
	}

	private void newBag() {
		if (theTime - lastBagTime > spawnRate) {
			spawnBag();
			escalatorMoved = true;
			spawnBag = true;
			lastBagTime = theTime;
			escalatorMoveTime = 0f;
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
		if(!pause && !pickUp){
			moveBags();
		}
	}

	private void newLetters() {
		if (theTime - lastLetterTime > spawnRate/3) {
			spawnLetters();
		}
	}

	private void moveBags() {
		if(bags.size() == 7){
			removeBag = true;
		}
		if(removeBag){
			dragAndDrop.removeTarget(targets.get(6));
			if(destroyBag && escalatorMoveTime >= escalatorAnimation.getAnimationDuration()/1.8){
			/*	bagDestroyStateTime = 0f;
				bagDestroyMoveTime = 0; */
				destroyBag = false;
				destroyBag2 = false;
			}
		}
		destroyBag();
		for (int i = 1; i < bags.size(); i++) {
			if (bags.size() < 8) {
				if (bags.get(i).getX() <= bagValues[i-1]) {
					float bagX = bags.get(i).getX();
					float x = bagX += screenWidth / BAG_SPEED * deltaTime;
					bags.get(i).setX(x);
				}
			}
		}
	}
	
	private void destroyBag(){
		// bagDestroyAnimation.getKeyFrameIndex(bagDestroyStateTime) == 25 &&
		if(destroyBag2 == false){
			bags.get(bags.size()-1).remove();
			bags.remove(bags.size()-1);
			destroyBag = true;
			destroyBag2 = true;
			removeBag = false;
		}
	}

	private void spawnLetters() {
		Letter letter = new Letter(bags,letters);
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
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(initPause());
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
}
