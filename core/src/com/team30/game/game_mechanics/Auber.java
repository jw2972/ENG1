package com.team30.game.game_mechanics;

import com.badlogic.gdx.graphics.Texture;

public class Auber extends Movement {
    public Auber(int x, int y) {
        super(new Texture("Auber.png"), x, y,1,1);
        this.VELOCITY_CHANGE=2f;
    }

}
