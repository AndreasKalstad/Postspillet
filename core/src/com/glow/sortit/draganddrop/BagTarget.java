package com.glow.sortit.draganddrop;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.glow.sortit.actors.BagActor;
import com.glow.sortit.actors.LetterActor;

public class BagTarget extends DragAndDrop.Target {
	
	private BagActor actor;
	
    public BagTarget(BagActor actor) {
        super(actor);
        this.actor = actor;
    }

    @Override
	public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x,float y, int pointer) {
        return true;
	}

    @Override
	public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
	}

    @Override
	public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
    	LetterActor letterActor = (LetterActor) payload.getDragActor();
    	if(actor.getBag().getCountry().equals(letterActor.getLetter().getNationality())){
    		actor.getBag().increaseLevel();
		}
	}
}

