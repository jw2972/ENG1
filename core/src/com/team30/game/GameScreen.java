package com.team30.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import static com.team30.game.GameContainer.SCREEN_HEIGHT;
import static com.team30.game.GameContainer.SCREEN_WIDTH;

public class GameScreen extends ScreenAdapter {

    private final GameContainer game;
    OrthographicCamera camera;

    public GameScreen(GameContainer game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.img, 0, 0);
        game.batch.end();
    }


    @Override
    public void dispose() {

    }
}
