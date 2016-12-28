package com.nischalbasuti1.bitman.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nischalbasuti1.bitman.BitmanGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 854;
		config.height = 480;
		new LwjglApplication(new BitmanGame(4), config);
	}
}
