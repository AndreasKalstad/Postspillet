package com.glow.sortit.main;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.glow.sortit.actors.BagActor;
import com.glow.sortit.actors.LetterActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import com.glow.sortit.animations.BagSpawn;
import com.glow.sortit.animations.Escalator;
import com.glow.sortit.draganddrop.BagTarget;
import com.glow.sortit.draganddrop.LetterSource;
import com.glow.sortit.objects.Bag;
import com.glow.sortit.animations.BagPickUp;
import com.glow.sortit.objects.CountryEvaluator;
import com.glow.sortit.objects.Letter;
import com.glow.sortit.tween.BitmapFontAccessor;
import com.glow.sortit.user.User;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class Glow implements Screen {
    private Actor backgroundActor;
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

    private Escalator escalatorAnimation;
    private float escalatorStateTime = 0f;
    private float escalatorMoveTime = 0f;

    private long theTime;
    private float deltaTime;

    private User user;

    private BagSpawn bagSpawnAnimation;
    private float bagSpawnStateTime = 0f;
    private TextureRegion bagSpawnFrame;
    private float bagSpawnMoveTime = 0f;
    private static final float BAG_SPEED = 10f;

    private static final int FRAME_COLS_BAG_PICKUP = 13;
    private static final int FRAME_ROWS_BAG_PICKUP = 3;
    public float screenWidth;
    public float screenHeight;
    private boolean destroyBag = true;

    private Texture bagPickUpTexture;
    private TextureRegion[] bagPickUpRegion;
    private boolean destroyBag2 = true;

    private BitmapFont levelFont;
    private BitmapFont scoreFont;
    private float startTime = 0;
    private Actor splashActor;
    private ArrayList<BagTarget> targets;

    private boolean pause;
    private ImageButton pauseButton;
    private long pauseTime = 0;

    private PostGame game;
    private InputMultiplexer inputMultiplexer;
    private boolean pickUp;
    private boolean removeBag;
    private StretchViewport viewport;
    private int spawnRate;

    private Texture Fire;
    private int FRAME_COLS_FIRE = 7;
    private int FRAME_ROWS_FIRE = 8;
    private TextureRegion[] fireRegion;
    private double fireMoveTime = 6f;
    private Animation fireAnimation;
    private float fireStateTime = 0f;
    private TextureRegion fireFrame;
    private Texture escalatorEdgeTexture;
    private Sprite escalatorEdgeSprite;

    private static TweenManager tweenManager;
    private BagPickUp[] bgu;
    private boolean[] bagPickUpIndex;
    private boolean remove[];
    private boolean[] bagPickUpIndex2;
    private boolean removed = true;
    private boolean pickUpQueue;
    private Animation bagPickUpAnimation;

    private float bagSize;

    public Glow(PostGame game){
        this.game = game;
        user = new User();
        CountryEvaluator ce = new CountryEvaluator();
        ce.restart();

        levelFont = new BitmapFont(Gdx.files.internal("fonts/showFont.fnt"), Gdx.files.internal("fonts/showFont_0.png"), false);
        levelFont.setColor(0.02f,1f,0.325f,1f);
        levelFont.setScale(0.3f);
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/showFont.fnt"), Gdx.files.internal("fonts/showFont_0.png"), false);
        scoreFont.setColor(0.02f,1f,0.325f,1f);
        scoreFont.setScale(0.6f);

        letters = new ArrayList<LetterActor>();
        bgu = new BagPickUp[7];
        remove = new boolean[6];
        bagPickUpIndex = new boolean[6];
        bagPickUpIndex2 = new boolean[6];

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        viewport = new StretchViewport(screenWidth, screenHeight, camera);

        tweenManager = new TweenManager();
        Tween.registerAccessor(BitmapFont.class, new BitmapFontAccessor());

        pauseTime = TimeUtils.millis();
        theTime = pauseTime;
        spawnRate = 8000;

        stage = new Stage(new StretchViewport(screenWidth, screenHeight));

        pauseButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("Play_Pause/BPause.png")))));
        pauseButton.setScale(0.5f);
        pauseButton.setPosition(screenWidth-pauseButton.getWidth(), screenHeight-pauseButton.getHeight());

        batch = new SpriteBatch();

        escalatorEdgeTexture = new Texture(Gdx.files.internal("escalator/greyEdge.png"));
        TextureRegion escalatorEdgeRegion = new TextureRegion(escalatorEdgeTexture);
        escalatorEdgeSprite = new Sprite(escalatorEdgeRegion);

        bags = new ArrayList<BagActor>(6);
        targets = new ArrayList<BagTarget>(6);

        //Bag pickup
        /*bagPickUpTexture = new Texture(Gdx.files.internal("bagPickup/bagPickup002.png"));

        TextureRegion[][] bpr = TextureRegion.split(bagPickUpTexture, bagPickUpTexture.getWidth() / FRAME_COLS_BAG_PICKUP, bagPickUpTexture.getHeight() / FRAME_ROWS_BAG_PICKUP);
        bagPickUpRegion = new TextureRegion[FRAME_COLS_BAG_PICKUP * FRAME_ROWS_BAG_PICKUP];
        int entry = 0;
        for (int i = 0; i < FRAME_ROWS_BAG_PICKUP; i++) {
            for (int j = 0; j < FRAME_COLS_BAG_PICKUP; j++) {
                bagPickUpRegion[entry++] = bpr[i][j];
            }
        } */

        escalatorAnimation = new Escalator(screenWidth, screenHeight);

        Fire = new Texture(Gdx.files.internal("fire/fire.png"));

        TextureRegion[][] fire = TextureRegion.split(Fire, Fire.getWidth() / FRAME_COLS_FIRE, Fire.getHeight() / FRAME_ROWS_FIRE);
        fireRegion = new TextureRegion[FRAME_COLS_FIRE * FRAME_ROWS_FIRE];
        int fr = 0;
        for (int i = 0; i < FRAME_ROWS_FIRE; i++) {
            for (int j = 0; j < FRAME_COLS_FIRE; j++) {
                fireRegion[fr++] = fire[i][j];
            }
        }

        fireAnimation = new Animation(0.1f, fireRegion);

        fireFrame = fireAnimation.getKeyFrame(fireStateTime, true);

        //bagSpawnAnimation = new BagSpawn(screenWidth, screenHeight);

        inputMultiplexer = new InputMultiplexer();

        dragAndDrop = new DragAndDrop();

        splashActor = new Actor();
        Image splashSprite = new Image(new Texture(Gdx.files.internal("NewG/GetRdy.png")));
        splashActor = splashSprite;
        splashActor.setPosition(300, 300);
        splashActor.setScale(0.5f);

        Image backgroundImage = new Image(new Texture(Gdx.files.internal("background/background8.png")));
        backgroundImage.setSize(screenWidth, screenHeight);
        backgroundActor = backgroundImage;

        stage.addActor(splashActor);
        stage.addActor(pauseButton);
        stage.addActor(backgroundActor);
        stage.addActor(escalatorAnimation);
        //stage.addActor(bagSpawnAnimation);
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
        screenWidth = width;
        screenHeight = height;
        screenHeight = height;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        camera.update();

        deltaTime = delta;

        tweenManager.update(delta);

        if(pickUp != true){
            theTime += 18;
        }

        if(pause){
            pauseTime = theTime;
            game.setScreen(game.getPauseScreen());
        }

        startTime += delta;
        if(startTime > 6f){
            stage.getRoot().removeActor(splashActor);
        }

