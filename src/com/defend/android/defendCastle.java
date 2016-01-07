
package com.defend.android;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;

public class defendCastle implements Screen {
    //Leikur og camera
    private Defend game;
    private OrthographicCamera camera;
    //Myndir og snúningur bolta
    private Texture background;
    private Texture white;
    private Texture red;
    private Texture green;
    private Texture blue;
    private Texture powerdouble;
    private Texture powerdestroy;
    private Texture powerslow;
    private Texture powerOff;
    private Texture pause;
    private Texture play;
    private Texture live;
    private TextureRegion bolti;
    private TextureRegion redbolti;
    private TextureRegion greenbolti;
    private TextureRegion bluebolti;
    private float[] countWhite;
    private float[] countRed;
    private float[] countGreen;
    private float[] countBlue;
    //Fylki af boltum og PowerUps
    private ArrayList<whiteBall> whites;
    private ArrayList<redBall> reds;
    private ArrayList<greenBall> greens;
    private ArrayList<blueBall> blues;
    private powerUP powerDouble;
    private powerUP1 powerDestroy;
    private powerUP2 powerSlow;
    //Staðsetningar á inputum
    private Vector3 touchPos;
    private Vector3 lastTouch;
    private Vector3 newTouch;
    private Vector3 secondTouch;
    private Vector3 thirdTouch;
    //Save-a seinustu staðsetningar
    private long thirdTime;
    private long secondTime;
    private long lastTime;
    private long newTime;
    //Tími milli bolta
    private long intervalBetweenBallWhite;
    private long lastTimeBallWhite;
    private long intervalBetweenBallRed;
    private long lastTimeBallRed;
    private long intervalBetweenBallBlue;
    private long lastTimeBallBlue;
    private long intervalBetweenBallGreen;
    private long lastTimeBallGreen;
    private long lastTimeBallAllRed;
    private long lastTimeBallAllNormal;
    private boolean ballAllnormal;
    private long IntervalRandom;
    private long BallFaster;
    private int lessBetween;
    private int randomStartTime=2000;
    //Tími milli powerUps
    private long doublePointTime;
    private long lastDoublePointTime;
    private long lastDoublePointTimeStop;
    //Fjöldi bolta á lífi og heildarlíf
    private int howMany;
    private int howManyreds;
    private int howManygreens;
    private int howManyblues;
    private int lives;
    //Stig
    private BitmapFont gameScore;
    private String totalScore;
    private Preferences prefs;
    private int score = 0;
    private boolean isBlack;
    private boolean soundOn;
    private boolean vibrateOn;
    private int redScore=10;
    private int whiteScore=20;
    private int greenScore=30;
    private int blueScore=100;
    //CountDown tímar á power-ups
    private BitmapFont gameTime;
    private BitmapFont gameRedTime;
    private BitmapFont gameYellowTime;
    private int time = 0;
    private int redtime = 0;
    private int yellowTime = 0;
    private String totalTime;
    private String totalRedTime;
    private String totalYellowTime;
    private boolean timi;
    private boolean redtimi;
    private boolean yellowtimi;
    private boolean slowmotion=false;
    private boolean oneloop=true;
    //Aðeins er hægt að halda á einum bolta
    private int isTouchingWhite = -1;
    private int isTouchingRed = -1;
    private int isTouchingGreen = -1;
    private int isTouchingBlue = -1;
    //Sprengja
    private static final int FRAME_COLS = 7;
    private static final int FRAME_ROWS = 1;
    private Animation walkAnimation;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    private TextureRegion currentFrame;
    private boolean explosionGoing;
    private float expX;
    private float expY;
    private float stateTime;
    //Sounds

    private Sound[] popSounds;
    private int randomSounds;
    //Bolti hoppar
    private boolean boltihoppar;
    //Hvaða state er í gangi(pause,run,resume)
    private State state = State.RUN;
    //pása
    private boolean changePaused;
    private long currentIntervalWhite;
    private long currentIntervalGreen;
    private long currentIntervalBlue;
    private long currentIntervalRed;
    private long currentBallFaster;
    private long currentDoublePointTime;
    private long currentDoublePointStopTime;
    private long currentBallAllRed;
    private long currentLastBallAllNormal;

