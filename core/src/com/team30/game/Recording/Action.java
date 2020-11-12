package com.team30.game.Recording;

import com.team30.game.game_mechanics.ID;

public class Action {
    public final ActionType type;
    private final ID id;
    private final ID target;
    private final float xPosition;
    private final float yPosition;
    //private final Vector2 velocity;
    private final float xVelocity;
    private final float yVelocity;

    /**
     * Stores a game "action", i.e. something important that happened
     *
     * @param id       The ID of the entity
     * @param type     The type of action that took place
     * @param position The current position of the entity
     * @param velocity The current velocity (direction of travel) for the entity
     * @param target   The id of an entity did something to another entity (
     */
    public Action(ID id, ActionType type, float xPosition, float yPosition, float xVelocity, float yVelocity, ID target) {
        this.id = id;
        this.type = type;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        //this.velocity = velocity;
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
