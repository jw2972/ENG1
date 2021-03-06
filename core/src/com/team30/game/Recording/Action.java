package com.team30.game.Recording;

import com.team30.game.game_mechanics.ID;

/**
 * Represents one action for a singular entity
 */
public class Action {
    private final ActionType type;
    private final ID id;
    private final ID target;
    // Can't use vectors because it borks GSON
    private final float xPosition;
    private final float yPosition;
    private final float xVelocity;
    private final float yVelocity;

    /**
     * Stores a game "action", i.e. something important that happened
     *
     * @param id        The ID of the entity
     * @param type      The type of action that took place
     * @param xPosition The current x position of the entity
     * @param yPosition The current y position of the entity
     * @param xVelocity The current x velocity (direction of travel) for the entity
     * @param yVelocity The current y velocity (direction of travel) for the entity
     * @param target    The id of an entity did something to another entity (
     */
    public Action(ID id, ActionType type, float xPosition, float yPosition, float xVelocity, float yVelocity, ID target) {
        this.id = id;
        this.type = type;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.target = target;

    }

    public ActionType getActionType() {
        return type;
    }

    public float getXVelocity() {
        return xVelocity;
    }

    public float getYVelocity() {
        return yVelocity;
    }

    public float getXPosition() {
        return xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

}
