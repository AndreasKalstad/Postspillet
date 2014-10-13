package objects;

import java.util.ArrayList;
import actors.BagActor;
import actors.LetterActor;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
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
	private int[] bagValues;
	private Animation escalatorAnimation;
	private Texture escalator;
	private TextureRegion[] escalatorRegion;
	private TextureRegion escalatorFrame;
	private float escalatorStateTime;
	private static final int FRAME_COLS_ESCALATOR = 1;
	private static final int FRAME_ROWS_ESCALATOR = 20;
	private float escalatorMoveTime = 0;
	private Stage stage;
	private DragAndDrop dragAndDrop;
	private long theTime;
	private float deltaTime;
	private BagActor bagActor;
	private User user;

	@Override
	public void create() {
		User user = new User();
		
		letters = new Array<LetterActor>();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		bagValues = new int[] {0, 100, 200, 300, 400, 500};
		bags = new ArrayList<BagActor>(6);

		//Escalator animation
		escalator = new Texture(Gdx.files.internal("MiniEscalator_005.png"));

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
		
		stage = new Stage();
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
		Gdx.gl.glClearColor(0.4f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		camera.update();
		
		deltaTime = Gdx.graphics.getDeltaTime();
		if (escalatorMoveTime < 500) {
			escalatorStateTime += deltaTime;
			escalatorFrame = escalatorAnimation.getKeyFrame(escalatorStateTime, true);
			escalatorMoveTime += deltaTime * 500;
		}
		batch.begin();
		batch.draw(escalatorFrame, 0, 280); 
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
		if (theTime - lastBagTime > 15000) {
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
			if (bags.size() != 6) {
				if (bags.get(i).getX() <= bagValues[i]) {
					float bagX = bags.get(i).getX();
					float x = bagX += 50 * deltaTime;
					bags.get(i).setX(x);
				}
			} else {
				bags.get(i).remove();
				bags.remove(i);
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
