package com.nischalbasuti1.bitman;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.nischalbasuti1.bitman.BitmanGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		int bombs = bundle.getInt("bombs");

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new BitmanGame(bombs), config);
	}
}