    public defendCastle(final Defend game) {
        //Leikur og camera
        this.game = game;
        touchPos = new Vector3();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        live = new Texture("ballslive.png");
        powerOff=new Texture("graybutton.png");
        //PowerUps
        powerdouble= new Texture("x2.png");
        powerdestroy= new Texture("redbutton.png");
        powerslow= new Texture("yellowbutton.png");
        pause= new Texture("pausegame.png");
        play= new Texture("playgame.png");
        //Boltar
        white = new Texture("white.png");
        red = new Texture("red.png");
        green = new Texture("green.png");
        blue = new Texture("blue.png");
        //TextRegion fyrir alla bolta, fylki af boltum og powerups
        bolti = new TextureRegion(white, 0, 0, white.getWidth(), white.getHeight());
        redbolti = new TextureRegion(red, 0, 0, red.getWidth(), red.getHeight());
        greenbolti = new TextureRegion(green, 0, 0, red.getWidth(), red.getHeight());
        bluebolti = new TextureRegion(blue, 0, 0, blue.getWidth(), blue.getHeight());
        whites = new ArrayList<whiteBall>();
        reds = new ArrayList<redBall>();
        greens = new ArrayList<greenBall>();
        blues = new ArrayList<blueBall>();
        powerDouble= new powerUP();
        powerDestroy= new powerUP1();
        powerSlow= new powerUP2();
        howMany = 0;
        howManyreds = 0;
        howManygreens = 0;
        howManyblues = 0;
        //Fyrir staðsetningar á boltanum aftur í tímann
        newTime = 0;
        lastTime = 0;
        secondTime = 0;
        thirdTouch = new Vector3(0, 0, 0);
        secondTouch = new Vector3(0, 0, 0);
        lastTouch = new Vector3(0, 0, 0);
        newTouch = new Vector3(0, 0, 0);
        //Tími milli bolta
        lastTimeBallWhite = 0;
        lastTimeBallRed = TimeUtils.millis();
        lastTimeBallGreen =TimeUtils.millis();
        lastTimeBallBlue =TimeUtils.millis();
        lastTimeBallAllRed = TimeUtils.millis();
        lastTimeBallAllNormal = TimeUtils.millis()+5000;
        ballAllnormal=false;
        lastDoublePointTime = TimeUtils.millis();
        lastDoublePointTimeStop = TimeUtils.millis()+15000;
        intervalBetweenBallWhite = (int)(3000+Math.random()*3000);
        intervalBetweenBallRed = (int)(2000+Math.random()*4000);
        intervalBetweenBallGreen = (int)(3000+Math.random()*4000);
        intervalBetweenBallBlue = (int)(10000+Math.random()*10000);
        IntervalRandom=(int)(20000+Math.random()*20000);
        doublePointTime=(int)(30000+Math.random()*30000);
        BallFaster=TimeUtils.millis();
        lessBetween=4000;
        //Stig og líf
        totalScore = "Score: 0";
        gameScore = new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));
        prefs = Gdx.app.getPreferences("Preferences");
        isBlack = prefs.getBoolean("black",false);
        lives = 3;
        soundOn = prefs.getBoolean("sound",true);
        vibrateOn = prefs.getBoolean("vibrate", true);
        //Fastur bakgrunnur
        if(!isBlack) {
            background = new Texture("bakgrunnur2.png");
        }
        else background = new Texture("bakgrunnur.png");

        //Tími fyrir power-ups
        totalTime="";
        totalRedTime="";
        totalYellowTime="";
        gameTime=new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));
        gameRedTime=new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));
        gameYellowTime=new BitmapFont(Gdx.files.internal("fonts/prufa.fnt"));
        timi=false;
        redtimi=false;
        yellowtimi=false;
        //Sprengja
        walkSheet = new Texture(Gdx.files.internal("movement2.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);              // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.03f, walkFrames);
        stateTime = 0f;
        explosionGoing = false;
        //Sounds
        popSounds = new Sound[19];
        for(int i = 0;i<19;i++)
        {
            popSounds[i] = Gdx.audio.newSound(Gdx.files.internal("sound/Pop/Sound"+(i+1)+".mp3"));
        }
        randomSounds = 0;
        //Byrjunar state
        setGameState(state.RUN);
        //Pása
        changePaused = false;
        countWhite= new float[30];
        countRed=new float[30];
        countGreen=new float[30];
        countBlue=new float[30];
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        //Leikur byrjar
        game.batch.begin();
        //Teiknar Bakgrunn
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Teiknar fjölda lífa sem eftir eru
        drawLives();
        //Teiknar powerupp takka
        if(redtimi==true)
            game.batch.draw(powerOff, Gdx.graphics.getWidth()*0.85f, Gdx.graphics.getHeight()*0.7f, Gdx.graphics.getWidth()*0.073f, Gdx.graphics.getWidth()*0.073f);
        else
            game.batch.draw(powerdestroy, powerDestroy.getX(), powerDestroy.getY(), Gdx.graphics.getWidth()*0.073f, Gdx.graphics.getWidth()*0.073f);

        if(yellowtimi==true)
            game.batch.draw(powerOff, Gdx.graphics.getWidth()*0.85f, Gdx.graphics.getHeight()*0.5f, Gdx.graphics.getWidth()*0.073f, Gdx.graphics.getWidth()*0.073f);
        else
            game.batch.draw(powerslow, powerSlow.getX(), powerSlow.getY(), Gdx.graphics.getWidth()*0.073f, Gdx.graphics.getWidth()*0.073f);
        //Switch state milli RUN og PAUSE
        switch (state)
        {
            //Ef það er Runnað
            case RUN:
                //Countdown teljari sem startast ef ýtt er á double points powerup
                if(timi){
                    drawTime();
                }
                //Countdown teljari sem startast ef ýtt er á destroy all takka
                if(redtimi){
                    drawRedButton();
                }
                //Countdown teljari sem startast ef ýtt er á slow takka
                if(yellowtimi){
                    drawYellowButton();
                }
                //Tímalykkja boltana
                TimeBetweenBalls();
                //Teiknar double points og pause
                game.batch.draw(powerdouble, powerDouble.getX(), powerDouble.getY(), Gdx.graphics.getWidth()*0.073f, Gdx.graphics.getWidth()*0.073f);
                game.batch.draw(pause,  Gdx.graphics.getWidth()*0.88f, Gdx.graphics.getHeight()*0.05f, Gdx.graphics.getWidth()*0.08f, Gdx.graphics.getWidth()*0.08f);
                //Teiknar rauðu bolta
                drawRedBall();
                //Teiknar hvítu bolta
                drawWhiteBall();
                //Teiknar grænu bolta
                drawGreenBall();
                //Teiknar bláu bolta
                drawBlueBall();
                //Ef skjár er snertur
                if (Gdx.input.isTouched()) {
                    thirdTouch = new Vector3(secondTouch.x, secondTouch.y, 0);
                    secondTouch = new Vector3(lastTouch.x, lastTouch.y, 0);
                    lastTouch = new Vector3(touchPos.x, touchPos.y, 0);
                    touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                    camera.unproject(touchPos);
                    newTouch = new Vector3(touchPos.x, touchPos.y, 0);
                    //Hreyfir hvíta boltann og vistar 3 hnit til baka
                    for (int i = 0; i < howMany; i++) {
                        if ((touchPos.x > whites.get(i).getX()-(Gdx.graphics.getWidth()*0.057) && touchPos.x < whites.get(i).getX()+(Gdx.graphics.getWidth()*0.078) && touchPos.y > whites.get(i).getY()-(Gdx.graphics.getWidth()*0.0156) && touchPos.y < whites.get(i).getY()+(Gdx.graphics.getWidth()*0.078) || whites.get(i).getMoving()) && (isTouchingWhite == i || isTouchingWhite == -1) && isTouchingRed == -1 && isTouchingGreen == -1 && isTouchingBlue == -1) {
                            isTouchingWhite = i;
                            whites.get(i).setDetermine(true);
                            if (newTime == 0) lastTime = TimeUtils.nanoTime();
                            else lastTime = newTime;
                            if (lastTime == 0) secondTime = TimeUtils.nanoTime();
                            else secondTime = lastTime;
                            if (secondTime == 0) thirdTime = TimeUtils.nanoTime();
                            else thirdTime = secondTime;
                            whites.get(i).move();
                            whites.get(i).setX(touchPos.x - Gdx.graphics.getWidth()*0.0333f);
                            whites.get(i).setY(touchPos.y);
                            //Kemst ekki undir byrjunarhæð boltans
                            if (whites.get(i).getY() < whites.get(i).getStartingY()) {
                                whites.get(i).setY(whites.get(i).getStartingY());
                            }
                            newTime = TimeUtils.nanoTime();
                        }
                    }
                    //Sprengir rauða bolta ef það er snert hann
                    for (int i = 0; i < howManyreds; i++) {
                        if ((touchPos.x  > reds.get(i).getX()-(Gdx.graphics.getWidth()*0.057) && touchPos.x < reds.get(i).getX()+(Gdx.graphics.getWidth()*0.078) && touchPos.y > reds.get(i).getY()-(Gdx.graphics.getWidth()*0.0156) && touchPos.y < reds.get(i).getY()+(Gdx.graphics.getWidth()*0.078) || reds.get(i).getMoving()) && (isTouchingRed == i || isTouchingRed == -1) && isTouchingWhite == -1 && isTouchingGreen == -1 && isTouchingBlue == -1) {
                            isTouchingRed = i;
                            reds.get(i).setExplode();
                        }
                    }
                    //Hreyfir græna boltann og vistar 3 hnit til baka
                    for (int i = 0; i < howManygreens; i++) {
                        if ((touchPos.x> greens.get(i).getX()-(Gdx.graphics.getWidth()*0.057) && touchPos.x < greens.get(i).getX()+(Gdx.graphics.getWidth()*0.078) && touchPos.y > greens.get(i).getY()-(Gdx.graphics.getWidth()*0.0156) && touchPos.y < greens.get(i).getY()+(Gdx.graphics.getWidth()*0.078) || greens.get(i).getMoving()) && (isTouchingGreen == i || isTouchingGreen == -1) && isTouchingRed == -1 && isTouchingWhite == -1 && isTouchingBlue == -1) {
                            isTouchingGreen = i;
                            greens.get(i).setDetermine(true);
                            if (newTime == 0) lastTime = TimeUtils.nanoTime();
                            else lastTime = newTime;
                            if (lastTime == 0) secondTime = TimeUtils.nanoTime();
                            else secondTime = lastTime;
                            if (secondTime == 0) thirdTime = TimeUtils.nanoTime();
                            else thirdTime = secondTime;
                            greens.get(i).move();
                            greens.get(i).setX(touchPos.x - Gdx.graphics.getWidth()*0.0333f);
                            greens.get(i).setY(touchPos.y);
                            //Kemst ekki undir byrjunarhæð boltans
                            if (greens.get(i).getY() < greens.get(i).getStartingY()) {
                                greens.get(i).setY(greens.get(i).getStartingY());
                            }
                            newTime = TimeUtils.nanoTime();
                        }
                    }
                    //Hreyfir bláa boltann og vistar 3 hnit til baka
                    for (int i = 0; i < howManyblues; i++) {
                        if ((touchPos.x > blues.get(i).getX()-(Gdx.graphics.getWidth()*0.057) && touchPos.x < blues.get(i).getX()+(Gdx.graphics.getWidth()*0.078) && touchPos.y > blues.get(i).getY()-(Gdx.graphics.getWidth()*0.0156) && touchPos.y < blues.get(i).getY()+(Gdx.graphics.getWidth()*0.078) || blues.get(i).getMoving()) && (isTouchingBlue == i || isTouchingBlue == -1) && isTouchingRed == -1 && isTouchingGreen == -1 && isTouchingWhite == -1) {
                            isTouchingBlue = i;
                            blues.get(i).setDetermine(true);
                            if (newTime == 0) lastTime = TimeUtils.nanoTime();
                            else lastTime = newTime;
                            if (lastTime == 0) secondTime = TimeUtils.nanoTime();
                            else secondTime = lastTime;
                            if (secondTime == 0) thirdTime = TimeUtils.nanoTime();
                            else thirdTime = secondTime;
                            blues.get(i).move();
                            blues.get(i).setX(touchPos.x - Gdx.graphics.getWidth()*0.0333f);
                            blues.get(i).setY(touchPos.y);

                            newTime = TimeUtils.nanoTime();
                        }
                    }
                    //PowerUp Double Points fer í gagn ef snert
                    if (touchPos.x > powerDouble.getX() && touchPos.x< powerDouble.getX() + Gdx.graphics.getWidth()*0.073f && touchPos.y> powerDouble.getY() && touchPos.y<powerDouble.getY()+Gdx.graphics.getWidth()*0.073f &&isTouchingWhite == -1 && isTouchingGreen == -1 && isTouchingWhite == -1 && isTouchingBlue == -1)
                    {
                        timi=true;
                        powerDouble.setX(-100);
                        powerDouble.setY(-100);
                        redScore=10*2;
                        whiteScore=20*2;
                        greenScore=30*2;
                        blueScore=100*2;
                    }
                    //PowerUp Destroy all fer í gagn ef snert
                    if (touchPos.x  > powerDestroy.getX() && touchPos.x  < powerDestroy.getX()+Gdx.graphics.getWidth()*0.073f && touchPos.y > powerDestroy.getY() && touchPos.y <powerDestroy.getY()+Gdx.graphics.getWidth()*0.073f && isTouchingGreen == -1 && isTouchingWhite == -1 && isTouchingBlue == -1)
                    {
                        for (int i = 0; i < howManyreds; i++) {
                            reds.get(i).setExplode();
                        }
                        for (int i = 0; i < howManygreens; i++) {
                            greens.get(i).setExplode();
                        }
                        for (int i = 0; i < howMany; i++) {
                            whites.get(i).setExplode();
                        }
                        for (int i = 0; i < howManyblues; i++) {
                            blues.get(i).setExplode();
                        }
                        powerDestroy.setX(-200);
                        powerDestroy.setY(-200);
                        redtime=3600;
                        redtimi=true;
                    }
                    //PowerUp Slow fer í gagn ef snert
                    if (touchPos.x  > powerSlow.getX() && touchPos.x < powerSlow.getX()+Gdx.graphics.getWidth()*0.073f && touchPos.y  > powerSlow.getY() && touchPos.y  <powerSlow.getY()+Gdx.graphics.getWidth()*0.073f && isTouchingGreen == -1 && isTouchingWhite == -1 && isTouchingBlue == -1)
                    {
                        slowmotion=true;
                        for (int i = 0; i < howManyreds; i++) {
                            reds.get(i).setStartingVel(reds.get(i).getStartingVel()*0.5f);
                        }
                        for (int i = 0; i < howManygreens; i++) {
                            greens.get(i).setStartingVel(greens.get(i).getStartingVel() * 0.5f);
                        }
                        for (int i = 0; i < howMany; i++) {
                            whites.get(i).setStartingVel(whites.get(i).getStartingVel() * 0.5f);
                        }
                        for (int i = 0; i < howManyblues; i++) {
                            blues.get(i).setStartingVel(blues.get(i).getStartingVel() * 0.5f);
                        }
                        powerSlow.setX(-400);
                        powerSlow.setY(-400);
                        yellowTime=2700;
                        yellowtimi=true;
                    }
                    if(touchPos.x  > Gdx.graphics.getWidth()*0.88f && touchPos.x <Gdx.graphics.getWidth()*0.88f+Gdx.graphics.getWidth()*0.08f && touchPos.y  > Gdx.graphics.getHeight()*0.05f && touchPos.y  <Gdx.graphics.getHeight()*0.05f+Gdx.graphics.getWidth()*0.08f && isTouchingGreen == -1 && isTouchingWhite == -1 && isTouchingBlue == -1)
                    {
                        //setGameState(state.PAUSE);
                        changePaused = true;
                    }
                } else {
                    if(changePaused)
                    {
                        setGameState(state.PAUSE);
                        changePaused = false;
                        currentIntervalBlue = TimeUtils.millis()-lastTimeBallBlue;
                        currentIntervalWhite = TimeUtils.millis()-lastTimeBallWhite;
                        currentIntervalRed = TimeUtils.millis()-lastTimeBallRed;
                        currentIntervalGreen = TimeUtils.millis()-lastTimeBallGreen;
                        currentBallFaster = TimeUtils.millis()-BallFaster;
                        currentBallAllRed = TimeUtils.millis()- lastTimeBallAllRed;
                        currentDoublePointStopTime = TimeUtils.millis() - lastDoublePointTimeStop;
                        currentDoublePointTime = TimeUtils.millis() - lastDoublePointTime;
                        currentLastBallAllNormal = TimeUtils.millis() - lastTimeBallAllNormal;
                    }
                    isTouchingWhite = -1;
                    isTouchingRed = -1;
                    isTouchingGreen = -1;
                    isTouchingBlue = -1;
                    for (int i = 0; i < howMany; i++) {
                        whites.get(i).stop();
                        if (whites.get(i).getDetermine()) {
                            whites.get(i).setXVelocity((newTouch.x - thirdTouch.x) / ((newTime - thirdTime) / 1000000.0f));
                            whites.get(i).setYVelocity((newTouch.y - thirdTouch.y) / ((newTime - thirdTime) / 1000000.0f));
                            whites.get(i).setDetermine(false);
                        }
                    }
                    for (int i = 0; i < howManygreens; i++) {
                        greens.get(i).stop();
                        if (greens.get(i).getDetermine()) {
                            greens.get(i).setXVelocity((newTouch.x - thirdTouch.x) / ((newTime - thirdTime) / 1000000.0f));
                            greens.get(i).setYVelocity((newTouch.y - thirdTouch.y) / ((newTime - thirdTime) / 1000000.0f));
                            greens.get(i).setDetermine(false);
                        }
                    }
                    for (int i = 0; i < howManyblues; i++) {
                        blues.get(i).stop();
                        if (blues.get(i).getDetermine()) {
                            blues.get(i).setXVelocity((newTouch.x - thirdTouch.x) / ((newTime - thirdTime) / 1000000.0f));
                            blues.get(i).setYVelocity((newTouch.y - thirdTouch.y) / ((newTime - thirdTime) / 1000000.0f));
                            blues.get(i).setDetermine(false);
                        }
                    }
                }

                for (int i = 0; i < howMany; i++) {
                    if (!whites.get(i).getMoving()) {
                        //Hreyfir hvítu boltana í loftinu
                        momvement(whites.get(i),null,null,0);
                    }
                    //Tékkar hvort bolti fór yfir ákveðna hæð
                    if (whites.get(i).getY() > whites.get(i).getStartingY() + Gdx.graphics.getHeight() * 0.5f) {
                        whites.get(i).setOver();
                    }
                    //Ef bolti fer yfir ákveðna hæð og lendir í upphafspunkti springur bolti
                    if (whites.get(i).getY() == whites.get(i).getStartingY() && whites.get(i).getOver()) {
                        whites.get(i).setExplode();
                    }
                    //Ef bolti fer í mark missuru líf og bolti eyðist
                    if (whites.get(i).getX() > Gdx.graphics.getWidth() * 0.82f && isTouchingWhite != i && whites.get(i).getStartingY() == whites.get(i).getY() && !whites.get(i).getOver()) {
                        howMany--;
                        whites.remove(i);
                        lives--;
                        if(vibrateOn) {
                            Gdx.input.vibrate(500);
                        }
                    }
                }
                for (int i = 0; i < howManyreds; i++) {
                    //Ef bolti fer í mark missuru líf og bolti eyðist
                    if (reds.get(i).getX() > Gdx.graphics.getWidth() * 0.82f) {
                        howManyreds--;
                        reds.remove(i);
                        lives--;
                        if(vibrateOn) {
                            Gdx.input.vibrate(500);
                        }
                    }
                }
                for (int i = 0; i < howManygreens; i++) {
                    if (!greens.get(i).getMoving()) {
                        //Hreyfir grænu boltana í loftinu
                        momvement(null,greens.get(i),null,1);
                    }
                    //Tékkar hvort bolti fór yfir ákveðna hæð
                    if (greens.get(i).getY() > greens.get(i).getStartingY() + Gdx.graphics.getHeight() * 0.5f) {
                        greens.get(i).setOver();
                    }
                    //Ef bolti fer yfir ákveðna hæð og lendir í upphafspunkti springur bolti
                    if (greens.get(i).getY() == greens.get(i).getStartingY() && greens.get(i).getOver()) {
                        greens.get(i).setExplode();
                    }
                    //Ef bolti fer í mark missuru líf og bolti eyðist
                    if (greens.get(i).getX() > Gdx.graphics.getWidth() * 0.82f && isTouchingGreen != i  && !greens.get(i).getOver()) {
                        howManygreens--;
                        greens.remove(i);
                        lives--;
                        if(vibrateOn) {
                            Gdx.input.vibrate(500);
                        }
                    }
                }
                for (int i = 0; i < howManyblues; i++) {
                    if (!blues.get(i).getMoving()) {
                        //Hreyfir bláu boltana í loftinu
                        momvement(null,null,blues.get(i),2);
                    }
                    for (int j = 0; j < howManyblues; j++) {
                        for (int k = 0; k < howManyblues; k++) {
                            if(blues.get(j).getX()<blues.get(k).getX()+Gdx.graphics.getWidth()*0.073f && blues.get(j).getX()>blues.get(k).getX() && blues.get(j).getY()<blues.get(k).getY()+Gdx.graphics.getWidth()*0.073f && blues.get(j).getY()>blues.get(k).getY()) {
                                blues.get(j).setExplode();
                                blues.get(k).setExplode();
                            }
                        }
                    }
                    //Ef bolti fer í mark missuru líf og bolti eyðist
                    if (blues.get(i).getX() > Gdx.graphics.getWidth() * 0.82f && isTouchingBlue != i) {
                        howManyblues--;
                        blues.remove(i);
                        lives--;
                        if(vibrateOn) {
                            Gdx.input.vibrate(500);
                        }
                    }
                }
                if (explosionGoing) {
                    drawBomb();
                }
                //Leikur endar
                break;
            //Ef það er pásað
            case PAUSE:
                game.batch.draw(powerdouble, powerDouble.getX(), powerDouble.getY(), Gdx.graphics.getWidth()*0.073f, Gdx.graphics.getWidth()*0.073f);
                gameYellowTime.draw(game.batch, totalYellowTime, Gdx.graphics.getWidth()*0.95f, Gdx.graphics.getHeight() * 0.58f);
                for (int i = 0; i < howManyreds; i++) {
                    game.batch.draw(redbolti, reds.get(i).getX(), reds.get(i).getY(), redbolti.getRegionWidth() / (9600f/(Gdx.graphics.getWidth())), redbolti.getRegionHeight() / (9600f/(Gdx.graphics.getWidth())), redbolti.getRegionWidth() / (4800f/(Gdx.graphics.getWidth())), redbolti.getRegionHeight() / (4800f/(Gdx.graphics.getWidth())), 1f, 1f, countRed[i], false);
                }
                for (int i = 0; i < howMany; i++) {
                    game.batch.draw(bolti, whites.get(i).getX(), whites.get(i).getY(), bolti.getRegionWidth() / (9600f/(Gdx.graphics.getWidth())), bolti.getRegionHeight() / (9600f/(Gdx.graphics.getWidth())), bolti.getRegionWidth() / (4800f/(Gdx.graphics.getWidth())), bolti.getRegionHeight() / (4800f/(Gdx.graphics.getWidth())), 1f, 1f, countWhite[i], false);
                }
                for (int i = 0; i < howManygreens; i++) {
                    game.batch.draw(greenbolti, greens.get(i).getX(), greens.get(i).getY(), greenbolti.getRegionWidth() / (9600f/(Gdx.graphics.getWidth())), greenbolti.getRegionHeight() / (9600f/(Gdx.graphics.getWidth())), greenbolti.getRegionWidth() / (4800f/(Gdx.graphics.getWidth())), greenbolti.getRegionHeight() / (4800f/(Gdx.graphics.getWidth())), 1f, 1f, countGreen[i], false);
                }
                for (int i = 0; i < howManyblues; i++) {
                    game.batch.draw(bluebolti, blues.get(i).getX(), blues.get(i).getY(), bluebolti.getRegionWidth() /(9600f/(Gdx.graphics.getWidth())), bluebolti.getRegionHeight() / (9600f/(Gdx.graphics.getWidth())), bluebolti.getRegionWidth()/(4800f/(Gdx.graphics.getWidth())), bluebolti.getRegionHeight()/(4800f/(Gdx.graphics.getWidth())), 1f, 1f, countBlue[i], false);
                }
                gameTime.draw(game.batch, totalTime, Gdx.graphics.getWidth()*0.5f, Gdx.graphics.getHeight() * 0.97f);
                gameRedTime.draw(game.batch, totalRedTime, Gdx.graphics.getWidth()*0.95f, Gdx.graphics.getHeight() * 0.78f);
                gameYellowTime.draw(game.batch, totalYellowTime, Gdx.graphics.getWidth()*0.95f, Gdx.graphics.getHeight() * 0.58f);
                game.batch.draw(play, Gdx.graphics.getWidth()*0.88f, Gdx.graphics.getHeight()*0.05f, Gdx.graphics.getWidth()*0.08f, Gdx.graphics.getWidth()*0.08f);
                if(explosionGoing==true)
                    game.batch.draw(currentFrame, expX, expY);
                if (Gdx.input.isTouched()) {
                    touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                    camera.unproject(touchPos);


                    if (touchPos.x > Gdx.graphics.getWidth() * 0.88f && touchPos.x < Gdx.graphics.getWidth() * 0.88f + Gdx.graphics.getWidth() * 0.08f && touchPos.y > Gdx.graphics.getHeight() * 0.05f && touchPos.y < Gdx.graphics.getHeight() * 0.05f + Gdx.graphics.getWidth() * 0.08f && isTouchingGreen == -1 && isTouchingWhite == -1 && isTouchingBlue == -1) {
                        changePaused=true;
                    }
                }
                else
                {
                    if(changePaused)
                    {
                        setGameState(state.RUN);
                        changePaused=false;
                        lastTimeBallBlue = TimeUtils.millis()-currentIntervalBlue;
                        lastTimeBallGreen = TimeUtils.millis()-currentIntervalGreen;
                        lastTimeBallWhite = TimeUtils.millis()-currentIntervalWhite;
                        lastTimeBallRed = TimeUtils.millis()-currentIntervalRed;

                        lastTimeBallAllNormal = TimeUtils.millis() - currentLastBallAllNormal;
                        lastDoublePointTime = TimeUtils.millis() - currentDoublePointTime;
                        lastDoublePointTimeStop = TimeUtils.millis() - currentDoublePointStopTime;
                        lastTimeBallAllRed = TimeUtils.millis() - currentBallAllRed;
                        BallFaster = TimeUtils.millis()-currentBallFaster;

                    }
                }
                break;
            //Ef það er Resume-að
            case RESUME:

                break;

            default:
                break;
        }
        game.batch.end();
    }
    @Override
    public void resize(int width, int height) {
    }
    public enum State {
        PAUSE,
        RUN,
        RESUME,
        STOPPED
    }
    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
    }
    @Override
    public void hide() {
        this.state = State.PAUSE;
    }
    @Override
    public void pause() {
        this.state = State.PAUSE;
    }
    @Override
    public void resume() {
        this.state = State.PAUSE;
    }
    public void setGameState(State s){
        this.state = s;
    }
    @Override
    public void dispose() {
    }
    public void drawWhiteBall(){
        for (int i = 0; i < howMany; i++) {
            game.batch.draw(bolti, whites.get(i).getX(), whites.get(i).getY(), bolti.getRegionWidth() / (9600f/(Gdx.graphics.getWidth())), bolti.getRegionHeight() / (9600f/(Gdx.graphics.getWidth())), bolti.getRegionWidth()/(4800f/(Gdx.graphics.getWidth())), bolti.getRegionHeight()/(4800f/(Gdx.graphics.getWidth())), 1f, 1f, countWhite[i], false);
            //Hraðinn á snúning
            if (!whites.get(i).getMoving() && whites.get(i).getY()==whites.get(i).getStartingY() ) {
                if (countWhite[i] < 0.0f) countWhite[i] = 360.0f;
                else countWhite[i] = countWhite[i] - whites.get(i).getStartingVel()-5f;
            }
            //Bolti hreyfist til hægri
            if (whites.get(i).getX() < Gdx.graphics.getWidth()* 0.82f && whites.get(i).getY() < whites.get(i).getStartingY() + 30) {
                whites.get(i).setX(whites.get(i).getX() + whites.get(i).getStartingVel());
            }
            //Upphafsstillir bolta, runnar bomb u.m.b. eina lykkju
            if (whites.get(i).getExplode()) {
                expX = whites.get(i).getX();
                expY = whites.get(i).getStartingY();
                explosionGoing = true;
                whites.remove(i);
                howMany--;
                score += whiteScore;
                totalScore = "Score: " + score;
                popSounds();
            }
        }
    }
    public void drawBlueBall(){
        for (int i = 0; i < howManyblues; i++) {
            game.batch.draw(bluebolti, blues.get(i).getX(), blues.get(i).getY(), bluebolti.getRegionWidth() /(9600f/(Gdx.graphics.getWidth())), bluebolti.getRegionHeight() / (9600f/(Gdx.graphics.getWidth())), bluebolti.getRegionWidth()/(4800f/(Gdx.graphics.getWidth())), bluebolti.getRegionHeight()/(4800f/(Gdx.graphics.getWidth())), 1f, 1f, countBlue[i], false);
            //Hraðinn á snúning
            if (!blues.get(i).getMoving() && blues.get(i).getY()==blues.get(i).getStartingY() ) {
                if (countBlue[i] < 0.0f) countBlue[i] = 360.0f;
                else countBlue[i] = countBlue[i] - blues.get(i).getStartingVel()-5f;
            }
            //Bolti hreyfist til hægri
            if (blues.get(i).getX() < Gdx.graphics.getWidth() && blues.get(i).getY() < blues.get(i).getStartingY() + 30) {
                blues.get(i).setX(blues.get(i).getX() + blues.get(i).getStartingVel());
            }
            //Upphafsstillir bolta, runnar bomb u.m.b. eina lykkju
            if (blues.get(i).getExplode()) {
                expX = blues.get(i).getX();
                expY = blues.get(i).getStartingY();
                explosionGoing = true;
                blues.remove(i);
                howManyblues--;
                score += blueScore;
                totalScore = "Score: " + score;
                popSounds();
            }
        }
    }
    public void drawRedBall(){
        for (int i = 0; i < howManyreds; i++) {
            game.batch.draw(redbolti, reds.get(i).getX(), reds.get(i).getY(), redbolti.getRegionWidth() / (9600f/(Gdx.graphics.getWidth())), redbolti.getRegionHeight() /(9600f/(Gdx.graphics.getWidth())), redbolti.getRegionWidth()/(4800f/(Gdx.graphics.getWidth())), redbolti.getRegionHeight()/(4800f/(Gdx.graphics.getWidth())), 1f, 1f, countRed[i], false);
            //Hraðinn á snúning
            if (!reds.get(i).getMoving()) {
                if (countRed[i] < 0.0f) countRed[i] = 360.0f;
                else countRed[i] = countRed[i] - reds.get(i).getStartingVel()-5f;
            }
            //Bolti hreyfist til hægri
            if (reds.get(i).getX() < Gdx.graphics.getWidth() && reds.get(i).getY() < reds.get(i).getStartingY() + 30) {
                reds.get(i).setX(reds.get(i).getX() + reds.get(i).getStartingVel());
            }
            //Upphafsstillir bolta, runnar bomb u.m.b. eina lykkju
            if (reds.get(i).getExplode()) {
                expX = reds.get(i).getX();
                expY = reds.get(i).getStartingY();
                explosionGoing = true;
                reds.remove(i);
                howManyreds--;
                score += redScore;
                totalScore = "Score: " + score;
                popSounds();
            }
        }
    }
    public void drawGreenBall(){
        for (int i = 0; i < howManygreens; i++) {
            game.batch.draw(greenbolti, greens.get(i).getX(), greens.get(i).getY(), greenbolti.getRegionWidth() / (9600f/(Gdx.graphics.getWidth())), greenbolti.getRegionHeight() / (9600f/(Gdx.graphics.getWidth())), greenbolti.getRegionWidth()/(4800f/(Gdx.graphics.getWidth())), greenbolti.getRegionHeight()/(4800f/(Gdx.graphics.getWidth())), 1f, 1f, countGreen[i], false);
            //Hraðinn á snúning
            if (greens.get(i).getY()<greens.get(i).getStartingY()+Gdx.graphics.getHeight() *0.2f) {
                if (countGreen[i] < 0.0f) countGreen[i] = 360.0f;
                else countGreen[i] = countGreen[i] - greens.get(i).getStartingVel()-2f;
            }
            //Bolti hreyfist til hægri
            if (greens.get(i).getX() < Gdx.graphics.getWidth()&& greens.get(i).getY() < greens.get(i).getStartingY()+Gdx.graphics.getHeight() * 0.2f) {
                greens.get(i).setX(greens.get(i).getX() + greens.get(i).getStartingVel());
            }
            //Bolti hoppar
            if (greens.get(i).getY() < greens.get(i).getStartingY() + 30) {
                if (boltihoppar) {
                    greens.get(i).setYVelocity(Gdx.graphics.getWidth()*0.0104f);
                    if (greens.get(i).getY() < greens.get(i).getStartingY() + Gdx.graphics.getHeight() * 0.3f)
                        boltihoppar = false;
                } else {
                    if (greens.get(i).getY() == greens.get(i).getStartingY())
                        boltihoppar = true;
                }
            }
            //Upphafsstillir bolta, runnar bomb u.m.b. eina lykkju
            if (greens.get(i).getExplode()) {
                expX = greens.get(i).getX();
                expY = greens.get(i).getStartingY();
                explosionGoing = true;
                greens.remove(i);
                howManygreens--;
                score += greenScore;
                totalScore = "Score: " + score;
                popSounds();
            }
        }
    }
    public void drawTime() {
        gameTime.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        gameTime.setScale(Gdx.graphics.getWidth()/2742f);
        float deltaTime = Gdx.graphics.getDeltaTime(); //You might prefer getRawDeltaTime()

        time -= deltaTime; //if counting down

        int seconds = ((int) time) / 60;
        totalTime = "" + seconds;
        gameTime.draw(game.batch, totalTime, Gdx.graphics.getWidth()*0.5f, Gdx.graphics.getHeight() * 0.97f);
        if(seconds==0) {
            totalTime = "";
            redScore = 10;
            whiteScore = 20;
            greenScore = 30;
            timi = false;
        }
    }
    public void drawRedButton() {
        gameRedTime.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        gameRedTime.setScale(Gdx.graphics.getWidth()/2742f);
        float deltaRedTime = Gdx.graphics.getDeltaTime(); //You might prefer getRawDeltaTime()

        redtime -= deltaRedTime; //if counting down

        int redseconds = ((int) redtime) / 60;
        totalRedTime = "" + redseconds;
        gameRedTime.draw(game.batch, totalRedTime, Gdx.graphics.getWidth()*0.95f, Gdx.graphics.getHeight() * 0.78f);
        if(redseconds==0) {
            totalRedTime = "";
            powerDestroy.setX(Gdx.graphics.getWidth()*0.85f);
            powerDestroy.setY(Gdx.graphics.getHeight()*0.7f);
            redtimi=false;
        }
    }
    public void drawYellowButton() {
        gameYellowTime.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        gameYellowTime.setScale(Gdx.graphics.getWidth()/2742f);
        float deltaYellowTime = Gdx.graphics.getDeltaTime(); //You might prefer getRawDeltaTime()

        yellowTime -= deltaYellowTime; //if counting down

        int secondsYellow = ((int) yellowTime) / 60;
        totalYellowTime = "" + secondsYellow;
        gameYellowTime.draw(game.batch, totalYellowTime, Gdx.graphics.getWidth()*0.95f, Gdx.graphics.getHeight() * 0.58f);
        if(secondsYellow==35 && oneloop==true) {
            for (int i = 0; i < howManyreds; i++) {
                reds.get(i).setStartingVel(reds.get(i).getStartingVel()*2f);
            }
            for (int i = 0; i < howManygreens; i++) {
                greens.get(i).setStartingVel(greens.get(i).getStartingVel() * 2f);
            }
            for (int i = 0; i < howMany; i++) {
                whites.get(i).setStartingVel(whites.get(i).getStartingVel() * 2f);
            }
            for (int i = 0; i < howManyblues; i++) {
                blues.get(i).setStartingVel(blues.get(i).getStartingVel() * 2f);
            }
            slowmotion=false;
            oneloop=false;
        }
        if(secondsYellow==0) {
            totalYellowTime = "";
            powerSlow.setX(Gdx.graphics.getWidth()*0.85f);
            powerSlow.setY(Gdx.graphics.getHeight()*0.5f);
            yellowtimi=false;
            oneloop=true;
        }
    }
    public void drawLives() {
        gameScore.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        gameScore.setScale(Gdx.graphics.getWidth()/2742f);
        gameScore.draw(game.batch, totalScore, Gdx.graphics.getWidth()*0.041f, Gdx.graphics.getHeight() * 0.97f);
        if (lives == 3) {
            game.batch.draw(live, Gdx.graphics.getWidth() * 0.9f, Gdx.graphics.getHeight() * 0.9f, Gdx.graphics.getWidth() * 0.04f, Gdx.graphics.getWidth() * 0.04f);
            game.batch.draw(live, Gdx.graphics.getWidth() * 0.9f - Gdx.graphics.getWidth()*0.057f, Gdx.graphics.getHeight() * 0.9f, Gdx.graphics.getWidth() * 0.04f, Gdx.graphics.getWidth() * 0.04f);
            game.batch.draw(live, Gdx.graphics.getWidth() * 0.9f - Gdx.graphics.getWidth()*0.114f, Gdx.graphics.getHeight() * 0.9f, Gdx.graphics.getWidth() * 0.04f, Gdx.graphics.getWidth() * 0.04f);
        } else if (lives == 2) {
            game.batch.draw(live, Gdx.graphics.getWidth() * 0.9f, Gdx.graphics.getHeight() * 0.9f, Gdx.graphics.getWidth() * 0.04f, Gdx.graphics.getWidth() * 0.04f);
            game.batch.draw(live, Gdx.graphics.getWidth() * 0.9f - Gdx.graphics.getWidth()*0.057f, Gdx.graphics.getHeight() * 0.9f, Gdx.graphics.getWidth() * 0.04f, Gdx.graphics.getWidth() * 0.04f);

        } else if (lives == 1) {
            game.batch.draw(live, Gdx.graphics.getWidth() * 0.9f, Gdx.graphics.getHeight() * 0.9f, Gdx.graphics.getWidth() * 0.04f, Gdx.graphics.getWidth() * 0.04f);
        } else {

            game.setScreen(new gameOver(game, score));
            dispose();
        }

    }
    public void drawBomb() {

        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        game.batch.draw(currentFrame, expX, expY);
        if (walkAnimation.isAnimationFinished(stateTime)) {
            explosionGoing = false;
            stateTime = 0f;
        } else explosionGoing = true;
    }
    public void popSounds() {
        if(soundOn) {
            randomSounds = (int) (Math.random() * 19);
            popSounds[randomSounds].play(1.0f);
        }
    }
    public void TimeBetweenBalls() {
        //Lengd milli hvíta bolta
        if (TimeUtils.millis() - lastTimeBallWhite > intervalBetweenBallWhite) {
            if(slowmotion==false)
                whites.add(new whiteBall(1));
            else
                whites.add(new whiteBall(0.5f));
            howMany++;
            if(ballAllnormal==false)
                intervalBetweenBallWhite = (int)(randomStartTime+Math.random()*lessBetween);
            lastTimeBallWhite = TimeUtils.millis();
        }
        //Lengd milli rauðra bolta
        if (TimeUtils.millis() - lastTimeBallRed > intervalBetweenBallRed) {
            if(slowmotion==false)
                reds.add(new redBall(1));
            else
                reds.add(new redBall(0.5f));
            howManyreds++;
            if(ballAllnormal==false)
                intervalBetweenBallRed = (int)(randomStartTime+Math.random()*lessBetween);
            lastTimeBallRed = TimeUtils.millis();
        }
        //Lengd milli grænra bolta
        if (TimeUtils.millis() - lastTimeBallGreen > intervalBetweenBallGreen) {
            if(slowmotion==false)
                greens.add(new greenBall(1));
            else
                greens.add(new greenBall(0.5f));
            howManygreens++;
            if(ballAllnormal==false)
                intervalBetweenBallGreen = (int)(randomStartTime+Math.random()*(lessBetween+1000));
            lastTimeBallGreen = TimeUtils.millis();
        }
        //Lengd milli grænra bolta
        if (TimeUtils.millis() - lastTimeBallBlue > intervalBetweenBallBlue) {
            if(slowmotion==false)
                blues.add(new blueBall(1));
            else
                blues.add(new blueBall(0.5f));
            howManyblues++;
            if(ballAllnormal==false)
                intervalBetweenBallBlue = (int)(10000+Math.random()*(lessBetween+10000));
            lastTimeBallBlue = TimeUtils.millis();
        }

        //Lengd milli þess að fullt af rauðum boltum koma og engum hvitum og grænum
        if (TimeUtils.millis() - lastTimeBallAllRed > IntervalRandom) {
            lastTimeBallWhite = TimeUtils.millis();
            lastTimeBallRed = TimeUtils.millis();
            lastTimeBallGreen = TimeUtils.millis();
            intervalBetweenBallWhite = 10000;
            intervalBetweenBallGreen = 10000;
            intervalBetweenBallRed = 500;
            ballAllnormal=true;
            lastTimeBallAllRed = TimeUtils.millis();
        }
        //Lengd milli þess að fullt af rauðum koma stoppar
        if (TimeUtils.millis() - lastTimeBallAllNormal > IntervalRandom) {
            intervalBetweenBallWhite = (int)(2000+Math.random()*lessBetween);
            intervalBetweenBallRed = (int)(2000+Math.random()*lessBetween);
            intervalBetweenBallGreen = (int)(2000+Math.random()*(lessBetween+1000));
            lastTimeBallAllNormal = TimeUtils.millis();
            ballAllnormal=false;
            IntervalRandom = (int) (20000 + Math.random() * 20000);
        }
        //Lengd milli þess að allir boltar minnki bilið á milli random tímans síns
        if (TimeUtils.millis() - BallFaster > 20000) {
            if(lessBetween>1000);
            lessBetween-=250;
            if(randomStartTime>500)
                randomStartTime-=250;
            BallFaster = TimeUtils.millis();
        }
        //Lengd milli Double Points powerups
        if (TimeUtils.millis() - lastDoublePointTime > doublePointTime) {
            powerDouble.setX((float) (Gdx.graphics.getWidth() * 0.2 + Math.random() * Gdx.graphics.getHeight() * 0.6));
            powerDouble.setY((float) (Gdx.graphics.getHeight() * 0.4 + Math.random() * Gdx.graphics.getHeight() * 0.4));
            time = 900;
            lastDoublePointTime = TimeUtils.millis();
        }
        //Double Points powerup hverfur eftir 15sek ef ekki er tekið upp
        if (TimeUtils.millis() - lastDoublePointTimeStop > doublePointTime) {
            powerDouble.setX(-100);
            powerDouble.setY(-100);
            lastDoublePointTimeStop = TimeUtils.millis();
            doublePointTime = (int) (45000 + Math.random() * 30000);
        }
    }
    public void momvement(whiteBall x, greenBall y, blueBall z, int number) {

        if (number == 0) {
            if (x.getXVel() > 0) {
                if (x.getXVel() - x.getXAcc() <= 0) x.setXVelocity(0);
                else x.setXVelocity(x.getXVel() - x.getXAcc());
            }

            if (x.getXVel() < 0) {
                if (x.getXVel() + x.getXAcc() >= 0) x.setXVelocity(0);
                else x.setXVelocity(x.getXVel() + x.getXAcc());
            }
            if (x.getY() > x.getStartingY()) {
                if (x.getYVel() <= 0) {
                    x.setYVelocity(x.getYVel() - x.getYAcc());
                } else x.setYVelocity(x.getYVel() - x.getYAcc());
            }
            if (x.getX() + x.getXVel() < Gdx.graphics.getWidth() - Gdx.graphics.getWidth()*0.23 && x.getX() + x.getXVel() > 0) {

                x.setX(x.getX() + x.getXVel());
            }

            if (x.getY() + x.getYVel() > x.getStartingY()) {
                x.setY(x.getY() + x.getYVel());
            } else {
                x.setY(x.getStartingY());
                x.setXVelocity(0);
            }
        }
        else if (number == 1) {
            if (y.getXVel() > 0) {
                if (y.getXVel() - y.getXAcc() <= 0) y.setXVelocity(0);
                else y.setXVelocity(y.getXVel() - y.getXAcc());
            }
            if (y.getXVel() < 0) {
                if (y.getXVel() + y.getXAcc() >= 0) y.setXVelocity(0);
                else y.setXVelocity(y.getXVel() + y.getXAcc());
            }
            if (y.getY() > y.getStartingY()) {
                if (y.getYVel() <= 0) {
                    y.setYVelocity(y.getYVel() - y.getYAcc());
                } else y.setYVelocity(y.getYVel() - y.getYAcc());
            }
            if (y.getX() + y.getXVel() < Gdx.graphics.getWidth() - Gdx.graphics.getWidth()*0.23 && y.getX() + y.getXVel() > 0) {

                y.setX(y.getX() + y.getXVel());
            }

            if (y.getY() + y.getYVel() > y.getStartingY()) {
                y.setY(y.getY() + y.getYVel());
            } else {
                y.setY(y.getStartingY());
                y.setXVelocity(0);
            }
        }
        else if (number == 2)
        {
            if (z.getXVel() > 0) {
                if (z.getXVel() - z.getXAcc() <= 0) z.setXVelocity(0);
                else z.setXVelocity(z.getXVel() - z.getXAcc());
            }

            if (z.getXVel() < 0) {
                if (z.getXVel() + z.getXAcc() >= 0) z.setXVelocity(0);
                else z.setXVelocity(z.getXVel() + z.getXAcc());
            }
            if (z.getY() > z.getStartingY()) {
                if (z.getYVel() <= 0) {
                    z.setYVelocity(z.getYVel() - z.getYAcc());
                } else z.setYVelocity(z.getYVel() - z.getYAcc());
            }
            if (z.getX() + z.getXVel() < Gdx.graphics.getWidth() - Gdx.graphics.getWidth()*0.23 && z.getX() + z.getXVel() > 0) {

                z.setX(z.getX() + z.getXVel());
            }

            if (z.getY() + z.getYVel() > z.getStartingY()) {
                z.setY(z.getY() + z.getYVel());
            } else {
                z.setY(z.getStartingY());
                z.setXVelocity(0);
            }
        }
    }
}