package com.glow.sortit.objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

public class PostGame extends Game {
	private Glow glow;
    private Pause pause;

   @Override
    public void create() {
        glow = new Glow(this);
        pause = new Pause(this);
        setScreen(glow);
    }
   
   public Screen getPauseScreen(){
	   return pause;
   }
   
   public Screen getGameScreen(){
	   return glow;
   }
   
   public void resume(){
	   glow.resume();
   }
   
   public void newGame(Glow newGame){
	   glow = newGame;
   }
}
