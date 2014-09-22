package com.glow;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Letter {
	private Rectangle rectangle;
	private Texture texture;
	
	public Letter(Rectangle rectangle, Texture texture){
		this.rectangle = rectangle;
		this.texture = texture;
	}
	
	public Rectangle getRectangle(){
		return rectangle;
	}
	
	public float getX(){
		return rectangle.x;
	}
	
	public float getY(){
		return rectangle.y;
	}
	
	public void setX(float x){
		this.rectangle.x = x;
	}
	
	public void setY(float y){
		this.rectangle.y = y;
	}

	public Texture getTexture() {
		return texture;
	}
}
