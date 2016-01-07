package com.defend.android;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;


public class settings implements Screen {

    private Defend game;
    private OrthographicCamera camera;
    //private BitmapFont font;
    private Texture on;
    private Texture off;
    private Texture onBlack;
    private Texture offBlack;
    private Texture back;
    private Texture reset;
    private Texture sure;
    private Vector3 touchPos;


    private BitmapFont black;
    private BitmapFont sound;
    private BitmapFont vibrate;
    private BitmapFont clear;
    private ImageButton backButton;
    private ImageButton resetButton;

    private Preferences prefs;
    private boolean isBlack;
    private boolean silentSound;
    private boolean vibration;

    private boolean changeBlack;
    private boolean changeSound;
    private boolean changeVib;
    private boolean aresure;



    public settings(final Defend game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        touchPos = new Vector3(0, 0, 0);
        on = new Texture("on.png");
        off = new Texture("off.png");
        onBlack = new Texture("on1.png");
        offBlack = new Texture("off1.png");
        sure = new Texture("sure.png");
        back = new Texture("back.png");
        reset  =new Texture("reset.png");

        black = new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));
        sound = new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));
        vibrate = new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));
        clear = new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));

        prefs = Gdx.app.getPreferences("Preferences");
        isBlack = prefs.getBoolean("black", false);
        silentSound = prefs.getBoolean("sound", true);
        vibration = prefs.getBoolean("vibrate", true);

        changeBlack = false;
        changeSound = false;
        changeVib = false;

        aresure=false;

        backButton = new ImageButton(back, Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() * -0.1f, Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.4f);
        resetButton = new ImageButton(reset, Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() * 0.05f, Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.4f);
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
        backButton.update(game.batch);
        resetButton.update(game.batch);

        black.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        black.setScale(Gdx.graphics.getWidth()*0.00065f);
        black.draw(game.batch, "Black theme: ", Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight() * 0.9f);

        sound.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        sound.setScale(Gdx.graphics.getWidth()*0.00065f);
        sound.draw(game.batch, "Sounds: ", Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight() * 0.7f);

        vibrate.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        vibrate.setScale(Gdx.graphics.getWidth()*0.00065f);
        vibrate.draw(game.batch, "Vibration: ", Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight() * 0.5f);

        clear.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        clear.setScale(Gdx.graphics.getWidth()*0.00065f);
        clear.draw(game.batch, "High Score: ", Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight() * 0.3f);


        isBlack = prefs.getBoolean("black");
        silentSound = prefs.getBoolean("sound");
        vibration = prefs.getBoolean("vibrate");

        if(isBlack) {
            game.batch.draw(onBlack, Gdx.graphics.getWidth()*0.40f, Gdx.graphics.getHeight() * 0.783f);
        }
        else {
            game.batch.draw(off,Gdx.graphics.getWidth()*0.40f,Gdx.graphics.getHeight() * 0.783f);
        }
        if(silentSound) {
            if(isBlack) {
                game.batch.draw(onBlack, Gdx.graphics.getWidth() * 0.40f, Gdx.graphics.getHeight() * 0.583f);
            }
            else game.batch.draw(on, Gdx.graphics.getWidth() * 0.40f, Gdx.graphics.getHeight() * 0.583f);
        }
        else {
            if(isBlack) {
                game.batch.draw(offBlack, Gdx.graphics.getWidth() * 0.40f, Gdx.graphics.getHeight() * 0.583f);
            }
            else game.batch.draw(off, Gdx.graphics.getWidth() * 0.40f, Gdx.graphics.getHeight() * 0.583f);

        }
        Log.w("mess", ""+vibration);
        if(vibration) {
            if(isBlack) {
                game.batch.draw(onBlack, Gdx.graphics.getWidth() * 0.40f, Gdx.graphics.getHeight() * 0.383f);
            }
            else game.batch.draw(on, Gdx.graphics.getWidth() * 0.40f, Gdx.graphics.getHeight() * 0.383f);
        }
        else {
            if(isBlack) {
                game.batch.draw(offBlack, Gdx.graphics.getWidth() * 0.40f, Gdx.graphics.getHeight() * 0.383f);
            }
            else game.batch.draw(off, Gdx.graphics.getWidth() * 0.40f, Gdx.graphics.getHeight() * 0.383f);
        }
        if(aresure==true)
        game.batch.draw(sure, Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getHeight() * 0.25f);

        game.batch.end();
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);


            if (touchPos.x > Gdx.graphics.getWidth()*0.40f  && touchPos.x < Gdx.graphics.getWidth()*0.65f && aresure==false) {
               // prefs.putBoolean("music", !silentMusic);
                if (Gdx.graphics.getHeight()-touchPos.y > Gdx.graphics.getHeight()*0.783f  && Gdx.graphics.getHeight()-touchPos.y < Gdx.graphics.getHeight()*0.913f) {
                    changeBlack = true;

                }
            }



            if (touchPos.x > Gdx.graphics.getWidth()*0.40f  && touchPos.x < Gdx.graphics.getWidth()*0.65f && aresure==false) {

                if (Gdx.graphics.getHeight()-touchPos.y > Gdx.graphics.getHeight()*0.583f  && Gdx.graphics.getHeight()-touchPos.y < Gdx.graphics.getHeight()*0.713f) {
                    changeSound=true;
                }
            }


            if (touchPos.x > Gdx.graphics.getWidth()*0.40f  && touchPos.x < Gdx.graphics.getWidth()*0.65f && aresure==false) {

                if (Gdx.graphics.getHeight()-touchPos.y > Gdx.graphics.getHeight()*0.383f  && Gdx.graphics.getHeight()-touchPos.y < Gdx.graphics.getHeight()*0.513f) {
                    changeVib = true;

                }
            }

                if (Gdx.graphics.getHeight()-touchPos.y > Gdx.graphics.getHeight()*0.2f  && Gdx.graphics.getHeight()-touchPos.y < Gdx.graphics.getHeight()*0.5f && aresure==true) {

                    if (touchPos.x > Gdx.graphics.getWidth()*0.2f  && touchPos.x < Gdx.graphics.getWidth()*0.525f) {
                    for(int i = 1;i<10;i++) {
                        prefs.putInteger("score"+i,0);
                        prefs.flush();
                    }
                        aresure=false;
                }
                    else if(touchPos.x > Gdx.graphics.getWidth()*0.525f  && touchPos.x < Gdx.graphics.getWidth()*0.85f)
                        aresure=false;
                }

            camera.unproject(touchPos);
            if(resetButton.checkIfClicked(touchPos.x, touchPos.y)&& aresure==false)
            {
                aresure=true;
            }

            if (backButton.checkIfClicked(touchPos.x, touchPos.y)&& aresure==false) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }

        }
        else {
            if(changeBlack)
            {
                prefs.putBoolean("black", !isBlack);
                prefs.flush();
                changeBlack=false;

            }
            if(changeSound)
            {
                prefs.putBoolean("sound", !silentSound);
                prefs.flush();
                changeSound =false;
            }
            if(changeVib)
            {
                prefs.putBoolean("vibrate", !vibration);
                prefs.flush();
                changeVib =false;
            }


        }

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {

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