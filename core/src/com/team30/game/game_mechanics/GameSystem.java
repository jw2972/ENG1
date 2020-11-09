package com.team30.game.game_mechanics;

import com.badlogic.gdx.graphics.g2d.Batch;

public class GameSystem extends Movement {
    private final String name;

    public GameSystem(String name, int x_pos, int y_pos, int width, int height) {
        super(null, x_pos, y_pos, width, height);
        this.name = name;
        this.MAX_VELOCITY = 0f;
        this.VELOCITY_CHANGE = 0f;
    }

    /**
     * Don't need to draw as should be in base layer of map
     *
     * @param batch The sprite batch to render to
     */
    @Override
    public void draw(Batch batch) {
    }
}
