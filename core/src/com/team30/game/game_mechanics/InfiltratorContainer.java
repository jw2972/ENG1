package com.team30.game.game_mechanics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;

/**
 * Wrapper class for all infiltrators, and handles the movement and rendering of them
 */
public class InfiltratorContainer {
    /**
     * The maximum number of infiltrators to spawn
     */
    private static final int MAX_INFILTRATORS = 10;
    /**
     * The infiltrators that are currently "alive" on the map
     */
    private final ArrayList<Infiltrator> currentInfiltrators;
    /**
     * The number of infiltrators that have been spawned so far
     */
    private int spawnedInfiltrators;
    /**
     * The time since an infiltrator was last spawned
     */
    private float timeSinceLastSpawn;
    /**
     * The time since the movements of the infiltrators were last updated
     */
    private float timeSinceLastUpdate;

    public InfiltratorContainer() {
        this.spawnedInfiltrators = 0;
        this.timeSinceLastSpawn = 0;
        this.timeSinceLastUpdate = 0;
        this.currentInfiltrators = new ArrayList<>();
    }

    public void updateAndDraw(float deltaTime, TiledMapTileLayer room, SystemContainer systemContainer, Batch batch) {
        timeSinceLastSpawn += deltaTime;
        timeSinceLastUpdate += deltaTime;
        if (timeSinceLastSpawn > 10) {
            spawnInfiltrator(room);
        }

        for (Infiltrator infiltrator : currentInfiltrators) {
            if (timeSinceLastUpdate > 0.2) {
                infiltrator.moveInfiltrator(room, systemContainer);
                timeSinceLastUpdate = 0;
            }
            infiltrator.updatePosition(deltaTime, room);
            infiltrator.draw(batch);

        }
    }

    /**
     * Attempts to spawn a new infriltrator
     *
     * @param room The map of valid room tiles
     */
    public void spawnInfiltrator(TiledMapTileLayer room) {
        if (spawnedInfiltrators < MAX_INFILTRATORS) {
            spawnedInfiltrators += 1;
            timeSinceLastSpawn = 0;
            currentInfiltrators.add(new Infiltrator(room, "inf_" + String.valueOf(this.spawnedInfiltrators)));
        }
    }
}
