package com.team30.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends ScreenAdapter implements InputProcessor {
    /**
     * The size of the tiles in pixels
     */
    private static final int TILE_SIZE = 64;
    private final Texture auberTexture;
    OrthographicCamera camera;
    private final Auber auber;
    GameContainer game;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;

    // TODO Move to separate file

    GameScreen(GameContainer game) {
        this.game = game;
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        auberTexture = new Texture("Auber.png");
        tiledMap = new TmxMapLoader().load("Map.tmx");
        // Builds the renderer and sets the grid to one "tile"
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, (float) 1 / TILE_SIZE);

        camera = new OrthographicCamera();
        // Give a view of 10 tiles, adjusting for resolution
        camera.setToOrtho(false, (width / height) * 10, 10);
        camera.update();

        auber = new Auber();
        // Move Auber to centre room
        auber.position.set(32, 31);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        // Set black background anc clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateAuber(delta);
        // Set the camera to focus on auber
        camera.position.x = auber.position.x;
        camera.position.y = auber.position.y;

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        Batch batch = tiledMapRenderer.getBatch();
        batch.begin();
        // Draw the Auber
        batch.draw(auberTexture, auber.position.x, auber.position.y, 1, 1);
        batch.end();
    }

    /**
     * Calculates the updated position of the Auber, since the last update
     *
     * @param deltaTime The time since last update
     */
    private void updateAuber(float deltaTime) {
        if (deltaTime == 0) return;

        if (deltaTime > 0.1f) {
            deltaTime = 0.1f;
        }
        // Limit the maximum speed
        auber.velocity.clamp(-Auber.MAX_VELOCITY, Auber.MAX_VELOCITY);
        // Scale for time passed
        auber.velocity.scl(deltaTime);
        auber.position.add(auber.velocity);
        auber.velocity.scl((1 / deltaTime));
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
            auber.velocity.x = 0;
        }
        if (keycode == Input.Keys.RIGHT) {
            auber.velocity.x = 0;
        }
        if (keycode == Input.Keys.UP) {
            auber.velocity.y = 0;
        }
        if (keycode == Input.Keys.DOWN) {
            auber.velocity.y = 0;
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
            auber.velocity.x = -Auber.VELOCITY_CHANGE;
        }
        if (keycode == Input.Keys.RIGHT) {
            auber.velocity.x = Auber.VELOCITY_CHANGE;
        }
        if (keycode == Input.Keys.UP) {
            auber.velocity.y = Auber.VELOCITY_CHANGE;
        }
        if (keycode == Input.Keys.DOWN) {
            auber.velocity.y = -Auber.VELOCITY_CHANGE;
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

    /**
     * Handles the positing and movement of the Auber character
     */
    static class Auber {
        static float VELOCITY_CHANGE = 4f;
        static float MAX_VELOCITY = 5f;
        final Vector2 position = new Vector2();
        final Vector2 velocity = new Vector2();
    }
}