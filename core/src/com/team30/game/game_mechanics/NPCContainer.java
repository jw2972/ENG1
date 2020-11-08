package com.team30.game.game_mechanics;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Handles all concurrent npcs, and rendering of them
 */
public class NPCContainer {
    private static final int NPC_AMOUNT = 50;
    private final NPC[] npcs;

    public NPCContainer(TiledMapTileLayer room) {
        npcs = new NPC[NPC_AMOUNT];
        for (int index = 0; index < NPC_AMOUNT; index++) {
            npcs[index] = new NPC(room);
        }
    }

    /**
     * Updates the position of all npcs, and renders them
     *
     * @param deltaTime Time since last update
     * @param room      The map of valid room cells
     * @param batch     The render batch
     */
    public void update(float deltaTime, TiledMapTileLayer room, Batch batch) {
        for (NPC npc : npcs) {
            npc.updatePosition(deltaTime, room);
            npc.draw(batch);
        }
    }

}
