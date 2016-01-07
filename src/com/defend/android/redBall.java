package com.defend.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by VP on 23/02/15.
 */
public class redBall {
    private Rectangle loc;
    private boolean isMoving;
    private boolean explode;
    private float startingY;
    private float startingVel;
    public redBall(float speed)
    {
        startingY = (float)(Gdx.graphics.getHeight()*0.05+Math.random()* Gdx.graphics.getHeight()*0.333);
        loc = new Rectangle(-50, startingY,10,10);
        startingVel = speed*(float)(Gdx.graphics.getWidth()*0.00520+(Math.random()*Gdx.graphics.getWidth()*0.00520));
        explode=false;
        isMoving = false;
    }
    public float getX()
    {
        return loc.x;
    }
    public float getY()
    {
        return loc.y;
    }
    public void setX(float x)
    {
        loc.x = x;
    }
    public void setY(float y)
    {
        loc.y = y;
    }
    public boolean getMoving(){
        return isMoving;
    }
    public boolean getExplode() { return explode; }
    public void setExplode() { explode = true; }
    public float getStartingY() { return startingY; }
    public float getStartingVel() { return startingVel; }
    public void setStartingVel(float startingspeed) { startingVel = startingspeed; }

}