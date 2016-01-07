package com.defend.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;


public class rules implements Screen {

    private Defend game;
    private OrthographicCamera camera;
    private Texture menu;
    private Texture back;
    private boolean goBack;
    private ImageButton backButton;
    private Vector3 touchPos;

    public rules(final Defend game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        touchPos = new Vector3(0, 0, 0);
        menu = new Texture("rulesMenu.png");
        back = new Texture("back.png");

        goBack = false;
        backButton = new ImageButton(back, Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * -0.1f, Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.4f);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.6f, 0.8f, 1.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(menu,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        backButton.update(game.batch);
        game.batch.end();
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);

            camera.unproject(touchPos);

            if (backButton.checkIfClicked(touchPos.x, touchPos.y)) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }

        }

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown

    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }
}