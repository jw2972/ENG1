package com.team30.game.game_mechanics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Handles all concurrent infriltators, and rendering of them
 */
public class InfriltatorContainer {
    private static final int MAX_INFILTRATORS = 10;
    private final int spawnedInfriltrators;
    private final ArrayList<Infriltrator> currentInfriltrators;
    private float timeSinceLastSpawn;
    private float timeSinceLastUpdate;

    public InfriltatorContainer() {
        this.spawnedInfriltrators = 0;
        this.timeSinceLastSpawn = 0;
        this.timeSinceLastUpdate = 0;
        this.currentInfriltrators = new ArrayList<>();
    }

    public void updateAndDraw(float deltaTime, Vector2 auberPosition, TiledMapTileLayer room, SystemContainer systemContainer, Batch batch) {
        timeSinceLastSpawn += deltaTime;
        timeSinceLastUpdate += deltaTime;
        if (timeSinceLastSpawn > 1 && currentInfriltrators.size() == 0) {
            currentInfriltrators.add(new Infriltrator(room));
            timeSinceLastSpawn = 0;
        }

        for (Infriltrator infriltrator : currentInfriltrators) {
            if (timeSinceLastUpdate > 0.5) {
                Infriltrator.Movements move = infriltrator.moveInfriltrator(room, systemContainer, auberPosition);
                timeSinceLastUpdate = 0;
            }
            infriltrator.draw(batch);
            infriltrator.updatePosition(deltaTime, room);

        }
    }
}
