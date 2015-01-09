package objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BagPickUp {
	private float bagPickUpMoveTime;
	private float bagPickUpStateTime;
	private Animation bagPickUpAnimation;
	
	public BagPickUp(float bagPickUpMoveTime, float bagPickUpStateTime,  Animation bagPickUpAnimation){
		this.bagPickUpMoveTime = bagPickUpMoveTime;
		this.bagPickUpStateTime = bagPickUpStateTime;
		this.bagPickUpAnimation = bagPickUpAnimation;
	}
	
	public Animation getBagPickUpAnimation() {
		return bagPickUpAnimation;
	}

	public void setBagPickUpAnimation(Animation bagPickUpAnimation) {
		this.bagPickUpAnimation = bagPickUpAnimation;
	}

	public float getBagPickUpMoveTime() {
		return bagPickUpMoveTime;
	}

	public void setBagPickUpMoveTime(float bagPickUpMoveTime) {
		this.bagPickUpMoveTime = bagPickUpMoveTime;
	}

	public float getBagPickUpStateTime() {
		return bagPickUpStateTime;
	}

	public void setBagPickUpStateTime(float bagPickUpStateTime) {
		this.bagPickUpStateTime = bagPickUpStateTime;
	}

	public TextureRegion getBagPickUpFrame() {
		return bagPickUpAnimation.getKeyFrame(bagPickUpStateTime, true);
	}
}
