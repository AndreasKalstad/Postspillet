package com.glow.sortit.draganddrop;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.glow.sortit.actors.BagActor;
import com.glow.sortit.actors.LetterActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.glow.sortit.user.User;

public class LetterSource extends DragAndDrop.Source {

    private Actor actor;
    private User user;
    private float x;
    private float y;
    DragAndDrop dragAndDrop;

    public LetterSource(LetterActor actor, User user, DragAndDrop dragAndDrop) {
        super(actor);
        this.actor = actor;
        this.dragAndDrop = dragAndDrop;
        this.user = user;
    }

    @Override
    public DragAndDrop.Payload dragStart(InputEvent event, float x, float y,int pointer) {
        DragAndDrop.Payload payload = new DragAndDrop.Payload();
        payload.setDragActor(actor);
        dragAndDrop.setDragActorPosition((-(Gdx.graphics.getWidth()/5.5f)/2), (Gdx.graphics.getHeight()/5.9f)/2);
        this.x = actor.getX();
        this.y = actor.getY();
        return payload;
    }

    @Override
    public void dragStop(InputEvent event, float x, float y,int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
        if (target == null) {
            if(event.getStageY() > Gdx.graphics.getHeight()/2){
                actor.setBounds(this.x, this.y, Gdx.graphics.getWidth()/6.4f, Gdx.graphics.getHeight()/6.4f);
            }
            actor.getStage().addActor(actor);
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
        actor.getStage().addActor(actor);
    }
}
