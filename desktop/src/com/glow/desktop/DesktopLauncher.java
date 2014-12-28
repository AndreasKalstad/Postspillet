package com.glow.desktop;

import objects.Glow;
import objects.PostGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
   public static void main(String[] args) {
      LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
      config.title = "Drop";
      config.width = 800;
      config.height = 480;
      new LwjglApplication(new PostGame(), config);
   }
}