/*        if (bagSpawnMoveTime < bagSpawnAnimation.getAnimation().getAnimationDuration()) {
            // Speed of bag spawn
            if(!pause){
                bagSpawnStateTime += delta;
                bagSpawnMoveTime += delta;
                bagSpawnAnimation.setBagSpawnStateTime(bagSpawnStateTime);
            }

            bagSpawnFrame = bagSpawnAnimation.getAnimation().getKeyFrame(bagSpawnStateTime, false);
        } */

        //Check if bag pick up is queued
        if (escalatorMoveTime >= escalatorAnimation.getAnimation().getAnimationDuration()/1.8 && pickUp == true) {
            pickUpQueue = true;
        } else {
            pickUpQueue = false;
        }

        if (escalatorMoveTime < escalatorAnimation.getAnimation().getAnimationDuration()/1.8) {
            if(bags.size() > 0){
                if(!pause && !pickUpQueue){
                    // Speed of escalator
                    escalatorStateTime += delta*4.4;
                    escalatorAnimation.setEscalatorStateTime(escalatorStateTime);
                    // Duration of escalator movement
                    escalatorMoveTime += delta*1.8;
                }
            }
        }

        if (fireMoveTime < fireAnimation.getAnimationDuration()) {
            // Speed of fire
            if(!pause && !pickUp){
                fireStateTime += delta*2;
            }
            if(!pause){
                // Duration of fire
                fireMoveTime += delta*2;
            }
            fireFrame = fireAnimation.getKeyFrame(fireStateTime, true);
        }

        // Set scaled positions for the bags
        if(bagValues == null){
            bagSize = (screenWidth/9.5f)/2;
            double ratio = screenWidth/8;
            bagValues = new double[] {ratio-bagSize, ratio*2-bagSize, ratio*3-bagSize, ratio*4-bagSize, ratio*5-bagSize, ratio*6-bagSize, ratio*7-bagSize};
        }

        newBag();
        newLetters();

        stage.act(delta);
        stage.draw();

        batch.begin();
        Sprite fireSpriteFrame = new Sprite(fireFrame);
        fireSpriteFrame.setSize(screenWidth/5, screenHeight/4);
        fireSpriteFrame.setPosition(screenWidth-fireSpriteFrame.getWidth(), screenHeight/1.69f);
        fireSpriteFrame.draw(batch);
        escalatorEdgeSprite.draw(batch);
        escalatorEdgeSprite.setSize(screenWidth/5, screenHeight/5);
        escalatorEdgeSprite.setPosition(screenWidth-escalatorEdgeSprite.getWidth(), screenHeight/2.42f);
        batch.end();

        for(int i = 0; i<bags.size(); i++){
            if(bags.get(i).getBag().isFull()){
                pickUp = true;
                if(bgu[i] == null && removed){
                    BagPickUp bguObject = new BagPickUp(0, 0);
                    bgu[i] = bguObject;
                    dragAndDrop.removeTarget(targets.get(i));
                    bagPickUpIndex[i] = true;
                    bagPickUpIndex2[i] = true;
                    remove[i] = true;
                }
            }
        }

        for(int i = 0; i<bagPickUpIndex.length; i++){
            if(bagPickUpIndex[i] == true){
                if(bgu[i] != null){
                    if(remove[i]){
                        if(bgu[i].getBagPickUpAnimation().getKeyFrameIndex(bgu[i].getBagPickUpStateTime()) == 20){
                            bags.get(i).remove();
                            remove[i] = false;
                        }
                    }
                    if(bgu[i].getBagPickUpMoveTime() > bgu[i].getBagPickUpAnimation().getAnimationDuration()){
                        bgu[i] = null;
                        pickUp = false;
                        bagPickUpIndex[i] = false;
                        removed = false;
                    }
                }
            }
        }

        if(ifDone()){
            for(int i = bagPickUpIndex2.length-1; i>=0; i--){
                if(bagPickUpIndex2[i] == true){
                    bags.remove(i);
                    bagPickUpIndex2[i] = false;
                    removed = true;
                }
            }
        }

        if(user.isScoreChanged()) {
            float d = 0.2f;   // duration
            Tween.to(scoreFont, BitmapFontAccessor.SCALE, d)
                    .targetRelative(0.1f)
                    .repeatYoyo(1, 0)
                    .start(tweenManager);
            user.setScoreChanged(false);
        }

        batch.begin();
        levelFont.draw(batch, "Level "+user.getLevel(), screenWidth/2f, screenHeight/1.25f);
        float x = scoreFont.getBounds(user.getPoints()).width/2;
        scoreFont.draw(batch, user.getPoints(), screenWidth/2.1f-x, screenHeight/1.1f);
        for(int i = 0; i < bgu.length; i++){
            if(bgu[i] != null){
                Sprite bagSpritePickUpFrame = new Sprite(bgu[i].getBagPickUpFrame());
                bagSpritePickUpFrame.setSize(screenWidth / 5.51f, screenHeight / 2.487f);
                bagSpritePickUpFrame.setPosition((float) bagValues[i] + bagSize - (bagSpritePickUpFrame.getWidth() / 2), screenHeight-bagSpritePickUpFrame.getHeight()+bagSize/4);
                bagSpritePickUpFrame.draw(batch);
            }
        }
        batch.end();

        if(lastBagTime == 0){
            lastBagTime = theTime;
        }

        if(!pause && pickUpQueue){
            for(int i = 0; i < bgu.length; i++){
                if(bgu[i] != null){
                    float stateTime = bgu[i].getBagPickUpStateTime();
                    float moveTime = bgu[i].getBagPickUpMoveTime();
                    stateTime += delta;
                    moveTime += delta;
                    bgu[i].setBagPickUpStateTime(stateTime);
                    bgu[i].setBagPickUpMoveTime(moveTime);
                }
            }
        }
    }

    private boolean ifDone(){
        for(int j = 0; j<bgu.length; j++){
            if(bgu[j] != null){
                return false;
            }
        }
        return true;
    }

    private void newBag() {
        if (theTime - lastBagTime > spawnRate) {
            spawnBag();
            lastBagTime = theTime;
            escalatorMoveTime = 0f;
            bagSpawnStateTime = 0f;
            bagSpawnMoveTime = 0f;
        }

        if(!pause){
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
            if(destroyBag && escalatorMoveTime >= escalatorAnimation.getAnimation().getAnimationDuration()/1.8){
                destroyBag = false;
                destroyBag2 = false;
            }
        }
        destroyBag();
        for (int i = 0; i < bags.size(); i++) {
            if (bags.size() < 8) {
                if (bags.get(i).getX() <= bagValues[i]) {
                    float bagX = bags.get(i).getX();
                    float x = bagX += screenWidth / BAG_SPEED * deltaTime;
                    bags.get(i).setX(x);
                }
            }
        }
    }

    private void destroyBag(){
        if(destroyBag2 == false){
            fireStateTime = 0;
            fireMoveTime = 0;
            bags.get(bags.size()-1).remove();
            bags.remove(bags.size()-1);
            destroyBag = true;
            destroyBag2 = true;
            removeBag = false;
        }
    }

    private void spawnLetters() {
        Letter letter = new Letter();
        LetterActor letterActor = new LetterActor(letter,stage);
        dragAndDrop.addSource(new LetterSource(letterActor, user, dragAndDrop));
        stage.addActor(letterActor);
        letters.add(letterActor);
        lastLetterTime = theTime;
    }

    private void spawnBag() {
        Bag bag = new Bag(bags, letters);
        bagActor = new BagActor(bag,-94.42f, screenWidth, screenHeight);
        stage.addActor(bagActor);
        //bagSpawnAnimation.remove();
        //bagSpawnAnimation = new BagSpawn(screenWidth, screenHeight);
        //stage.addActor(bagSpawnAnimation);
        bags.add(0, bagActor);
        BagTarget bagTarget = new BagTarget(bagActor);
        dragAndDrop.addTarget(bagTarget);
        targets.add(0,bagTarget);
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
