package com.defend.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenuScreen implements Screen {

    private Defend game;
    private OrthographicCamera camera;
    private Texture start;
    private Texture instr;
    private Texture highScore;
    private Texture settings;
    private Vector3 touchPos;
    private ImageButton startB;
    private ImageButton instrB;
    private ImageButton highScoreB;
    private ImageButton settingsB;
    private boolean isBlack;
    private Preferences prefs;
    private Texture background;



    public MainMenuScreen(final Defend game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        touchPos = new Vector3(0, 0, 0);
        start = new Texture("play.png");
        instr = new Texture("rules.png");
        highScore = new Texture("highscore.png");
        settings = new Texture("settings.png");
        background = new Texture("background.png");


        startB = new ImageButton(start, Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() * 0.54f, Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.4f);
        instrB = new ImageButton(instr, Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() * 0.36f, Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.4f);

        highScoreB = new ImageButton(highScore, Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() * 0.18f, Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.4f);
        settingsB = new ImageButton(settings, Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() * 0.0f, Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.4f);

        prefs = Gdx.app.getPreferences("Preferences");
        isBlack = prefs.getBoolean("black",false);
    }

    @Override
    public void render(float delta) {

        if(!isBlack) {
            Gdx.gl.glClearColor(0.6f, 0.8f, 1.0f, 1);
        }
        else Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        startB.update(game.batch);
        instrB.update(game.batch);
        highScoreB.update(game.batch);
        settingsB.update(game.batch);
        game.batch.end();
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);

            camera.unproject(touchPos);


        }
        else
        {
            if (startB.checkIfClicked(touchPos.x, touchPos.y)) {
                game.setScreen(new defendCastle(game));
                dispose();
            }
            if (highScoreB.checkIfClicked(touchPos.x, touchPos.y)) {
                game.setScreen(new highScore(game));
                dispose();
            }
            if (instrB.checkIfClicked(touchPos.x, touchPos.y)) {
                game.setScreen(new rules(game));
                dispose();
            }
            if(settingsB.checkIfClicked(touchPos.x, touchPos.y))
            {
                game.setScreen(new settings(game));
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