package com.team30.game.game_mechanics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;

/**
 * Wrapper class for all infiltrators, and handles the movement and rendering of them
 */
public class InfiltratorContainer {
    /**
     * The maximum number of inftriltrators to spawn
     */
    private static final int MAX_INFILTRATORS = 10;
    /**
     * The infriltrators that are currently "alive" on the map
     */
    private final ArrayList<Infiltrator> currentInfiltrators;
    /**
     * The number of infriltrators that have been spawned so far
     */
    private int spawnedInfriltrators;
    /**
     * The time since an infriltrator was last spawned
     */
    private float timeSinceLastSpawn;
    /**
     * The time since the movements of the infriltrators were last updated
     */
    private float timeSinceLastUpdate;

    public InfiltratorContainer() {
        this.spawnedInfriltrators = 0;
        this.timeSinceLastSpawn = 0;
        this.timeSinceLastUpdate = 0;
        this.currentInfiltrators = new ArrayList<>();
    }

    public void updateAndDraw(float deltaTime, TiledMapTileLayer room, SystemContainer systemContainer, Batch batch) {
        timeSinceLastSpawn += deltaTime;
        timeSinceLastUpdate += deltaTime;
        if (timeSinceLastSpawn > 10) {
            spawnInfriltrator(room);
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
    public void spawnInfriltrator(TiledMapTileLayer room) {
        if (spawnedInfriltrators < MAX_INFILTRATORS) {
            spawnedInfriltrators += 1;
            timeSinceLastSpawn = 0;
            currentInfiltrators.add(new Infiltrator(room));
        }

    }
}
