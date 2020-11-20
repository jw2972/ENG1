package com.team30.game.game_mechanics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.me.move.Invisible;

import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;

import static com.team30.game.game_mechanics.Node.getMovement;

public class Infiltrator extends Entity {
    /**
     * The amount of damage applied in "one" attack
     */
    public static final int DAMAGE_DEALT = 50;
    /**
     * The time for coolDown special ability
     */
	public int coolDown;
	/**
     * The time for Auber hallucination
     */
	public int hallucinationTime;
    // TODO Convert to an ID
    public String name;
    /**
     * The list of movements to take
     */
    Queue<Node.Movements> moves;
    private ID targetSystem;
    
    /**
     * Spawns a new infiltrator at a random position
     *
     * @param roomTiles The map of valid tiles
     */
    public Infiltrator(TiledMapTileLayer roomTiles, String name) {
        super(new ID(EntityType.Infiltrator), new Texture(("Infiltrator.png")), roomTiles, 1, 1);
        this.name = name;
        this.targetSystem = null;
        moves = new LinkedList<>();
        System.out.println("Spawned infiltrator:" + this.name + " at: " + this.position.toString());
    }

    /**
     * Spawns a new NPC with the given ID, and at the given position
     *
     * @param id        The ID of the NPC
     * @param xPosition The x coordinate to spawn on
     * @param yPosition The y coordinate to spawn on
     */
    // TODO Check for clashing ID'S?
    public Infiltrator(ID id, int xPosition, int yPosition) {
        super(id, new Texture(("Infiltrator.png")), xPosition, yPosition, 1, 1);
        this.name = null;
        this.targetSystem = null;
        moves = new LinkedList<>();
        System.out.println("Spawned infiltrator:" + this.name + " at: " + this.position.toString());
    }

    /**
     * Spawns a new infiltrator at a random position and has hallucination ability
     *
     * @param roomTiles The map of valid tiles
     * @param coolDown  The time for coolDown special ability
     * @param hallucinationTime The time for Auber hallucination
     */
    public Infiltrator(TiledMapTileLayer roomTiles, String name, int coolDown, int hallucinationTime) {
        super(new ID(EntityType.Infiltrator), new Texture(("Infiltrator.png")), roomTiles, 1, 1);
        if (coolDown > hallucinationTime){
        	this.name = name;
        	this.targetSystem = null;
        	this.coolDown = coolDown;
        	this.hallucinationTime = hallucinationTime;
        	moves = new LinkedList<>();
        	System.out.println("Spawned infiltrator:" + this.name + " at: " + this.position.toString());
        }
        else {
        	throw new Exception("Time for coolDown should longer than hallucinationTime");
        }
    }
    
    /**
     * Spawns a new infiltrator at a random position and has faster speed
     *
     * @param roomTiles The map of valid tiles
     * @param VELOCITY_CHANGE Higher velocity change for special infiltrator
     * @param MAX_VELOCITY Higher Max velocity for special infiltrator
     */
    public Infiltrator(TiledMapTileLayer roomTiles, String name, float VELOCITY_CHANGE, float MAX_VELOCITY) {
        super(new ID(EntityType.Infiltrator), new Texture(("Infiltrator.png")), roomTiles, 1, 1);
        this.name = name;
        this.targetSystem = null;
        this.VELOCITY_CHANGE = VELOCITY_CHANGE;
        this.MAX_VELOCITY = MAX_VELOCITY;
        moves = new LinkedList<>();
        System.out.println("Spawned infiltrator:" + this.name + " at: " + this.position.toString());
    }
    
    
    /**
     * Spawns a new infiltrator at a random position and has invisible ability creation
     *
     * @param roomTiles The map of valid tiles
     * @param invisible To create a invisible ability
     * @param delay The time to change invisible diagram.( It can be used as cooling down time.)
     */
    public Infiltrator(TiledMapTileLayer roomTiles, String name,Invisible invisible, float delay) {
        super(new ID(EntityType.Infiltrator), new Texture(("Infiltrator.png")), roomTiles, 1, 1);
        invisible.show(delay);
        this.name = null;
        this.targetSystem = null;
        moves = new LinkedList<>();
        System.out.println("Spawned infiltrator:" + this.name + " at: " + this.position.toString());
    }

    /**
     * Returns the vector containing the direction to the closest attackable system
     *
     * @param position        The position to start from
     * @param systemContainer The container with positions of all active systems
     * @return Vector2 The direction of the target system
     */
    public static Vector2 getClosestSystemVect(Vector2 position, SystemContainer systemContainer) {
        float minDistance = Float.MAX_VALUE;
        Vector2 direction = new Vector2();
        for (Integer id : systemContainer.getAttackableSystems()) {
            StationSystem system = (StationSystem) systemContainer.getEntityByInt(id);
            float currentDistance = position.dst(system.position);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                direction = system.position.cpy().sub(position);
            }
        }
        return direction;
    }

    /**
     * Returns the closest system that is active and not on cool down
     *
     * @param position        The position to start from
     * @param systemContainer The container with positions of all active systems
     *                        //TODO Find out how to properly document nullable
     * @return ID    The ID of the target system (Could be null if no systems are found!)
     */
    public static ID getClosestSystem(Vector2 position, SystemContainer systemContainer) {
        float minDistance = Float.MAX_VALUE;
        Integer closestSystem = null;
        for (Integer id : systemContainer.getAttackableSystems()) {
            float currentDistance = position.dst(systemContainer.getEntityPosition(systemContainer.integerIdLookup(id)));
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                closestSystem = id;
            }
        }
        return systemContainer.integerIdLookup(closestSystem);
    }


    /**
     * Executes the next move, on its path
     * If the path is empty, triggers damaging the system
     *
     * @param room    The map of valid room tiles
     * @param systems The location of all systems
     * @return True if it can start damaging a system
     */
    public boolean moveInfiltrator(TiledMapTileLayer room, SystemContainer systems) {
        if (moves.isEmpty() && targetSystem == null) {
            Node node = getMove(room, systems);
            moves = node.exportPath();
        }
        this.velocity.x = 0;
        this.velocity.y = 0;
        if (targetSystem != null) {
            systems.applyDamage(this.id, targetSystem, DAMAGE_DEALT);
            if (!systems.getActiveSystems().contains(targetSystem.ID)) {
                targetSystem = null;
                //TODO look at moving the infiltrator away from just attacked system to avoid detection and make game harder
            }
        } else if (!moves.isEmpty()) {
            Node.Movements move = moves.remove();
            this.velocity.add(getMovement(move)).scl(this.MAX_VELOCITY);
            // We have reached the target system
            if (moves.isEmpty()) {
                targetSystem = getClosestSystem(position, systems);
                System.out.println("At target system: " + targetSystem);
            }
        }
        return false;
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
            Vector2 closestSystem = getClosestSystemVect(currentNode.getPosition(), systems);
            if (closestSystem.len() < 1) {
                return currentNode;
            }
            // Add the valid children to the queue
            for (Node.Movements move : currentNode.getValidMoves(room)) {
                Vector2 position = currentNode.getPosition().cpy().add(getMovement(move));
                if (!visited.contains(position)) {
                    int cost = currentNode.getCost() + 1;
                    float heuristic = getClosestSystemVect(position, systems).len();
                    possibleMoves.put(heuristic + cost, new Node(currentNode, position, move, cost, heuristic));
                }
            }

        }
        return currentNode;

    }

}
