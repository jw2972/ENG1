package com.team30.game;

import com.badlogic.gdx.math.Vector2;
import com.team30.game.game_mechanics.ID;

import java.util.LinkedList;

/**
 * Takes half second snapshots that can then be replayed back
 */
public class RecordingContainer {
    LinkedList<LinkedList<Action>> recordings;
    private int snapshotIndex;

    public RecordingContainer() {
        recordings = new LinkedList<>();
        snapshotIndex = -1;
    }

    public void newSnapshot() {
        this.recordings.add(new LinkedList<>());
        this.snapshotIndex += 1;
    }

    public void addAction(Action action) {
        this.recordings.get(snapshotIndex).add(action);
    }


    enum ActionType {
        AuberMove,
        AuberCapture,
        InfiltratorMove,
        InfiltratorDamage,
        NpcMove,
    }

    class Action {
        private final int snapshotIndex;
        private final ID id;
        private final ID target;
        private final ActionType type;
        private final Vector2 currentPosition;
        private final Vector2 velocity;

        public Action(int snapshotIndex, ID id, ActionType type, Vector2 currentPosition, Vector2 velocity, ID target) {
            this.snapshotIndex = snapshotIndex;
            this.id = id;
            this.type = type;
            this.currentPosition = currentPosition;
            this.velocity = velocity;
            this.target = target;

        }

    }
}