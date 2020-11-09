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

    /**
     * Builds all systems from the map
     * Each object should have these properties:
     * name
     * x
     * y
     * width
     * height
     *
     * @param map The object layer containing systems
     */
    public SystemContainer(MapLayer map) {
        this.systems = new GameSystem[map.getObjects().getCount()];
        int index = 0;
        for (MapObject object : map.getObjects()) {
            MapProperties properties = object.getProperties();

            // Check all required fields are valid
            Object xPosObject = properties.get("x");
            Object yPosObject = properties.get("y");
            Object widthObject = properties.get("width");
            Object heightObject = properties.get("height");
            if (xPosObject == null || yPosObject == null || widthObject == null || heightObject == null) {
                //TODO Convert to error logging
                System.out.println("ERROR MapObject is missing required fields!!!");
                continue;
            }

            int x_pos = ((int) (float) xPosObject) / GameScreen.TILE_SIZE;
            int y_pos = ((int) (float) yPosObject) / GameScreen.TILE_SIZE;
            int width = ((int) (float) widthObject) / GameScreen.TILE_SIZE;
            int height = ((int) (float) heightObject) / GameScreen.TILE_SIZE;

            this.systems[index] = new GameSystem(object.getName(), x_pos, y_pos, width, height);
            index += 1;

        }
    }

    /**
     * @return The list of currently active systems
     */
    // TODO Only return "active" systems
    public GameSystem[] getActiveSystems() {
        return systems;
    }
}
