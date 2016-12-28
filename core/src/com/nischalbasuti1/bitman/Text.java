package com.nischalbasuti1.bitman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Text extends Actor {

    BitmapFont font;
    String text;      //I assumed you have some object
    //that you use to access score.
    //Remember to pass this in!

    boolean alignCenter; //!!!Only works for single lines (i.e. no line breaks)!!!

    float x,y;
    public Text(float x, float y, boolean alignCenter, int size,String fontDir){
        this.x = x;
        this.y = y;
        this.alignCenter = alignCenter;
        text = "";

        try{
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal
                    (fontDir));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = size;

            font = generator.generateFont(parameter);
            generator.dispose();
        }
        catch (Exception e) {
            e.printStackTrace();
            font = new BitmapFont();
        }

        font.setColor(1,1,1,1);   //Brown is an underated Colour
    }

    public void setText(String text){
        this.text = text;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if(alignCenter){
            font.draw(batch,text, x - ((font.getLineHeight()*(text.length()))/2), y);
        }
        else {
            font.draw(batch,text,x,y);
        }
        //Also remember that an actor uses local coordinates for drawing within
        //itself!
    }
}