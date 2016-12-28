package com.nischalbasuti1.bitman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.awt.Font;

/*
 * Created by nischal on 7/15/2016.
 */
class MainMenuScreen implements Screen {

    private final BitmanGame game;

    private Stage stage;

    private OrthographicCamera camera;


    MainMenuScreen(final BitmanGame game){


        this.game = game;
        float w = 854;
        float h = 480;
        camera = new OrthographicCamera(w, h);
        camera.position.set(w /2, h /2, 0);
        camera.update();

        stage = new Stage(new ExtendViewport(w, h));
        Gdx.input.setInputProcessor(stage);

        Text heading = new Text(w / 2, h - 10, true, 50, "fonts/8-bit_wonder.ttf");
        heading.setText("bitman");
        heading.font.setColor(Color.BLACK);

        stage.addActor(heading);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton startButton = new TextButton("Start", skin);
        TextButton helpButton = new TextButton("Help", skin);
        TextButton aboutButton = new TextButton("About", skin);
        TextButton scoresButton = new TextButton("Scores", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        startButton.setPosition(w /2, h -90);
        helpButton.setPosition(w /2, h -150);
        aboutButton.setPosition(w /2, h -210);
        scoresButton.setPosition(w /2, h -270);
        exitButton.setPosition(w /2, h -330);

        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new GameScreen(game,3));
            }
        });
        aboutButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new NewScreen(game,"about"));
            }
        });
        helpButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new NewScreen(game,"help"));
            }
        });
        scoresButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new NewScreen(game,"scores"));
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.exit(0);
            }
        });

        stage.addActor(startButton);
        stage.addActor(helpButton);
        stage.addActor(aboutButton);
        stage.addActor(scoresButton);
        stage.addActor(exitButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        stage.act();
        stage.draw();
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