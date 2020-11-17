package com.team30.game.game_mechanics;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Invisible {
	public static float statetime;
	Animation animation;
	Texture texture;
	TextureRegion[]currentFrame;
	TextureRegion Frame;
	
	/**
     * @param delay the time to change diagram.( It can be used as cooling down time.)
     */
	public void show(float delay) {
		texture = new Texture(Gdx.files.internal("data/infiltrator_change.png"));
		
		TextureRegion region = new TextureRegion( texture );
		TextureRegion[] regions = region.split( 64, 64 )[0];
		animation = new Animation(delay, regions[0], regions[1]);
		statetime = 0;
	}
	
	public void draw(SpriteBatch batch) {
		statetime += Gdx.graphics.getDeltaTime();
		Frame=animation.getKeyFrame(statetime,true);
		batch.begin();
		batch.draw(Frame, 100, 100);
		batch.end();
		
	}

}


