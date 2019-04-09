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

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		character = new Texture[4];
		character[0] = new Texture("frame-1.png");
		character[1] = new Texture("frame-2.png");
		character[2] = new Texture("frame-3.png");
		character[3] = new Texture("frame-4.png");
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if (characterState < 3){
			characterState++;
		}else{
			characterState = 0;
		}
		batch.draw(character[characterState],Gdx.graphics.getWidth()/2- character[characterState].getWidth()/2, Gdx.graphics.getHeight()/2);
		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
