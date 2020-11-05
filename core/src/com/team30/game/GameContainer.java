package com.team30.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team30.game.screen.MainMenu;

public class GameContainer extends Game {
	// TODO Get rid of this
	public static final float SCREEN_WIDTH = 640;
	public static final float SCREEN_HEIGHT = 480;
	public BitmapFont font;
	public SpriteBatch batch;
	public Texture img;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.RED);
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
