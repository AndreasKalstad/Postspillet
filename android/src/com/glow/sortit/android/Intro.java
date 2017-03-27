package com.glow.sortit.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Intro extends Activity {
	public static Button start;
	public static Button leaderboard;
	private Button credit;
    private Button exit;

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
	  leaderboard = (Button) findViewById(R.id.leaderboard);

	  credit = (Button) findViewById(R.id.credit);
	  credit.setOnClickListener(new OnClickListener() {
		  public void onClick(View v) {

		  }
	  });

	   exit = (Button) findViewById(R.id.exitgame);
	   exit.setOnClickListener(new OnClickListener() {
		   public void onClick(View v) {
			   System.exit(0);
		   }
	   });
   }

   public void startGame(){
	   Intent i = new Intent(this, GameLauncher.class);
	   startActivity(i);
   }

}

