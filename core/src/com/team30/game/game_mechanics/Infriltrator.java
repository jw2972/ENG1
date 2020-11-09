package com.team30.game.game_mechanics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class Infriltrator extends Movement {
    Queue<Movements> moves;

    public Infriltrator(TiledMapTileLayer room) {
        super(new Texture(("Infriltrator.png")), 0, 0, 1, 1);
        moves = new LinkedList<>();
        this.moveRandomCell(room);
        System.out.println("Spawned infriltrator at: " + this.position.toString());
    }

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

    public static Vector2 getClosestSystem(Vector2 position, SystemContainer systemContainer) {
        float minDistance = Float.MAX_VALUE;
        Vector2 direction = new Vector2();
        GameSystem targetSystem = null;
        for (GameSystem system : systemContainer.systems) {
            float currentDistance = position.dst(system.position);
            if (targetSystem == null) {
                minDistance = currentDistance;
                targetSystem = system;
                direction = system.position.cpy().sub(position);
            }
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                targetSystem = system;
                direction = system.position.cpy().sub(position);
            }
        }
        return direction;
    }

    /**
     * Sets the velocity for the Infriltrator in a random direction
     * Then updates it's position
     *
     * @param deltaTime The time since last update
     * @param room      The room layer for collision detection
     * @return
     */

    public Movements moveInfriltrator(TiledMapTileLayer room, SystemContainer systems, Vector2 auberPosition) {
        if (moves.isEmpty()) {
            Node node = getMove(room, systems, auberPosition);
            moves = node.exportPath();
        }
        this.velocity.x = 0;
        this.velocity.y = 0;
        Movements move = moves.remove();
        this.velocity.add(getMovement(move)).scl(this.MAX_VELOCITY);
        return move;
    }

    private Node getMove(TiledMapTileLayer room, SystemContainer systems, Vector2 auberPosition) {
        Node currentNode = new Node(position, 0, 0, null, null, 0);
        Comparator<Node> comparator = new NodeQueue();
        TreeMap<Float, Node> possibleMoves = new TreeMap<Float, Node>();
        LinkedList<Vector2> visited = new LinkedList<>();
        possibleMoves.put(currentNode.getHeuristic(), currentNode);
        while (possibleMoves.size() > 0) {
            currentNode = possibleMoves.remove(possibleMoves.firstKey());
            visited.add(currentNode.getPosition());
            Vector2 closestSystem = getClosestSystem(currentNode.getPosition(), systems);
            if (closestSystem.len() < 2) {
                return currentNode;
            }
            for (Movements move : currentNode.getValidMoves(room)) {
                int cost = currentNode.getCost() + 1;
                Vector2 position = currentNode.getPosition().cpy().add(getMovement(move));
                if (!visited.contains(position)) {
                    float heuristic = getClosestSystem(position, systems).len();//+ (100 / position.dst(auberPosition));
                    if (possibleMoves.size() <= 100 || heuristic < possibleMoves.lastEntry().getValue().getHeuristic()) {
                        possibleMoves.put(heuristic, new Node(position, cost, heuristic, move, currentNode, currentNode.depth + 1));
                    }
                }
            }

        }
        System.out.println("Failed to find move!");
        return currentNode;

    }

    public enum Movements {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}

class NodeQueue implements Comparator<Node> {
    @Override
    public int compare(Node node, Node t1) {
        if (node.getCost() + node.getHeuristic() < t1.getCost() + t1.getHeuristic()) {
            return -1;
        }
        if (node.getCost() + node.getHeuristic() > t1.getCost() + t1.getHeuristic()) {
            return 1;
        }
        return 0;
    }
}

class Node {

    final int depth;
    private final Vector2 position;
    private final int cost;
    private final Infriltrator.Movements move;
    private final Node parent;
    private final float heuristic;

    public Node(Vector2 position, int cost, float heuristic, Infriltrator.Movements move, Node parent, int depth) {
        this.position = position;
        this.cost = cost;
        this.heuristic = heuristic;
        this.move = move;
        this.parent = parent;
        this.depth = depth;
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

    public ArrayList<Infriltrator.Movements> getValidMoves(TiledMapTileLayer room) {
        ArrayList<Infriltrator.Movements> moves = new ArrayList<>();
        for (Infriltrator.Movements movement : Infriltrator.Movements.values()) {
            if (position.cpy().add(Infriltrator.getMovement(movement)) != null) {
                moves.add(movement);
            }
        }
        return moves;
    }

    public Queue<Infriltrator.Movements> exportPath() {
        if (move == null) {
            return new LinkedList<>();
        }
        Queue<Infriltrator.Movements> moves = this.parent.exportPath();
        moves.add(this.move);

        return moves;
    }
}


