package com.dev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Sprite {

    Texture playerTexture;
    float playerSpeed = 6.7f;
    Rectangle playerRectangle;

    public Player() {
        playerTexture = new Texture("player_1.png");
        set(new Sprite(playerTexture));
        setSize(1, 1);
        playerRectangle = new Rectangle();
    }

    public void input() {
        float delta = Gdx.graphics.getDeltaTime();
        float dx = 0, dy = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) dx -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) dx += 1;

        if (dx != 0 && dy != 0) {
            dx *= 0.7071f;
            dy *= 0.7071f;
        }

        translateX(dx * playerSpeed * delta);
        translateY(dy * playerSpeed * delta);
    }

    public void update(){

        playerRectangle.set(getX(),getY(),getWidth(),getHeight());
    }
    public void dispose() {
        playerTexture.dispose();
    }


}
