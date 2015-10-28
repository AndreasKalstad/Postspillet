package com.glow.sortit.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.glow.sortit.main.PostGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Postspillet!";
        config.width = 800;
        config.height = 480;
        config.resizable = false;
		new LwjglApplication(new PostGame(), config);
	}
}
