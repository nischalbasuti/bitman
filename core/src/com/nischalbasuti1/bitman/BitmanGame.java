package com.nischalbasuti1.bitman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BitmanGame extends Game {
	SpriteBatch batch;

	int bombCount;
	public BitmanGame(int bombCount){
		this.bombCount = bombCount;
	}
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new GameScreen(this,bombCount));
	}

	@Override
	public void render () {
		super.render();
	}

	public void dispose() {
		batch.dispose();
	}
}
