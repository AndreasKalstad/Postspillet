package objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

public class PostGame extends Game {
	private Glow glow;
    private Pause pause;
	private InputMultiplexer im;

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
   
   public void setMultiplexer(InputMultiplexer im){
	   this.im = im;
   }
   
   public InputMultiplexer getMultiplexer(){
	   return im;
   }
   
   public void resume(){
	   glow.resume();
   }
}
