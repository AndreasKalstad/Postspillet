package draganddrop;

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
	private float x;
	private float y;
	
	public LetterSource(LetterActor actor) {
		super(actor);
		this.actor = actor;
		actor.setBounds(50, 125, Gdx.graphics.getWidth()/6.4f, Gdx.graphics.getHeight()/6.4f);
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
		}
	}
}
