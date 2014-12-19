package objects;

import java.util.ArrayList;
import actors.BagActor;
import actors.LetterActor;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import draganddrop.BagTarget;
import draganddrop.LetterSource;
import user.User;

public class Glow implements ApplicationListener {
	private Array<LetterActor> letters;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ArrayList<BagActor> bags;
	private long lastBagTime;
	private long lastLetterTime;
	private double[] bagValues;
	private Animation escalatorAnimation;
	private Texture escalator;
	private TextureRegion[] escalatorRegion;
	private TextureRegion escalatorFrame;
	private float escalatorStateTime = 0f;
	private static final int FRAME_COLS_ESCALATOR = 2;
	private static final int FRAME_ROWS_ESCALATOR = 20;
	private float escalatorMoveTime = 0f;
	private Stage stage;
	private DragAndDrop dragAndDrop;
	private long theTime;
	private float deltaTime;
	private BagActor bagActor;
	private User user;
	private Texture backgroundTexture;
	private Sprite backgroundSprite;
	private static final int FRAME_COLS_BAG_SPAWN = 5;
	private static final int FRAME_ROWS_BAG_SPAWN = 5;
	public float screen_width;
    public float screen_height;
	private float screenWidth;
	private float screenHeight;
	private Texture BagSpawn;
	private TextureRegion[] bagSpawnRegion;
	private Animation bagSpawnAnimation;
	private float bagSpawnStateTime = 0f;
	private TextureRegion bagSpawnFrame;
	private float bagSpawnMoveTime = 0f;
	private Texture BagDestroy;
	private TextureRegion[] bagDestroyRegion;
	private Animation bagDestroyAnimation;
	private TextureRegion bagDestroyFrame;
	private float bagDestroyMoveTime;
	private float bagDestroyStateTime;
	private boolean spawnBag;
	private boolean escalatorMoved;
	private static final float BAG_SPEED = 10f;
	private boolean destroyBag = true;

	@Override
	public void create() {
		User user = new User();
		
		letters = new Array<LetterActor>();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		
		backgroundTexture = new Texture(Gdx.files.internal("NewG/Bakgrunn4.png"));
		backgroundTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    TextureRegion region = new TextureRegion(backgroundTexture, 0, 0, (int) screenWidth, (int) screenHeight); 
	    backgroundSprite = new Sprite(region);

		bags = new ArrayList<BagActor>(6);

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
		BagSpawn = new Texture(Gdx.files.internal("NewG/bagSpawn003.png"));

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
		BagDestroy = new Texture(Gdx.files.internal("NewG/bagDestroy003.png"));

		TextureRegion[][] bd = TextureRegion.split(BagDestroy, BagDestroy.getWidth() / FRAME_COLS_BAG_SPAWN, BagDestroy.getHeight() / FRAME_ROWS_BAG_SPAWN);
		bagDestroyRegion = new TextureRegion[FRAME_COLS_BAG_SPAWN * FRAME_ROWS_BAG_SPAWN];
		int count = 0;
		for (int i = 0; i < FRAME_ROWS_BAG_SPAWN; i++) {
			for (int j = 0; j < FRAME_COLS_BAG_SPAWN; j++) {
				bagDestroyRegion[count++] = bd[i][j];
			}
		}

		bagDestroyAnimation = new Animation(0.1f, bagDestroyRegion);
		
		screen_width = Gdx.graphics.getWidth();
	    screen_height = Gdx.graphics.getHeight();
		
		stage = new Stage(new StretchViewport(screen_width, screen_height));
		Gdx.input.setInputProcessor(stage);

		dragAndDrop = new DragAndDrop();
		dragAndDrop.setDragActorPosition(-(Gdx.graphics.getWidth()/6.4f)/2, (Gdx.graphics.getHeight()/6.4f)/2);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		camera.update();
		
		deltaTime = Gdx.graphics.getDeltaTime();
		
		if (bagSpawnMoveTime < bagSpawnAnimation.getAnimationDuration()) {
			// Speed of bag spawn
			bagSpawnStateTime += deltaTime;
			
			bagSpawnFrame = bagSpawnAnimation.getKeyFrame(bagSpawnStateTime, false);
			bagSpawnMoveTime += deltaTime;
		}
		
		if (bagDestroyMoveTime < bagDestroyAnimation.getAnimationDuration()) {
			// Speed of bag destroy
			bagDestroyStateTime += deltaTime;
			bagDestroyFrame = bagDestroyAnimation.getKeyFrame(bagDestroyStateTime, false);
			bagDestroyMoveTime += deltaTime;
		}
		
		if (escalatorMoveTime < escalatorAnimation.getAnimationDuration()/1.8) {
			escalatorFrame = escalatorAnimation.getKeyFrame(escalatorStateTime, true);
			if(bags.size() > 1){
				// Speed of escalator
				escalatorStateTime += deltaTime*2.9;
			}
			// Duration of escalator movement
			escalatorMoveTime += deltaTime*1.8;
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
		batch.draw(escalatorFrame, 0, screen_height/1.83f);
		batch.end();
		
		stage.act(deltaTime);
		stage.draw();
		
		batch.begin();
		batch.draw(bagSpawnFrame, 0, screenHeight-bagSpawnFrame.getRegionHeight());
		batch.draw(bagDestroyFrame, screenWidth-bagDestroyFrame.getRegionWidth(), screenHeight-bagDestroyFrame.getRegionHeight());
		batch.end();
		
		theTime = TimeUtils.millis();
		if(lastBagTime == 0){
			lastBagTime = theTime;
		}

		newBag();
		newLetters();
		
		for(int i = 0; i<bags.size(); i++){
			if(bags.get(i).getBag().isFull()){
				bags.get(i).remove();
				bags.remove(i);
			}
		}
	}

	private void newBag() {
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
		
		if(bagSpawnAnimation.getKeyFrameIndex(bagSpawnStateTime) == 12 && spawnBag == true){
			addBagStage();
			spawnBag = false;
		}
		moveBags();
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
				if(destroyBag && escalatorMoveTime >= escalatorAnimation.getAnimationDuration()/1.8){
					bagDestroyStateTime = 0f;
					bagDestroyMoveTime = 0;
					destroyBag = false;
				}
				destroyBag();
			}
		}
	}
	
	private void destroyBag(){
		if(bagDestroyAnimation.getKeyFrameIndex(bagDestroyStateTime) == 12){
			bags.get(6).remove();
			bags.remove(6);
			destroyBag = true;
		}
	}

	private void spawnLetters() {
		Letter letter = new Letter();
		LetterActor letterActor = new LetterActor(letter,stage);
		dragAndDrop.addSource(new LetterSource(letterActor));
        stage.addActor(letterActor);
		letters.add(letterActor);
		lastLetterTime = theTime;
	}

	private void spawnBag() {
		Bag bag = new Bag();
        bagActor = new BagActor(bag, stage,bagSpawnFrame.getRegionWidth()/3);
		bags.add(0, bagActor);
	} 
	
	private void addBagStage(){
		dragAndDrop.addTarget(new BagTarget(bagActor));
        stage.addActor(bagActor);
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
		stage.dispose();
		batch.dispose();
	}
}
