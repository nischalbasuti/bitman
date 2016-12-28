package com.nischalbasuti1.bitman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/*
 * Created by nischal on 7/15/2016.
 */
public class Shield {

    Sprite sprite;
    Body body;
    BodyDef bodyDef;

    Animation animation;
    TextureRegion[] frames;
    TextureRegion currentFrame;

    float stateTime = 0;

    final float PIXELS_TO_METERS = 100;

    World world;
    boolean move = false;

    Shield(Texture texture, int x, int y, World world) {

        this.world = world;
        sprite = new Sprite(texture);
        sprite.setPosition(x,y);

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX() / PIXELS_TO_METERS,sprite.getY() / PIXELS_TO_METERS);

        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2f / PIXELS_TO_METERS,sprite.getHeight()/2f /
                PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.density = 0.0f;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);

        body.setFixedRotation(false);

        body.setUserData(3);

        frames = new TextureRegion[6];
        frames[0] = new TextureRegion(new Texture("shield.png"));
        frames[1] = new TextureRegion(new Texture("bomb/jbomb1.png"));
        frames[2] = new TextureRegion(new Texture("bomb/jbomb2.png"));
        frames[3] = new TextureRegion(new Texture("bomb/jbomb3.png"));
        frames[4] = new TextureRegion(new Texture("bomb/jbomb4.png"));
        frames[5] = new TextureRegion(new Texture("bomb/jbomb5.png"));

        animation = new Animation(0.1f,frames);

        currentFrame = frames[0];
    }

    public void update(){
        //match sprite with body
        sprite.setPosition(
                (body.getPosition().x * PIXELS_TO_METERS) - sprite.getWidth()/2,
                (body.getPosition().y * PIXELS_TO_METERS) - sprite.getHeight()/2);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }
}

