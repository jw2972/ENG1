package com.team30.game.game_mechanics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.Random;

/**
 * Separate Movement System from Infriltrators
 * Select random points in space station to move to
 */
public class NPC extends Movement {
    public NPC(TiledMapTileLayer room) {
        super(new Texture(("NPC.png")), 0, 0, 1, 1);
        this.moveRandomCell(room);
    }

    /**
     * Sets the velocity for the npc in a random direction
     * Then updates it's position
     *
     * @param deltaTime The time since last update
     * @param room      The room layer for collision detection
     */
    @Override
    public void updatePosition(float deltaTime, TiledMapTileLayer room) {
        Random rand = new Random();
        this.velocity.x += (((float) rand.nextInt((int) (this.VELOCITY_CHANGE * 100))) / 100) - (this.VELOCITY_CHANGE / 2);
        this.velocity.y += (((float) rand.nextInt((int) (this.VELOCITY_CHANGE * 100))) / 100) - (this.VELOCITY_CHANGE / 2);
        super.updatePosition(deltaTime, room);
    }
}
