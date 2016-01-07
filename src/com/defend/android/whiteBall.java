package com.defend.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by jonhlynur on 18/02/15.
 */
public class whiteBall {
    private Rectangle loc;
    private float xVel;
    private float yVel;
    private float xAcc;
    private float yAcc;
    private boolean isMoving;
    private boolean explode;
    private boolean over;
    private float startingY;
    private float startingVel;
    private boolean determine;
    public whiteBall(float speed)
    {
        startingY = (float)(Gdx.graphics.getHeight()*0.05+Math.random()* Gdx.graphics.getHeight()*0.333);
        loc = new Rectangle(-50,startingY,10,10);
        startingVel = speed*(float)(Gdx.graphics.getWidth()*0.00312+(Math.random()*Gdx.graphics.getWidth()*0.00312));
        xVel=0;
        yVel = 0;
        yAcc = Gdx.graphics.getWidth()*0.0005208f;
        xAcc = Gdx.graphics.getWidth()*0.00005208f;
        explode=false;
        over = false;
        determine = false;
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
    public void move()
    {
        isMoving=true;
    }
    public void stop()
    {
        isMoving=false;
    }
    public void setXVelocity(float speed)
    {
        xVel = speed;
    }
    public void setYVelocity(float speed)
    {
        yVel = speed;
    }
    public float getXVel()
    {
        return xVel;
    }
    public float getYVel()
    {
        return yVel;
    }
    public float getXAcc()
    {
        return xAcc;
    }
    public float getYAcc()
    {
        return yAcc;
    }
    public boolean getExplode() { return explode; }
    public void setExplode() { explode = true; }
    public boolean getOver() { return over; }
    public void setOver() { over = true; }
    public boolean getDetermine() { return determine; }
    public void setDetermine(boolean deter) { determine = deter; }
    public float getStartingY() { return startingY; }
    public float getStartingVel() { return startingVel; }
    public void setStartingVel(float startingspeed) { startingVel = startingspeed; }

}