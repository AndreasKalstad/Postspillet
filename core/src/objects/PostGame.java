package objects;

import com.badlogic.gdx.Game;

public class PostGame extends Game {
	private Glow glow;
    private Pause pause;

   @Override
    public void create() {
        glow = new Glow(this);
        pause = new Pause(this);
        setScreen(glow);              
    }
}
