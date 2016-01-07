package com.defend.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Created by jonhlynur on 05/03/15.
 */
public class gameOver implements Screen {

    private Defend game;
    private OrthographicCamera camera;
    private BitmapFont finalScore;
    private Vector3 touchPos;
    private ImageButton menuButton;
    private Preferences prefs;
    private Texture menu;
    private int highScore;
    private int secondHighest;
    private int thirdHighest;
    private int fourthHighest;
    private int fifthHighest;
    private int sixthHighest;
    private int score;
    private boolean isBlack;


    public gameOver(final Defend game, final int score)
    {
        this.game = game;
        touchPos = new Vector3(0,0,0);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.score = score;

        menu = new Texture("menu.png");

        menuButton = new ImageButton(menu, Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() * -0.1f, Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.4f);
        prefs = Gdx.app.getPreferences("Preferences");
        highScore = prefs.getInteger("score1", 0);
        secondHighest = prefs.getInteger("score2", 0);
        thirdHighest = prefs.getInteger("score3", 0);
        fourthHighest = prefs.getInteger("score4", 0);
        fifthHighest = prefs.getInteger("score5", 0);
        sixthHighest = prefs.getInteger("score6", 0);
        isBlack = prefs.getBoolean("black",false);
        isNewHighScore();

        finalScore = new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));
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
        menuButton.update(game.batch);
        /*if(inLeaderBoard)
        {
            textListener listener = new textListener();

            Gdx.input.getTextInput(listener, "Congratulations!", "You made it to the leaderboards","Initials");
            Gdx.input.setOnscreenKeyboardVisible(true);
        }*/
        finalScore.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        finalScore.setScale(Gdx.graphics.getWidth()*0.001f);
        String fontText = "You Lost!";
        float fontLength = finalScore.getBounds(fontText).width;
        finalScore.draw(game.batch,fontText, Gdx.graphics.getWidth()*0.5f-fontLength/2, Gdx.graphics.getHeight() * 0.9f);



        finalScore.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        finalScore.setScale(Gdx.graphics.getWidth() * 0.001f);
        fontText = "Your Final Score Was: ";
        fontLength = finalScore.getBounds(fontText).width;
        finalScore.draw(game.batch, fontText, Gdx.graphics.getWidth()*0.5f - fontLength/2, Gdx.graphics.getHeight() * 0.65f);

        finalScore.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        finalScore.setScale(Gdx.graphics.getWidth() * 0.001f);
        fontText = ""+score;
        fontLength = finalScore.getBounds(fontText).width;
        finalScore.draw(game.batch, fontText, Gdx.graphics.getWidth()*0.5f - fontLength/2, Gdx.graphics.getHeight() * 0.4f);

        game.batch.end();

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);

            camera.unproject(touchPos);

            if (menuButton.checkIfClicked(touchPos.x, touchPos.y)) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }

        }


    }

    private void isNewHighScore()
    {
        if(score > highScore)
        {
            prefs.putInteger("score6",fifthHighest);
            prefs.putInteger("score5",fourthHighest);
            prefs.putInteger("score4", thirdHighest);
            prefs.putInteger("score3",secondHighest);
            prefs.putInteger("score2",highScore);
            prefs.putInteger("score1", score);
            prefs.flush();
        }
        else if(score > secondHighest)
        {
            prefs.putInteger("score6",fifthHighest);
            prefs.putInteger("score5",fourthHighest);
            prefs.putInteger("score4", thirdHighest);
            prefs.putInteger("score3",secondHighest);
            prefs.putInteger("score2",score);
            prefs.flush();
        }
        else if(score > thirdHighest)
        {
            prefs.putInteger("score6",fifthHighest);
            prefs.putInteger("score5",fourthHighest);
            prefs.putInteger("score4", thirdHighest);
            prefs.putInteger("score3",score);
            prefs.flush();
        }
        else if(score > fourthHighest)
        {
            prefs.putInteger("score6",fifthHighest);
            prefs.putInteger("score5",fourthHighest);
            prefs.putInteger("score4", score);
            prefs.flush();
        }
        else if(score > fifthHighest)
        {
            prefs.putInteger("score6",fifthHighest);
            prefs.putInteger("score5",score);
            prefs.flush();

        }
        else if(score > sixthHighest)
        {
            prefs.putInteger("score6",score);
            prefs.flush();
        }
    }
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {

        /*TextField.TextFieldStyle txtStyle = new TextField.TextFieldStyle();
        txtStyle.background = menuSkin.getDrawable("textbox");
        txtStyle.font = mainFont;

        txtUsername = new TextField("", txtStyle);
        txtUsername.setMessageText("test");

        txtUsername.setTextFieldListener(new TextField.TextFieldListener() {

            @Override
            public void keyTyped(TextField textField, char key) {
                Gdx.app.log("Skillaria", "" + key);
            }
        });
*/



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

    private class textListener implements Input.TextInputListener {

        private String name;

        @Override
        public void input (String text) {
            name = text;
        }

        @Override
        public void canceled () {
            name="cancel";
        }
        public String getName()
        {
            return name;
        }
    }


}
