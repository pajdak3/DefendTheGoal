package com.defend.android;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by VP on 26.2.2015.
 */
public class powerUP {
    private Rectangle loc;
    public powerUP()
    {
        loc = new Rectangle(-100,-100,10,10);
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

}