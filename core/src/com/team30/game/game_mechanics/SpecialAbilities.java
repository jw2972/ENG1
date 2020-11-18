package com.team30.game.game_mechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.team30.game.game_mechanics.Auber;
import com.team30.game.game_mechanics.Infiltrator;


public class SpecialAbilities {
	/**
	 * the value for loop
	 */
	int i=0;
	
	 /**
     * The time for coolDown special ability
     */
	public float coolDown;
	
	 /**
     * the time for Auber hallucination
     */
	public float hallucinationTime;
	
	TiledMapTileLayer roomTiles;
	
	String name;
	
	Auber auber= new Auber(roomTiles);
	Infiltrator infil=new Infiltrator(roomTiles,name, coolDown, hallucinationTime);
	
	/**

     *if infiltrator in the brig or be captured by Auber can't use ability,
     *if Auber is nearby and cooldown is over,infiltrator will use the ability
     *@param coolDown the time for coolDown special ability
     *@param hallucinationTime the time for Auber hallucination
     */
	
	public void showHallucinations() {
		while(i==1) {

		//TODO Determine when infiltrator at brig position or being captured can not use this special ability.
			
			if (infil.position.x-auber.position.x < 2 | infil.position.y-auber.position.y< 2 ) {
				auber.setXVelocity(0);
				auber.setYVelocity(0);
				auber.VELOCITY_CHANGE=0;
				infil.hallucinationTime=hallucinationTime;
				infil.coolDown=coolDown;
				
				if(this.hallucinationTime == 0 ) { 
					auber.VELOCITY_CHANGE = 2f;}
				if(this.coolDown == 0) {
					continue;
				}
				
			}
		}
		}

	
	/**

     *@param VELOCITY_CHANGE higher velocity change for special infiltrator
     *@param MAX_VELOCITY higher Max velocity for special infiltrator
     */
	public void fasterSpeed(float VELOCITY_CHANGE, float MAX_VELOCITY) {
		
		infil.VELOCITY_CHANGE=VELOCITY_CHANGE;
		infil.MAX_VELOCITY=MAX_VELOCITY;
	}
	
	
	/**
	* @param inflicted The amount of damage to be inflicted to the system
	*/
	public void highDamage(int inflicted) {
		//When creating an applydamage(int inflicted) method in GameSystem,fill in a higher value in applydamage.
	}
	

	
	
	 /**
	 * @param time The time that has passed
     */
    public void updateHallucinationTime(float delta) {
        this.hallucinationTime -= delta;
    }
    /**
     * @param time The time that has passed
     */
    public void updateCoolDown(float delta) {
        this.coolDown -= delta;
    }
     }
     
	

