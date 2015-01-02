package draganddrop;

import actors.BagActor;
import actors.LetterActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;

public class BagTarget extends Target {
	
	private BagActor actor;
	
    public BagTarget(BagActor actor) {
        super(actor);
        this.actor = actor;
        actor.setBounds(actor.getStartX(), Gdx.graphics.getHeight()/1.8f, Gdx.graphics.getWidth()/9.5f, Gdx.graphics.getHeight()/4f);
    }

    @Override
	public boolean drag(Source source, Payload payload, float x,float y, int pointer) {
		return true;
	}

    @Override
	public void reset(Source source, Payload payload) {
	}

    @Override
	public void drop(Source source, Payload payload, float x, float y, int pointer) {
    	LetterActor letterActor = (LetterActor) payload.getDragActor();
    	if(actor.getBag().getCountry().equals(letterActor.getLetter().getNationality())){
    		actor.getBag().increaseLevel();
		}
	}
}

