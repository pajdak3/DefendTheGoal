package com.defend.android;


        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Sprite;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by jonhlynur on 24/02/15.
 */
public class ImageButton {

    private Sprite skin;

    public ImageButton(Texture texture, float x, float y, float width, float height) {
        skin = new Sprite(texture); // your image
        skin.setPosition(x, y);
        skin.setSize(width, height);
    }

    public void update (SpriteBatch batch) {
        //checkIfClicked(input_x, input_y);
        skin.draw(batch); // draw the button
    }

    public boolean checkIfClicked (float ix, float iy) {
        if (ix > skin.getX() && ix < skin.getX() + skin.getWidth()) {

            if (iy > (skin.getY()+ Gdx.graphics.getHeight()*0.1) && iy < (skin.getY() - Gdx.graphics.getHeight()*0.1)  + skin.getHeight()) {
                // the button was clicked, perform an action
                return true;
            }
        }
        return false;
    }


}