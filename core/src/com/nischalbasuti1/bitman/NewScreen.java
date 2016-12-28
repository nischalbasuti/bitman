package com.nischalbasuti1.bitman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/*
 * Created by nischal on 7/18/2016.
 */
public class NewScreen implements Screen {

    BitmanGame game;
    SpriteBatch batch;

    float w = 854;
    float h = 480;

    Stage stage;

    OrthographicCamera camera;

    Text text;

    NewScreen(BitmanGame game, String type) {
        this.game = game;
        camera = new OrthographicCamera(w, h);
        camera.position.set(w/2, h/2, 0);
        camera.update();

        stage = new Stage(new ExtendViewport(w,h));
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();
        text = new Text(10,h-10,false,12,"fonts/8-bit_wonder.ttf");
        text.font.setColor(Color.BLACK);
        stage.addActor(text);

        if(type.equals("help")) {
            text.setText("Help Bit-man dodge bombs to survive as long as possible. \nMove Bit-man "
                    + "by tilting your phone.\nCatch shields to add a protective layer to bitman");
        }

        if(type.equals("about")) {
            text.setText("Bit-man was developed and designed by Nischal Basuti.");
        }

        if(type.equals("scores")) {
            text.setText("High Scores: ");
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game));
        }

        batch.begin();

        batch.end();

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
