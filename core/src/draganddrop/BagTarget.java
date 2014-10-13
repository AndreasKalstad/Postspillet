package draganddrop;

import objects.Bag;
import actors.BagActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;

public class BagTarget extends Target {
	
	private BagActor actor;
	
    public BagTarget(BagActor actor) {
        super(actor);
        this.actor = actor;
        actor.setBounds(-100, 370, 50, 50);
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
    	Bag bag = actor.getBag();
    	bag.increaseLevel();
	}
}

