package com.team30.game.game_mechanics;

import com.badlogic.gdx.graphics.g2d.Batch;

public class GameSystem extends Movement {
    public final String name;
    public int health;
    public boolean active;
    private float coolDown;


    public GameSystem(String name, int x_pos, int y_pos, int width, int height, int health) {
        super(null, x_pos, y_pos, width, height);
        this.name = name;
        this.MAX_VELOCITY = 0f;
        this.VELOCITY_CHANGE = 0f;
        this.health = health;
        this.active = true;
        this.coolDown = 0.0f;
    }

    /**
     * Allows infiltrators to damage systems and sets the system on cool down
     *
     * @param inflicted the damage to be inflicted to the system
     * @return health the remaining health of the system
     */
    public int damaged(int inflicted) {
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

    public float getCoolDown() {return this.coolDown;}

    public void updateCoolDown(float delta) {this.coolDown -= delta;}

    /**
     * Don't need to draw as should be in base layer of map
     *
     * @param batch The sprite batch to render to
     */
    @Override
    public void draw(Batch batch) {
        batch.draw(texture, getXPosition(), getYPosition(), width, height);
    }
}
