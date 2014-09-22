package com.glow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Bag {
	private Rectangle rectangle;
	private Texture[] textures;
	private Texture texture;
	private int size;
	private int position;
	
	public Bag(Rectangle rectangle, int position, Texture texture){
		this.rectangle = rectangle;
		this.position = position;
		this.texture = texture;
		this.size = 0;
		textures = new Texture[]{new Texture(Gdx.files.internal("bag1.jpg")),new Texture(Gdx.files.internal("bag2.png")),new Texture(Gdx.files.internal("droplet.png"))};
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setBag(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	
	public float getX() {
		return rectangle.x;
	}

	public void setX(float x) {
		this.rectangle.x = x;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void upgrade() {
		texture = textures[size];
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
