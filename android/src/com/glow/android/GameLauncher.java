package com.glow.android;

import objects.PostGame;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class GameLauncher extends AndroidApplication {
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	
	      AndroidApplicationConfiguration config= new AndroidApplicationConfiguration();
	      config.useAccelerometer = false;
	      config.useCompass = false;
	
	      initialize(new PostGame(), config);
	   }
}