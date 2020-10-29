package com.team30.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameContainer extends Game {
	static final float SCREEN_WIDTH = 800;
	static final float SCREEN_HEIGHT = 480;
	public BitmapFont font;
	SpriteBatch batch;
	Texture img;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		img = new Texture("No.png");
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}
}
