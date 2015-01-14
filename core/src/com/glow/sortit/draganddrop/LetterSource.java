package com.glow.sortit.draganddrop;

import java.util.Random;

import com.glow.sortit.actors.BagActor;
import com.glow.sortit.actors.LetterActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.glow.sortit.user.User;

public class LetterSource extends Source {

    private Actor actor;
    private User user;
    private float x;
    private float y;
    private DragAndDrop dragAndDrop;

    public LetterSource(LetterActor actor, User user, DragAndDrop dragAndDrop) {
        super(actor);
        this.actor = actor;
        this.dragAndDrop = dragAndDrop;
        Random ran = new Random();
        actor.setSize(Gdx.graphics.getWidth()/5.5f, Gdx.graphics.getHeight()/5.9f);
        actor.setPosition(ran.nextInt((int)(Gdx.graphics.getWidth()-actor.getWidth())), ran.nextInt((int)(Gdx.graphics.getHeight()/2-actor.getHeight()/2)));
        this.user = user;
    }

    @Override
    public Payload dragStart(InputEvent event, float x, float y,int pointer) {
        Payload payload = new Payload();
        payload.setDragActor(actor);
        System.out.println(x + " | " + y);
        //  dragAndDrop.setDragActorPosition(actor.getX() + (actor.getWidth()/2), -(actor.getY() + (actor.getHeight()/2)));
        this.x = actor.getX();
        this.y = actor.getY();
        return payload;
    }

    @Override
    public void dragStop(InputEvent event, float x, float y,int pointer, DragAndDrop.Payload payload,DragAndDrop.Target target) {
            if (target == null) {
                if(event.getStageY() > Gdx.graphics.getHeight()/2){
                    actor.setBounds(this.x, this.y, Gdx.graphics.getWidth()/6.4f, Gdx.graphics.getHeight()/6.4f);
                }
                Stage stage = actor.getStage();
                stage.addActor(actor);
            } else {
            BagActor bagActor = (BagActor) target.getActor();
			LetterActor letterActor = (LetterActor) payload.getDragActor();
			if(bagActor.getBag().getCountry().equals(letterActor.getLetter().getNationality())){
				user.increasePoints();
				user.setScoreChanged(true);
			} else {
				resetLetter();
			}
		}
    }

    private void resetLetter(){
        actor.setBounds(this.x, this.y, Gdx.graphics.getWidth()/6.4f, Gdx.graphics.getHeight()/6.4f);
        Stage stage = actor.getStage();
        stage.addActor(actor);
    }
}
