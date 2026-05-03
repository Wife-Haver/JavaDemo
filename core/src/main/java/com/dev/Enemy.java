package com.dev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.lang.reflect.Array;

public class Enemy extends Sprite {

    Texture enemyTexture;
    float delta;
    float enemySpeed;
    Rectangle enemyRectangle;
    boolean isDying = false;
    float fadeTimer = 1f; // seconds to fade out

    public Enemy(String enemy_type){
        switch (enemy_type){
            case "fast bug":
                enemyTexture  = new Texture("fast_bug.png");
                enemySpeed = 5;
                break;
            case "zippy bug":
                enemyTexture = new Texture("zippy_bug.png");
                enemySpeed = 4;
                break;
            case "worm":
                enemyTexture = new Texture("worm.png");
                enemySpeed = 3;
                break;
        }
        set(new Sprite(enemyTexture));
        setSize(1, 1);
        enemyRectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void update(){
        delta = Gdx.graphics.getDeltaTime();

        if (isDying) {
            fadeTimer -= delta;
            setAlpha(fadeTimer); // fade out over 1 second
        } else {
            translateX(-enemySpeed * delta);
            enemyRectangle.set(getX(), getY(), getWidth(), getHeight());
        }
    }

    public void startDying() {
        isDying = true;
        enemyRectangle.set(0, 0, 0, 0);
    }

    public boolean isDeadAndGone() {
        return isDying && fadeTimer <= 0;
    }

    public void dispose(){
        enemyTexture.dispose();
    }

    public boolean isOutOfBounds(float worldWidth, float worldHeight) {
        return getX() + getWidth() < 0;
    }

    public Rectangle getRectangle() {
        return enemyRectangle;
    }
}
