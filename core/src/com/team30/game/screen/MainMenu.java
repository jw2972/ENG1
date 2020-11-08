package com.team30.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.team30.game.GameContainer;

import static com.team30.game.GameContainer.SCREEN_HEIGHT;
import static com.team30.game.GameContainer.SCREEN_WIDTH;

public class MainMenu extends ScreenAdapter {
    private final GameContainer game;
    /**
     * The input handler for the buttons
     */
    private final Stage stage;
    OrthographicCamera camera;

    public MainMenu(final GameContainer game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage = new Stage();
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = game.font;

        TextButton start = new TextButton("Start", style);
        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Start Game");
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        TextButton settings = new TextButton("Settings", style);
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Settings");
                game.setScreen(new Settings(game));
                dispose();
            }
        });
        TextButton quit = new TextButton("Quit", style);
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                Gdx.app.exit();
            }
        });
        table.add(start).space(16).row();
        table.add(settings).space(16).row();
        table.add(quit).space(16).row();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(game.teamLogoImg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        game.batch.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
