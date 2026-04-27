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

        enemyRectangle = new Rectangle(getX(),getY(),getWidth(),getHeight());
    }
    public void update(){
        delta = Gdx.graphics.getDeltaTime();
        translateX(-enemySpeed * delta);
        enemyRectangle.set(getX(),getY(),getWidth(),getHeight());
    }

    public void dispose(){
        enemyTexture.dispose();
    }

    public boolean isOutOfBounds(float worldWidth, float worldHeight) {
        return getX() + getWidth() < 0;  // enemies only move left, so only check left edge
    }
    public Rectangle getRectangle() {
        return enemyRectangle;
    }

}
