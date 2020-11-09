package com.team30.game.game_mechanics;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.team30.game.screen.GameScreen;

/**
 * Handles all systems, and rendering of them
 */
public class SystemContainer {
    public GameSystem[] systems;

    public SystemContainer(MapLayer map) {
        //this.systems=new GameSystem[map.getObjects().getCount()];
        this.systems = new GameSystem[4];
        int index = 0;
        for (MapObject object : map.getObjects()) {

            MapProperties properties = object.getProperties();
            System.out.println(object.getName());
            if (object.getName().equals("Teleportation")) {
                int x_pos_property = (int) (float) properties.get("x");
                int x_pos = ((int) (float) properties.get("x")) / GameScreen.TILE_SIZE;
                int y_pos = ((int) (float) properties.get("y")) / GameScreen.TILE_SIZE;
                int width = ((int) (float) properties.get("width")) / GameScreen.TILE_SIZE;
                int height = ((int) (float) properties.get("height")) / GameScreen.TILE_SIZE;
                this.systems[index] = new GameSystem(object.getName(), x_pos, y_pos, width, height);
                index += 1;
            }
        }
    }
}
