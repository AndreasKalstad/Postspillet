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
	private float escalatorStateTime;
	private static final int FRAME_COLS_ESCALATOR = 2;
	private static final int FRAME_ROWS_ESCALATOR = 10;
	private float escalatorMoveTime = 0;
	private Stage stage;
	private DragAndDrop dragAndDrop;
	private long theTime;
	private float deltaTime;
	private BagActor bagActor;
	private User user;
	private Texture backgroundTexture;
	private Sprite backgroundSprite;
	private Animation bagAnimation;
	private Texture bagTexture;
	private TextureRegion[] bagRegion;
	private TextureRegion bagFrame;
	private float bagStateTime;
	private static final int FRAME_COLS_BAG = 5;
	private static final int FRAME_ROWS_BAG = 6;
	public float screen_width;
    public float screen_height;
	private Texture bagDisposal;
	private TextureRegion[] escalatorEndRegion;
	private Texture escalatorEnd;
	private Animation escalatorEndAnimation;
	private TextureRegion escalatorEndFrame;

	@Override
	public void create() {
		User user = new User();
		
		letters = new Array<LetterActor>();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();
		
		backgroundTexture = new Texture(Gdx.files.internal("Bakgrunn2.png"));
		backgroundTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    TextureRegion region = new TextureRegion(backgroundTexture, 0, 0, 800, 480);
	    backgroundSprite = new Sprite(region);
	    
	    bagDisposal = new Texture(Gdx.files.internal("Bag/BagDisposal002_NoCrop.png"));

		bags = new ArrayList<BagActor>(6);

		//Escalator animation
		escalator = new Texture(Gdx.files.internal("Escalator/Escalator_Back001.png"));

		TextureRegion[][] tmp = TextureRegion.split(escalator, escalator.getWidth() / FRAME_COLS_ESCALATOR, escalator.getHeight() / FRAME_ROWS_ESCALATOR);
		escalatorRegion = new TextureRegion[FRAME_COLS_ESCALATOR * FRAME_ROWS_ESCALATOR];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS_ESCALATOR; i++) {
			for (int j = 0; j < FRAME_COLS_ESCALATOR; j++) {
				escalatorRegion[index++] = tmp[i][j];
			}
		}

		escalatorAnimation = new Animation(0.1f, escalatorRegion);
		escalatorStateTime = 0f;
		
		// Escalator fix
		escalatorEnd = new Texture(Gdx.files.internal("Escalator/Escalator_Front001.png"));

		TextureRegion[][] tme = TextureRegion.split(escalatorEnd, escalatorEnd.getWidth() / FRAME_COLS_ESCALATOR, escalatorEnd.getHeight() / FRAME_ROWS_ESCALATOR);
		escalatorEndRegion = new TextureRegion[FRAME_COLS_ESCALATOR * FRAME_ROWS_ESCALATOR];
		int in = 0;
		for (int i = 0; i < FRAME_ROWS_ESCALATOR; i++) {
			for (int j = 0; j < FRAME_COLS_ESCALATOR; j++) {
				escalatorEndRegion[in++] = tme[i][j];
			}
		}

		escalatorEndAnimation = new Animation(0.1f, escalatorEndRegion);
		
		//Bagdrop animation
		bagTexture = new Texture(Gdx.files.internal("Bag/BagDrop005.png"));

		TextureRegion[][] trb = TextureRegion.split(bagTexture, bagTexture.getWidth() / FRAME_COLS_BAG, bagTexture.getHeight() / FRAME_ROWS_BAG);
		bagRegion = new TextureRegion[FRAME_COLS_BAG * FRAME_ROWS_BAG];
		int counter = 0;
		for (int i = 0; i < FRAME_ROWS_BAG; i++) {
			for (int j = 0; j < FRAME_COLS_BAG; j++) {
				bagRegion[counter++] = trb[i][j];
			}
		}

		bagAnimation = new Animation(0.1f, bagRegion);
		bagStateTime = 0f;
		bagAnimation.setPlayMode(Animation.PlayMode.NORMAL);
		
		screen_width = Gdx.graphics.getWidth();
	    screen_height = Gdx.graphics.getHeight();
		
		stage = new Stage(new StretchViewport(screen_width, screen_height));
		Gdx.input.setInputProcessor(stage);

		dragAndDrop = new DragAndDrop();
		dragAndDrop.setDragActorPosition(-(25), 25);
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
		if (escalatorMoveTime < escalatorAnimation.getAnimationDuration()/1.5) {
			// Speed of escalator
			escalatorStateTime += deltaTime*1.2;
			escalatorFrame = escalatorAnimation.getKeyFrame(escalatorStateTime, true);
			escalatorEndFrame = escalatorEndAnimation.getKeyFrame(escalatorStateTime, true);
			// Duration of escalator movement
			escalatorMoveTime += deltaTime;
		}
		
		// Set scaled positions for the bags
		if(bagValues == null){
			double ratio = (550/6);
			double start = 78;
			bagValues = new double[] {ratio-start, ratio*2-start, ratio*3-start, ratio*4-start, ratio*5-start, ratio*6-start}; 
		}
		
		batch.begin();
		backgroundSprite.draw(batch);
		// Draw escalator
		batch.draw(escalatorFrame, -2, 280);
		// Draw bag animation
		if(bagStateTime < bagAnimation.getAnimationDuration()){
			bagFrame = bagAnimation.getKeyFrame(bagStateTime, true);
			// Bag drop animation speed
			bagStateTime += deltaTime;
			batch.draw(bagFrame, escalatorFrame.getRegionWidth()-(bagFrame.getRegionWidth()/1.41f), 168);
		}
		batch.draw(bagDisposal, 0, 0);
		batch.draw(escalatorEndFrame, -2, 280);
		batch.end();
		
		stage.act(deltaTime);
		stage.draw();
		
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
		if (theTime - lastBagTime > 6000) {
			spawnBag();
			escalatorMoveTime = 0;
		}
		moveBags();
	} 

	private void newLetters() {
		if (theTime - lastLetterTime > 5000) {
			spawnLetters();
		}
	}

	private void moveBags() {
		for (int i = 0; i < bags.size(); i++) {
			if (bags.size() < 7) {
				if (bags.get(i).getX() <= bagValues[i]) {
					float bagX = bags.get(i).getX();
					float x = bagX += 70 * deltaTime;
					bags.get(i).setX(x);
				}
			} else {
				bags.get(6).remove();
				bags.remove(6);
				bagStateTime = 0;
			}
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
        bagActor = new BagActor(bag, stage);
		dragAndDrop.addTarget(new BagTarget(bagActor));
        stage.addActor(bagActor);
		bags.add(0, bagActor);
		lastBagTime = theTime;
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
