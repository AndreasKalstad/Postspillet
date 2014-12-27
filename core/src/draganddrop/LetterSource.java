package draganddrop;

import user.User;
import actors.BagActor;
import actors.LetterActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;

public class LetterSource extends Source {
	
	private Actor actor;
	private User user;
	private float x;
	private float y;
	
	public LetterSource(LetterActor actor, User user) {
		super(actor);
		this.actor = actor;
		actor.setBounds(150, 165, Gdx.graphics.getWidth()/6.4f, Gdx.graphics.getHeight()/6.4f);
		this.user = user;
	}

	@Override
	public Payload dragStart(InputEvent event, float x, float y,int pointer) {
		Payload payload = new Payload();
		payload.setDragActor(actor);
		this.x = actor.getX();
		this.y = actor.getY();
		return payload;
	}

	@Override
	public void dragStop(InputEvent event, float x, float y,int pointer, DragAndDrop.Payload payload,DragAndDrop.Target target) {
		if (target == null) {
			if(event.getStageY() > 280){
				actor.setBounds(this.x, this.y, Gdx.graphics.getWidth()/6.4f, Gdx.graphics.getHeight()/6.4f);
			}
			Stage stage = actor.getStage();
			stage.addActor(actor);
		} else {
			BagActor bagActor = (BagActor) target.getActor();
			LetterActor letterActor = (LetterActor) payload.getDragActor();
			if(bagActor.getBag().getCountry().equals(letterActor.getLetter().getNationality())){
				user.increasePoints();
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
