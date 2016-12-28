package com.nischalbasuti1.bitman;

/*
 * Created by nischal on 7/15/2016.
 */
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

public class Player {
    Sprite sprite;
    Body body;
    BodyDef bodyDef;

    TextureRegion[] frames;
    TextureRegion currentFrame;

    private boolean shieldOn = false;

    final float PIXELS_TO_METERS = 100;

    Player(Texture texture, int x, int y, World world){
        sprite = new Sprite(texture);
        sprite.setPosition(x,y);

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(sprite.getX() / PIXELS_TO_METERS,sprite.getY()/PIXELS_TO_METERS);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                sprite.getWidth()/2 /PIXELS_TO_METERS,
                sprite.getHeight()/2 /PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction=0;
        fixtureDef.restitution = 0;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);

        body.setUserData(1);

        sprite.setPosition(
                (body.getPosition().x *PIXELS_TO_METERS) - sprite.getWidth()/2,
                (body.getPosition().y *PIXELS_TO_METERS) - sprite.getHeight()/2
        );

        frames = new TextureRegion[7];
        frames[0] = new TextureRegion(new Texture("bitman/bitman.png"));
        frames[1] = new TextureRegion(new Texture("bitman/bitman_left.png"));
        frames[2] = new TextureRegion(new Texture("bitman/bitman_right.png"));
        frames[3] = new TextureRegion(new Texture("bitman/bitman_dead.png"));

        frames[4] = new TextureRegion(new Texture("blue_bitman/bitman.png"));
        frames[5] = new TextureRegion(new Texture("blue_bitman/bitman_left.png"));
        frames[6] = new TextureRegion(new Texture("blue_bitman/bitman_right.png"));

        currentFrame = frames[0];
    }

    public void update(){
        //match sprite with body
        sprite.setPosition(
                (body.getPosition().x * PIXELS_TO_METERS) - sprite.getWidth()/2,
                (body.getPosition().y * PIXELS_TO_METERS) - sprite.getHeight()/2);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);

    }

    public boolean isShieldOn() {
        return shieldOn;
    }

    public void setShieldOn(boolean shieldOn) {
        this.shieldOn = shieldOn;
    }
}
