package com.defend.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by VP on 2.3.2015.
 */
public class powerUP2 {
    private Rectangle loc;

    public powerUP2() {

        loc = new Rectangle(Gdx.graphics.getWidth()*0.85f, Gdx.graphics.getHeight()*0.5f, 600, 600);
    }

    public float getX() {
        return loc.x;
    }
    public float getY() {
        return loc.y;
    }
    public void setX(float x) {
        loc.x = x;
    }
    public void setY(float y) {
        loc.y = y;
    }


}

