package com.team30.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.team30.game.GameContainer;
import com.team30.game.Recording.Action;
import com.team30.game.Recording.ActionType;
import com.team30.game.Recording.RecordingContainer;
import com.team30.game.game_mechanics.Auber;
import com.team30.game.game_mechanics.ID;

import java.util.LinkedList;

public class GameScreen extends ScreenAdapter implements InputProcessor {
    /**
     * The size of the tiles in pixels
     */
    private static final int TILE_SIZE = 64;
    private static final float SNAPSHOT_INTERVAL = 0.1f;
    private final Auber auber;

    /**
     * A map layer, representing valid tiles for characters to enter (Room Tiles)
     * Used for collision detection
     */
    private final TiledMapTileLayer room;
    /**
     * Used for selecting the view window for the player
     */
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    TiledMap tiledMap;
    GameContainer game;
    float timeSinceLastSnapshot;
    RecordingContainer recording;
    Boolean shouldRecord;
    Boolean isPlayback;

    GameScreen(GameContainer game, Boolean shouldRecord) {
        this.game = game;
        this.timeSinceLastSnapshot = 0;
        recording = new RecordingContainer();
        this.shouldRecord = shouldRecord;
        this.isPlayback = false;
        float width = GameContainer.SCREEN_WIDTH;
        float height = GameContainer.SCREEN_HEIGHT;

        tiledMap = new TmxMapLoader().load("Map.tmx");

        MapLayers layers = tiledMap.getLayers();
        room = (TiledMapTileLayer) layers.get("Rooms");
        // Builds the renderer and sets the grid to one "tile"
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, (float) 1 / TILE_SIZE);

        camera = new OrthographicCamera();
        // Give a view of 10 tiles, adjusting for aspect ratio
        camera.setToOrtho(false, (width / height) * 10, 10);
        camera.update();

        // Create and move Auber to centre room
        auber = new Auber(31, 32);
        Gdx.input.setInputProcessor(this);
    }

    GameScreen(GameContainer game, RecordingContainer recording) {
        this.game = game;
        this.timeSinceLastSnapshot = 0;
        this.recording = recording;
        this.shouldRecord = false;
        this.isPlayback = true;
        float width = GameContainer.SCREEN_WIDTH;
        float height = GameContainer.SCREEN_HEIGHT;

        tiledMap = new TmxMapLoader().load("Map.tmx");

        MapLayers layers = tiledMap.getLayers();
        room = (TiledMapTileLayer) layers.get("Rooms");
        // Builds the renderer and sets the grid to one "tile"
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, (float) 1 / TILE_SIZE);

        camera = new OrthographicCamera();
        // Give a view of 10 tiles, adjusting for aspect ratio
        camera.setToOrtho(false, (width / height) * 10, 10);
        camera.update();

        // Create and move Auber to centre room
        auber = new Auber(31, 32);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        // Set black background anc clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        timeSinceLastSnapshot += delta;
        if (isPlayback & timeSinceLastSnapshot > SNAPSHOT_INTERVAL) {
            timeSinceLastSnapshot -= SNAPSHOT_INTERVAL;
            LinkedList<Action> actions = recording.getSnapshot();
            for (Action action : actions) {
                switch (action.type) {
                    case AuberMove:
                        auber.setXVelocity(action.getXVelocity());
                        auber.setYVelocity(action.getYVelocity());

                        // In case the recording gets stuck
                        if (Math.abs(action.getXPosition() - auber.getXPosition()) > 2) {
                            auber.setXPosition(action.getXPosition());
                        }
                        if (Math.abs(action.getYPosition() - auber.getYPosition()) > 2) {
                            auber.setYPosition(action.getYPosition());
                        }
                        break;
                    case AuberCapture:
                        break;
                    case InfiltratorMove:
                        break;
                    case InfiltratorDamage:
                        break;
                    case NpcMove:
                        break;
                }
            }
        }
        auber.updateAuber(delta, room);

        // Set the camera to focus on Auber
        camera.position.x = auber.getXPosition();
        camera.position.y = auber.getYPosition();

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        Batch batch = tiledMapRenderer.getBatch();
        batch.begin();
        auber.draw(batch);
        batch.end();
        if (shouldRecord & timeSinceLastSnapshot > SNAPSHOT_INTERVAL) {
            recording.newSnapshot();
            System.out.println(auber.velocity);
            recording.addAction(new Action(new ID(), ActionType.AuberMove, auber.getXPosition(), auber.getYPosition(), auber.getXVelocity(), auber.getYVelocity(), null));
            timeSinceLastSnapshot = 0;
        }

    }


    /**
     * Key not being pressed, so set velocity to zero
     *
     * @param keycode
     * @return
     */
    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            if (shouldRecord) {
                recording.exportRecording();
            }
            game.setScreen(new MainMenu(game));
        }
        if (!isPlayback) {
            if (keycode == Input.Keys.LEFT) {
                auber.setXVelocity(0);
            }
            if (keycode == Input.Keys.RIGHT) {
                auber.setXVelocity(0);
            }
            if (keycode == Input.Keys.UP) {
                auber.setYVelocity(0);
            }
            if (keycode == Input.Keys.DOWN) {
                auber.setYVelocity(0);
            }
            if (keycode == Input.Keys.SPACE) {
                recording.exportRecording();
            }
        }
        return false;
    }

    /**
     * Key not being pressed, so set velocity to max
     *
     * @param keycode
     * @return
     */
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            auber.setXVelocity(-auber.VELOCITY_CHANGE);
        }
        if (keycode == Input.Keys.RIGHT) {
            auber.setXVelocity(auber.VELOCITY_CHANGE);
        }
        if (keycode == Input.Keys.UP) {
            auber.setYVelocity(auber.VELOCITY_CHANGE);
        }
        if (keycode == Input.Keys.DOWN) {
            auber.setYVelocity(-auber.VELOCITY_CHANGE);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}