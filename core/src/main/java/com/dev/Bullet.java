package com.dev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Bullet extends Sprite {
    Texture bulletTexture;
    float bulletSpeed = 6.7f;
    float delta;
    Rectangle bulletRectangle;

    public Bullet() {
        bulletTexture = new Texture("bullet_outlined.png");
        set(new Sprite(bulletTexture));
        setSize(0.5f,0.5f);
        bulletRectangle = new Rectangle();

    }

    public void update(float worldWidth,float worldHeight){
        delta = Gdx.graphics.getDeltaTime();
        translateX(bulletSpeed * delta);

        bulletRectangle.set(getX(),getY(),getWidth(),getHeight());


    }

    public boolean isOutOfBounds(float worldWidth, float worldHeight) {
        return getX() > worldWidth || getX() + getWidth() < 0 ||
            getY() > worldHeight || getY() + getHeight() < 0;
    }
    public void dispose() {
        bulletTexture.dispose();
    }


}
