package com.team30.game.Recording;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Stores all game "snapshot" for playback
 * Each snapshot is stored as a list of actions that are executed in that interval
 */
public class RecordingContainer {
    private LinkedList<LinkedList<Action>> recordings;
    private int snapshotIndex;

    /**
     * Creates a new blank recording
     */
    public RecordingContainer() {
        recordings = new LinkedList<>();
        snapshotIndex = -1;
    }

    /**
     * Loads an existing recording from the given file
     *
     * @param filename
     */
    public RecordingContainer(String filename) {
        Gson gson = new Gson();
        try {
            RecordingContainer container = gson.fromJson(new FileReader(filename), RecordingContainer.class);
            this.recordings = container.recordings;
            System.out.println(recordings);
            this.snapshotIndex = 0;
        } catch (FileNotFoundException e) {
            // TODO Proper error handling
            e.printStackTrace();
        }
    }

    /**
     * Creates a new blank snapshot list
     */
    public void newSnapshot() {
        LinkedList<Action> actions = new LinkedList<>();
        this.recordings.add(actions);
        this.snapshotIndex += 1;
    }

    /**
     * Returns the previous snapshot
     *
     * @return The list of actions for that snapshot
     */
    public LinkedList<Action> getSnapshot() {
        this.snapshotIndex += 1;
        if (snapshotIndex < recordings.size()) {
            return this.recordings.get(snapshotIndex);
        }
        return new LinkedList<>();
    }

    /**
     * Adds a new action to the currently selected snapshot
     *
     * @param action The action to save
     */
    public void addAction(Action action) {
        this.recordings.get(snapshotIndex).add(action);
    }

    /**
     * Adds all the given actions to the currently selected snapshot
     *
     * @param action The action to save
     */
    public void addAllAction(List<Action> action) {
        this.recordings.get(snapshotIndex).addAll(action);
    }

    /**
     * Exports this recording instance to a file
     */
    public void exportRecording() {
        Gson gson = new Gson();
        String json = null;
        try {
            // TODO Change this name
            FileWriter writer = new FileWriter("Test.json");
            gson.toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            //TODO Error Handling
            e.printStackTrace();
        }
        System.out.println(json);

    }
}

