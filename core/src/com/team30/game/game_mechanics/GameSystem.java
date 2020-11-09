package com.team30.game.game_mechanics;

import com.badlogic.gdx.graphics.Texture;

public class GameSystem extends Movement {
    public GameSystem(String name, int x_pos, int y_pos, int width, int height) {
        super(new Texture("Infriltrator.png"), x_pos, y_pos, width, height);
        this.MAX_VELOCITY = 0f;
        this.VELOCITY_CHANGE = 0f;
    }
}
