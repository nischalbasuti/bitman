package com.nischalbasuti1.bitman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;
import java.util.Random;

/*
 * Created by nischal on 7/15/2016.
 */
public class GameScreen implements Screen{
    BitmanGame game;
    SpriteBatch batch;

    boolean paused = false;
    boolean gameOver = false;
    

    int difficulty = 1;

    int score = 0;
    int highScore;

    Texture backgroundTexture;
    Sprite backgroundSprite;

    Texture platformTexture;
    Platform platform;

    Texture buildingTexture;
    Sprite buildingSprite1;
    Sprite buildingSprite2;
    Sprite buildingSprite3;
    Sprite buildingSprite4;
    Sprite buildingSprite5;

    Texture bitmanTexture;
    Player bitman;

    Texture bombTexture;
    ArrayList<Bomb> bombs;

    Texture shieldTexture;
    Shield shield;

    OrthographicCamera camera;

    Stage stage;

    Touchpad touchpad;
    Skin touchpadSkin;
    Touchpad.TouchpadStyle touchpadStyle;
    Drawable touchBackground;
    Drawable touchKnob;
    int w ,h;

    World world;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;

    ArrayList<Body> moveBodiesBuffer = new ArrayList<Body>();
    ArrayList<Bomb> animatingBombs = new ArrayList<Bomb>();

    Text scoreText;
    Text highScoreText;

    Text gameOverText;

    final int BITMAN = 1, BOMB = 0, PLATFORM = 2, SHIELD = 3;

    Random random = new Random();

    Preferences prefs = Gdx.app.getPreferences("GamePreferences");

