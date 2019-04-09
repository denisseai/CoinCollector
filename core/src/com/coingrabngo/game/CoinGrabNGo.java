package com.coingrabngo.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.xml.soap.Text;

public class CoinGrabNGo extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] character;
	int characterState = 0;
	int pause = 0;
	float gravity = 0.2f;
	float velocity = 0;
	int characterY = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		character = new Texture[4];
		character[0] = new Texture("frame-1.png");
		character[1] = new Texture("frame-2.png");
		character[2] = new Texture("frame-3.png");
		character[3] = new Texture("frame-4.png");
		characterY =  Gdx.graphics.getHeight() / 2;
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (Gdx.input.justTouched()){
			velocity = -10;
		}
		if (pause < 8){
			pause++;
		}else {
			pause = 0;
			if (characterState < 3) {
				characterState++;
			} else {
				characterState = 0;
			}
		}
		velocity += gravity;
		characterY -= velocity;

		if (characterY <= 0){
			characterY = 0;
		}

		batch.draw(character[characterState], Gdx.graphics.getWidth() / 2 - character[characterState].getWidth() / 2, characterY);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
