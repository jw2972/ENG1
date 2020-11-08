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
import com.team30.game.game_mechanics.Auber;

public class GameScreen extends ScreenAdapter implements InputProcessor {
    /**
     * The size of the tiles in pixels
     */
    private static final int TILE_SIZE = 64;
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

    GameScreen(GameContainer game) {
        this.game = game;
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

    }


    /**
     * Key not being pressed, so set velocity to zero
     *
     * @param keycode
     * @return
     */
    @Override
    public boolean keyUp(int keycode) {
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