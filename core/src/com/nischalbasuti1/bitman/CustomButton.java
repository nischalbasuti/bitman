package com.nischalbasuti1.bitman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by nischal on 7/12/16.
 */

class CustomButton {

    private Sprite skin;
    public CustomButton(Texture texture, float x, float y, float width, float height){
        skin = new Sprite(texture);
        skin.setPosition(x,y);
        skin.setSize(width,height);
    }
    public void update (SpriteBatch batch, float input_x, float input_y) {
        checkIfClicked(input_x, input_y);
        skin.draw(batch); // draw the button
    }

    private void checkIfClicked (float ix, float iy) {
        if (ix > skin.getX() && ix < skin.getX() + skin.getWidth()) {
            if (iy > skin.getY() && iy < skin.getY() + skin.getHeight()) {
                // the button was clicked, perform an action
                System.out.println("Button clicked !");
            }
        }
    }
}
