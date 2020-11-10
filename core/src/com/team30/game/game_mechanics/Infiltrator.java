package com.team30.game.game_mechanics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;

public class Infiltrator extends Movement {
    /**
     * The list of movements to take
     */
    Queue<Movements> moves;

    /**
     * Spawns a new infiltrator at a random position
     *
     * @param room The map of valid tiles
     */
    public Infiltrator(TiledMapTileLayer room) {
        super(new Texture(("Infiltrator.png")), 32, 32, 1, 1);
        moves = new LinkedList<>();
        this.moveRandomCell(room);
        System.out.println("Spawned infiltrator at: " + this.position.toString());
    }


    /**
     * Returns the vector containing the direction to the closest active system
     *
     * @param position        The position to start from
     * @param systemContainer The container with positions of all active systems
     * @return Vector2 The direction of the target system
     */
    public static Vector2 getClosestSystem(Vector2 position, SystemContainer systemContainer) {
        float minDistance = Float.MAX_VALUE;
        Vector2 direction = new Vector2();
        for (GameSystem system : systemContainer.getActiveSystems()) {
            float currentDistance = position.dst(system.position);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                direction = system.position.cpy().sub(position);
            }
        }
        return direction;
    }

    /**
     * Converts a Movements enum to a vector
     *
     * @param move The enum to convert
     * @return A Vector2 of the movement
     */
    public static Vector2 getMovement(Movements move) {
        switch (move) {
            case LEFT:
                return new Vector2(-1, 0);
            case RIGHT:
                return new Vector2(1, 0);
            case UP:
                return new Vector2(0, 1);
            case DOWN:
                return new Vector2(0, -1);
        }
        return null;
    }

    /**
     * Executes the next move, on its path
     * If the path is empty, triggers damaging the system
     *
     * @param room    The map of valid room tiles
     * @param systems The location of all systems
     */
    public void moveInfiltrator(TiledMapTileLayer room, SystemContainer systems) {
        if (moves.isEmpty()) {
            Node node = getMove(room, systems);
            moves = node.exportPath();
        }
        this.velocity.x = 0;
        this.velocity.y = 0;
        if (moves.isEmpty()) {
            // TODO Start infiltrator damage

            System.out.println("DO DAMAGE");
        } else {
            Movements move = moves.remove();
            this.velocity.add(getMovement(move)).scl(this.MAX_VELOCITY);
        }
    }

    /**
     * Uses A* to find the closest system to the infiltrator
     *
     * @param room    The map of valid room tiles
     * @param systems The location of all systems
     * @return The node of the path to the closest system
     */
    private Node getMove(TiledMapTileLayer room, SystemContainer systems) {
        TreeMap<Float, Node> possibleMoves = new TreeMap<>();
        LinkedList<Vector2> visited = new LinkedList<>();

        Node currentNode = new Node(null, position, null, 0, 0);
        possibleMoves.put(currentNode.getHeuristic(), currentNode);

        while (!possibleMoves.isEmpty()) {
            currentNode = possibleMoves.remove(possibleMoves.firstKey());
            visited.add(currentNode.getPosition());
            // Checks if we are at a system
            Vector2 closestSystem = getClosestSystem(currentNode.getPosition(), systems);
            if (closestSystem.len() < 1) {
                return currentNode;
            }
            // Add the valid children to the queue
            for (Movements move : currentNode.getValidMoves(room)) {
                Vector2 position = currentNode.getPosition().cpy().add(getMovement(move));
                if (!visited.contains(position)) {
                    int cost = currentNode.getCost() + 1;
                    float heuristic = getClosestSystem(position, systems).len();
                    possibleMoves.put(heuristic + cost, new Node(currentNode, position, move, cost, heuristic));
                }
            }

        }
        return currentNode;

    }

    /**
     * Represents all possible movement directions
     */
    public enum Movements {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}

/**
 * Helper class for A* algorithm
 */
class Node {
    private final Node parent;
    private final Vector2 position;
    private final Infiltrator.Movements move;
    private final int cost;
    private final float heuristic;

    public Node(Node parent, Vector2 position, Infiltrator.Movements move, int cost, float heuristic) {
        this.parent = parent;
        this.position = position;
        this.move = move;
        this.cost = cost;
        this.heuristic = heuristic;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getCost() {
        return cost;
    }

    public float getHeuristic() {
        return heuristic;
    }

    /**
     * Checks the 4 neighbouring cells, to see if they are room tiles
     * Returning the valid ones
     *
     * @param room The map containing valid room tiles
     * @return The list of valid movements
     */
    public ArrayList<Infiltrator.Movements> getValidMoves(TiledMapTileLayer room) {
        ArrayList<Infiltrator.Movements> moves = new ArrayList<>();
        for (Infiltrator.Movements movement : Infiltrator.Movements.values()) {
            Vector2 newPosition = position.cpy().add(Infiltrator.getMovement(movement));
            if (room.getCell((int) newPosition.x, (int) newPosition.y) != null) {
                moves.add(movement);
            }
        }
        return moves;
    }

    /**
     * Backtracks up the tree, returning the path of movements to get to the target
     *
     * @return A queue of movements
     */
    public Queue<Infiltrator.Movements> exportPath() {
        // At the top of the tree
        if (move == null) {
            return new LinkedList<>();
        }
        Queue<Infiltrator.Movements> moves = this.parent.exportPath();
        moves.add(this.move);
        return moves;
    }
}


