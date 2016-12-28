package com.nischalbasuti1.bitman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/*
 * Created by nischal on 7/15/2016.
 */
public class Platform {
    Sprite sprite;
    Body body;
    BodyDef bodyDef;

    final float PIXELS_TO_METERS = 100;

    Platform(Texture texture, int x, int y, World world){
        sprite = new Sprite(texture);
        sprite.setPosition(x,y);

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(sprite.getX() / PIXELS_TO_METERS,sprite.getY() / PIXELS_TO_METERS);

        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2 / PIXELS_TO_METERS,sprite.getHeight()/2 / PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef);

        body.setGravityScale(0);
        body.setFixedRotation(true);

        body.setUserData(2);
    }

    public void update(){
        //match sprite with body
        sprite.setPosition(
                (body.getPosition().x * PIXELS_TO_METERS) - sprite.getWidth()/2,
                (body.getPosition().y * PIXELS_TO_METERS) - sprite.getHeight()/2);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);

    }
}