    GameScreen(final BitmanGame game, int difficulty) {
        this.game = game;
        this.difficulty = difficulty;
        batch = new SpriteBatch();

        w = 854;
        h = 480;

        world = new World(new Vector2(0,-2f),true);
        world.setContactListener(new MyContactListener());

        camera = new OrthographicCamera(w, h);
        camera.position.set(w/2, h/2, 0);
        camera.update();

        backgroundTexture = new Texture("background.png");
        backgroundSprite = new Sprite(backgroundTexture);

        platformTexture = new Texture("platform.png");

        buildingTexture = new Texture("building.png");
        buildingSprite1 = new Sprite(buildingTexture);
        buildingSprite2 = new Sprite(buildingTexture);
        buildingSprite3 = new Sprite(buildingTexture);
        buildingSprite4 = new Sprite(buildingTexture);
        buildingSprite5 = new Sprite(buildingTexture);

        bombTexture = new Texture("bomb/jbomb.png");
        bitmanTexture = new Texture("bitman/bitman.png");
        shieldTexture = new Texture("shield.png");

        //initializing bombs
        bombs = new ArrayList<Bomb>();
        for(int i = 0;i < difficulty;i++) {
            bombs.add(new Bomb(bombTexture,
                    1 + random.nextInt(700-100+1),
                    h + bombTexture.getHeight()/2
                            + random.nextInt((int)(h
                            + bombTexture.getHeight()/2
                            + 1 - h
                            + bombTexture.getHeight()/2
                            +1)),
                    world));
        }


        //initializing bitman
        bitman = new Player(bitmanTexture,w/2,h/3,world);

        //initializing shield
        shield = new Shield(shieldTexture,1 + random.nextInt(700-100+1),
                h + bombTexture.getHeight()/2
                        + random.nextInt((int)(h + bombTexture.getHeight()/2
                        + 1 - h
                        + bombTexture.getHeight()/2
                        +1)),
                world);

        //initializing platform
        platform = new Platform(platformTexture,
                (w/2-(platformTexture.getWidth()/2)),
                (int)(bitman.sprite.getY()-(platformTexture.getHeight())),
                world);
        platform.body.setTransform(
                (platform.sprite.getX() + platform.sprite.getWidth()/2)/100,
                (platform.sprite.getY() + platform.sprite.getHeight()/2)/100,
                platform.body.getAngle());

        //scaling and positioning buildings
        buildingSprite1.setPosition(0,0);
        buildingSprite1.setSize(120,260);

        buildingSprite2.setPosition(200,0);
        buildingSprite2.setSize(120,260);

        buildingSprite3.setPosition(400,0);
        buildingSprite3.setSize(120,260);

        buildingSprite4.setPosition(600,0);
        buildingSprite4.setSize(120,260);

        buildingSprite5.setPosition(780,0);
        buildingSprite5.setSize(120,260);

        debugMatrix=new Matrix4(camera.combined);
        debugMatrix.scale(100f, 100f, 1f);
        debugRenderer = new Box2DDebugRenderer();

        //Create a Stage and add TouchPad
        stage = new Stage(new ExtendViewport(w,h),batch);
     //   makeTouchpad();
        Gdx.input.setInputProcessor(stage);

        scoreText = new Text(5,h - 5,false,19,"fonts/slkscrb.ttf");
        scoreText.font.setColor(Color.BLUE);

        scoreText.setText("random scoreText");
        stage.addActor(scoreText);


        highScoreText = new Text(5,h - 5 - 19,false,19,"fonts/slkscrb.ttf");
        highScoreText.font.setColor(Color.GOLD);

        highScore = prefs.getInteger("score",0);
        highScoreText.setText("high score: "+highScore);
        stage.addActor(highScoreText);

        Skin buttonSkin;
        buttonSkin = new Skin(Gdx.files.internal("uiskin.json"));

        final TextButton pauseButton;
        //final TextButton exitButton;

        pauseButton = new TextButton("pause",buttonSkin);
        pauseButton.setPosition(w-60,h-30);
        pauseButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    paused = !paused;

                    if(paused){
                        pauseButton.setText("resume");
                    }
                    else{
                        pauseButton.setText("pause");
                    }
                }
            }
        );
        stage.addActor(pauseButton);

     //   exitButton = new TextButton("exit",buttonSkin);
       // exitButton.setPosition(w-60,h-60);
      //  exitButton.addListener(new ClickListener() {
        //        @Override
          //      public void clicked(InputEvent event, float x, float y) {
            //        game.setScreen(new MainMenuScreen(game));
              //  }
    //        }
      //  );
    //    stage.addActor(exitButton);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        gameOverText = new Text(w/2,h/2,true,50,"fonts/8-bit_wonder.ttf");
        gameOverText.font.setColor(Color.RED);
        gameOverText.setPosition(w/2,h/2 - (gameOverText.font.getLineHeight()/2));
        stage.addActor(gameOverText);
    }

    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        scoreText.setText("score: "+score);
        //get a preferences instance

        if(score > highScore) {
            //put some Integer
            prefs.putInteger("score", score);
            //persist preferences
            prefs.flush();
            highScoreText.setText("high score: "+score);
        }

        for(Bomb b : bombs){
            if(moveBodiesBuffer.contains(b.body)){
                animatingBombs.add(b);
            }
        }

        if(gameOver) {
            if (Gdx.input.justTouched()) {
                game.setScreen(new GameScreen(game,difficulty));
            }
        }

        if(!paused && !gameOver) {
            world.step(1f/60f,6,2);

            if(gameOver) {
                gameOverText.setText("Game Over");
            }
            else {
                moveBitman();
            }
            bitman.update();

            for(Bomb b : bombs){
                //animate the bombs that made contact
                if(animatingBombs.contains(b)) {
                    b.stateTime += Gdx.graphics.getDeltaTime();
                    b.currentFrame = b.animation.getKeyFrame(b.stateTime, false);
                    if (b.animation.isAnimationFinished(b.stateTime)) {
                        animatingBombs.remove(b);

                        b.body.setTransform(
                                0.5f + random.nextInt(8),
                                h / 100f + b.sprite.getHeight()/200f + random.nextInt(
                                        (int)(h / 100f + b.sprite.getHeight()/200f
                                                + 1 - h / 100f
                                                + b.sprite.getHeight()/200f
                                                +1)),
                                b.body.getAngle());
                        b.body.setAwake(true);
                        moveBodiesBuffer.remove(b.body);
                    }
                }
                else {
                    b.stateTime = 0f;
                    b.currentFrame = b.animation.getKeyFrames()[0];
                }

                if(b.body.getPosition().y <0) {
                    moveBodiesBuffer.add(b.body);
                }
                b.update();
            }
            if(moveBodiesBuffer.contains(shield.body)) {
                shield.body.setTransform(
                        2 + random.nextInt(7-1+1),
                        h / 100f + shield.sprite.getHeight()/200
                                        + random.nextInt((int)(h / 100f
                                        + shield.sprite.getHeight()/200f
                                        + 1 - h / 100f
                                        + shield.sprite.getHeight()/200f
                                        + 1)),
                        shield.body.getAngle());
                moveBodiesBuffer.remove(shield.body);
                shield.move = false;
            }
            if(shield.move) {
                shield.body.setLinearVelocity(0,-2.5f);
            }
            else {
                shield.body.setLinearVelocity(0,0.5f);
            }

            if(score % 20 == 0){
                shield.move = true;
            }
            shield.update();

        }

        //...................drawing stuff..........................
        batch.begin();

        batch.draw(backgroundSprite, 0, -60);

        buildingSprite1.draw(batch);
        buildingSprite2.draw(batch);
        buildingSprite3.draw(batch);
        buildingSprite4.draw(batch);
        buildingSprite5.draw(batch);

        platform.sprite.draw(batch);

        //bitman.sprite.draw(batch);
        batch.draw(bitman.currentFrame,bitman.sprite.getX(),bitman.sprite.getY());

        for(Bomb bomb : bombs) {
        //    bomb.sprite.draw(batch);
            batch.draw(bomb.currentFrame,bomb.sprite.getX(),bomb.sprite.getY());
        }
        batch.draw(shield.sprite,shield.sprite.getX(),shield.sprite.getY());

        batch.end();
        //...................end drawing stuff......................

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

   //     debugRenderer.render(world, debugMatrix);
    }

    void moveBitman(){
        bitman.body.setLinearVelocity(Gdx.input.getAccelerometerY(),0);

        if(Gdx.input.getAccelerometerY() > 0) {
            if(bitman.isShieldOn()){
                bitman.currentFrame = bitman.frames[6];
            }
            else{
                bitman.currentFrame = bitman.frames[2];
            }
        }
        else if(Gdx.input.getAccelerometerY() < 0) {
            if(bitman.isShieldOn()){
                bitman.currentFrame = bitman.frames[5];
            }
            else{
                bitman.currentFrame = bitman.frames[1];
            }
        }
        else {
            if(bitman.isShieldOn()){
                bitman.currentFrame = bitman.frames[4];
            }
            else{
                bitman.currentFrame = bitman.frames[0];
            }
        }
        /*  bitman.body.setLinearVelocity(touchpad.getKnobPercentX()*3f,0);
        if(touchpad.isTouched()) {
            if (touchpad.getKnobPercentX() > 0) {
                bitman.currentFrame = bitman.frames[2];
            } else if (touchpad.getKnobPercentX() < 0) {
                bitman.currentFrame = bitman.frames[1];
            }
        }*/

        if(gameOver)
            bitman.currentFrame = bitman.frames[3];

        if(bitman.body.getPosition().x > 7.7f) {
            bitman.body.setTransform(7.7f,1.6f,bitman.body.getAngle());
        }
        else if (bitman.body.getPosition().x < 0.83f) {
            bitman.body.setTransform(0.83f,1.6f,bitman.body.getAngle());
        }
    }

    void makeTouchpad(){
        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("touchpad/touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("touchpad/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 90, 90);

        stage.addActor(touchpad);
    }

    public class MyContactListener implements ContactListener {
        @Override
        public void beginContact(Contact contact) {
            //bomb contacts
            if(contact.getFixtureA().getBody().getUserData().equals(BOMB)
                    && !contact.getFixtureB().getBody().getUserData().equals(BOMB)
                    && !contact.getFixtureB().getBody().getUserData().equals(SHIELD)) {
                moveBodiesBuffer.add(contact.getFixtureA().getBody());
                if(contact.getFixtureB().getBody().getUserData().equals(BITMAN)) {
                    if (bitman.isShieldOn()) {
                        //if shield is on, remove shield.
                        bitman.setShieldOn(false);
                        bitman.currentFrame = bitman.frames[0];
                    } else {
                        gameOver = true;
                        bitman.currentFrame = bitman.frames[3];
                    }
                }
                else if(contact.getFixtureB().getBody().getUserData().equals(PLATFORM)){
                    score++;
                }
            }
            if(contact.getFixtureB().getBody().getUserData().equals(BOMB)
                    && !contact.getFixtureA().getBody().getUserData().equals(BOMB)
                    && !contact.getFixtureA().getBody().getUserData().equals(SHIELD)) {
                moveBodiesBuffer.add(contact.getFixtureB().getBody());
                if(contact.getFixtureA().getBody().getUserData().equals(BITMAN)) {
                    if (bitman.isShieldOn()) {
                        //if shield is on, remove shield.
                        bitman.setShieldOn(false);
                        bitman.currentFrame = bitman.frames[0];
                    } else {
                        gameOver = true;
                        bitman.currentFrame = bitman.frames[3];
                    }
                }
                else if(contact.getFixtureA().getBody().getUserData().equals(PLATFORM)){
                    score++;
                }
            }
            //shield contacts
            if(contact.getFixtureA().getBody().getUserData().equals(SHIELD)
                    && !contact.getFixtureB().getBody().getUserData().equals(BOMB)) {
                moveBodiesBuffer.add(contact.getFixtureA().getBody());
                if(contact.getFixtureB().getBody().getUserData().equals(BITMAN)) {
                    bitman.setShieldOn(true);
                    bitman.currentFrame = bitman.frames[4];
                }
            }
            if(contact.getFixtureB().getBody().getUserData().equals(SHIELD)
                    && !contact.getFixtureA().getBody().getUserData().equals(BOMB)) {
                moveBodiesBuffer.add(contact.getFixtureB().getBody());
                if(contact.getFixtureA().getBody().getUserData().equals(BITMAN)) {
                    bitman.setShieldOn(true);
                    bitman.currentFrame = bitman.frames[4];
                }
            }
        }

        @Override
        public void endContact(Contact contact) {
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}