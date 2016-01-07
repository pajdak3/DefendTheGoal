package com.defend.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by VP on 26.2.2015.
 */
public class powerUP1 {
    private Rectangle loc;

    public powerUP1() {
        loc = new Rectangle(Gdx.graphics.getWidth()*0.85f, Gdx.graphics.getHeight()*0.7f, 600, 600);
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

