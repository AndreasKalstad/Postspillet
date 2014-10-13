package com.glow.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Intro extends Activity{
	public static Button start;
	
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      
	      setContentView(R.layout.intro);
	      
	      start = (Button) findViewById(R.id.startgame);
	      start.setOnClickListener(new OnClickListener() {	
	    	  public void onClick(View v) {
					startGame();
				}
			});
	   }
	   
	   public void startGame(){
		   Intent i = new Intent(this, GameLauncher.class);
		   startActivity(i);
	   }
}

