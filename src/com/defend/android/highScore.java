package com.defend.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class highScore implements Screen {

    private Defend game;
    private OrthographicCamera camera;
    private BitmapFont gameScore;
    private BitmapFont second;
    private BitmapFont third;
    private BitmapFont fourth;
    private BitmapFont fifth;
    private BitmapFont sixth;
    private Texture back;
    private ImageButton backButton;

    private BitmapFont header;
    private Preferences prefs;
    private int highScore;
    private int secondHighest;
    private int thirdHighest;
    private int fourthHighest;
    private int fifthHighest;
    private int sixthHighest;
    private boolean isBlack;
    private Vector3 touchPos;


    public highScore(final Defend game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //camera.setToOrtho(false, 100,100);
        touchPos = new Vector3(0, 0, 0);
        gameScore = new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));
        second = new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));
        third = new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));
        fourth = new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));
        fifth = new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));
        sixth = new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));

        back = new Texture("back.png");



        header = new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));
        prefs = Gdx.app.getPreferences("Preferences");
        highScore = prefs.getInteger("score1", 0);
        secondHighest = prefs.getInteger("score2", 0);
        thirdHighest = prefs.getInteger("score3", 0);
        fourthHighest = prefs.getInteger("score4", 0);
        fifthHighest = prefs.getInteger("score5", 0);
        sixthHighest = prefs.getInteger("score6", 0);

        isBlack = prefs.getBoolean("black",false);

        backButton = new ImageButton(back, Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() * -0.1f, Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.4f);
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
        //label.draw(game.batch, 55f);
        backButton.update(game.batch);


        header.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        header.setScale(Gdx.graphics.getWidth()*0.001f);
        String fontText = "High Scores";
        float fontWidth = header.getBounds(fontText).width;
        header.draw(game.batch,fontText, Gdx.graphics.getWidth()*0.5f - fontWidth/2, Gdx.graphics.getHeight() * 0.9f);

        gameScore.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        gameScore.setScale(Gdx.graphics.getWidth()*0.00065f);
        gameScore.draw(game.batch, "1. "+highScore, Gdx.graphics.getWidth()*0.25f, Gdx.graphics.getHeight() * 0.65f);

        second.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        second.setScale(Gdx.graphics.getWidth()*0.00065f);
        second.draw(game.batch, "2. "+secondHighest, Gdx.graphics.getWidth()*0.25f, Gdx.graphics.getHeight() * 0.50f);

        third.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        third.setScale(Gdx.graphics.getWidth()*0.00065f);
        third.draw(game.batch, "3. "+thirdHighest, Gdx.graphics.getWidth()*0.25f, Gdx.graphics.getHeight() * 0.35f);

        fourth.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        fourth.setScale(Gdx.graphics.getWidth()*0.00065f);
        fourth.draw(game.batch, "4. "+fourthHighest, Gdx.graphics.getWidth()*0.65f, Gdx.graphics.getHeight() * 0.65f);

        fifth.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        fifth.setScale(Gdx.graphics.getWidth()*0.00065f);
        fifth.draw(game.batch, "5. " + fifthHighest, Gdx.graphics.getWidth()*0.65f, Gdx.graphics.getHeight() * 0.50f);

        sixth.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        sixth.setScale(Gdx.graphics.getWidth()*0.00065f);
        sixth.draw(game.batch, "6. "+sixthHighest, Gdx.graphics.getWidth()*0.65f, Gdx.graphics.getHeight() * 0.35f);


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
    public interface RequestHandler {
        public void confirm(ConfirmInterface confirmInterface);
    }
    public interface ConfirmInterface {
        public void yes();
        public void no();
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