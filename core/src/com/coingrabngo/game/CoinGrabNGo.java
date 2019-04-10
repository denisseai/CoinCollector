package com.coingrabngo.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.soap.Text;

public class CoinGrabNGo extends ApplicationAdapter {
	SpriteBatch batch;
	int characterY = 0;
	int characterX = 0;
	int characterState = 0;
	int pause = 0;
	int coinCount;
	int bombCount;
	int score = 0;
	int gameState = 0;
	float gravity = 0.2f;
	float velocity = 0;

	Random random;

	ArrayList<Integer> coinX = new ArrayList<Integer>();
	ArrayList<Integer> coinY = new ArrayList<Integer>();
	ArrayList<Rectangle> coinRectangle = new ArrayList<Rectangle>();

	ArrayList<Integer> bombX = new ArrayList<Integer>();
	ArrayList<Integer> bombY = new ArrayList<Integer>();
	ArrayList<Rectangle> bombRectangle = new ArrayList<Rectangle>();

	Texture background;
	Texture[] character;
	Texture characterDizzy;
	Texture coin;
	Texture bomb;

	Rectangle rectCharacter;
	BitmapFont font;

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
		characterX = Gdx.graphics.getWidth() / 2 - character[characterState].getWidth() / 2;
		characterDizzy = new Texture("dizzy-1.png");

		coin = new Texture("coin.png");
		bomb = new Texture("bomb.png");
		random = new Random();

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);
	}
	public void  makeCoin(){
		float height = random.nextFloat()* Gdx.graphics.getHeight();
		coinY.add((int)height);
		coinX.add(Gdx.graphics.getWidth());
	}
	public void  makeBomb() {
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		bombY.add((int) height);
		bombX.add(Gdx.graphics.getWidth());
	}
	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1){
			//GAME IS LIVE
			//COIN
			if(coinCount < 100){
				coinCount++;
			}else{
				coinCount = 0;
				makeCoin();
			}
			coinRectangle.clear();
			for (int i =0; i< coinX.size(); i++){
				batch.draw(coin, coinX.get(i), coinY.get(i));
				coinX.set(i, coinX.get(i)-4);
				coinRectangle.add(new Rectangle(coinX.get(i), coinY.get(i), coin.getWidth(), coin.getHeight()));
			}
			//BOMB
			if(bombCount < 250){
				bombCount++;
			}else{
				bombCount = 0;
				makeBomb();
			}
			bombRectangle.clear();
			for (int i =0; i< bombX.size(); i++) {
				batch.draw(bomb, bombX.get(i), bombY.get(i));
				bombX.set(i, bombX.get(i) - 6);
				bombRectangle.add(new Rectangle(bombX.get(i), bombY.get(i), bomb.getWidth(), bomb.getHeight()));
			}
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
		}else if(gameState == 0){
			//Waiting to Start
			if (Gdx.input.justTouched()){
				gameState = 1;
			}
		}else if (gameState == 2){
			//GAME OVER
			if (Gdx.input.justTouched()) {
				gameState = 1;
				characterY =  Gdx.graphics.getHeight() / 2;
				score = 0;
				velocity = 0;
				coinX.clear();
				coinY.clear();
				coinRectangle.clear();
				coinCount = 0;
				bombX.clear();
				bombY.clear();
				bombRectangle.clear();
				bombCount = 0;
			}
		}
		if (gameState == 2){
			batch.draw(characterDizzy, characterX, characterY);
		}else{
			batch.draw(character[characterState], characterX, characterY);
		}
		rectCharacter = new Rectangle(characterX, characterY, character[characterState].getWidth(), character[characterState].getHeight());
		for (int i = 0; i < coinRectangle.size();i++){
			if (Intersector.overlaps(rectCharacter, coinRectangle.get(i))){
				score++;
				coinRectangle.remove(i);
				coinX.remove(i);
				coinY.remove(i);
				break;
			}
		}
		for (int i = 0; i < bombRectangle.size();i++){
			if (Intersector.overlaps(rectCharacter, bombRectangle.get(i))){
				gameState = 2;
			}
		}
		font.draw(batch, String.valueOf(score), 100, 200);
		batch.end();
	}
	@Override
	public void dispose () {
		batch.dispose();
	}
}
