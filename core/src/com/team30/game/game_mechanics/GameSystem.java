package com.team30.game.game_mechanics;

import com.badlogic.gdx.graphics.g2d.Batch;

public class GameSystem extends Movement {
    public final String name;
    public int health;
    public boolean active;
    private float coolDown;


    public GameSystem(String name, int xPos, int yPos, int width, int height, int health) {
        super(null, xPos, yPos, width, height);
        this.name = name;
        this.MAX_VELOCITY = 0f;
        this.VELOCITY_CHANGE = 0f;
        this.health = health;
        this.active = true;
        this.coolDown = 0.0f;
    }

    /**
     * If the system is not on cooldown, applies the given amount of damage<br>     *
     * And returns the remaining health
     *
     * @param inflicted The amount of damage to be inflicted to the system
     * @return health The remaining health of the system
     */
    public int applyDamage(int inflicted) {
        if (this.coolDown > 0.0) {
            return this.health;
        }
        this.health = this.health - inflicted;
        System.out.println("Inflicting: " + inflicted + " to system: " + this.name + "now at: " + this.health);
        //TODO create sprite to warn Auber of system damage (play sound?)
        if (this.health <= 0) {
            this.active = false;
            this.health = 0;
        }
        this.coolDown = 10.0f;
        return health;
    }

    public float getCoolDown() {
        return this.coolDown;
    }

    /**
     * Removes the given amount of time from the cooldown
     *
     * @param delta The amount of time that has elapsed
     */
    public void updateCoolDown(float delta) {
        this.coolDown -= delta;
    }

    /**
     * Overridden and does nothing because the system images are currently in the base map layer (As of 14/11/2020)
     *
     * @param batch The sprite batch to render to
     */
    @Override
    public void draw(Batch batch) {

    }
}
