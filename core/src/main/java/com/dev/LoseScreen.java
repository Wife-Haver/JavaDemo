package com.dev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class LoseScreen implements Screen {
    final Main game;
    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;

    public LoseScreen(Main game){
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );
        viewport = new FitViewport(25, 10);
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.1f);
        font.setColor(Color.BLACK);
        // prints out used memory. should hover around 18mb used
//        MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
//        MemoryUsage heap = memBean.getHeapMemoryUsage();

        //System.out.println("Used: " + heap.getUsed() / 1024 / 1024 + " MB");
        //System.out.println("Max:  " + heap.getMax()  / 1024 / 1024 + " MB");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GRAY);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();


        font.draw(batch, "You Lost!!! ", 1, 8);
        font.draw(batch, "Press any key to Restart!!", 1, 5);


        batch.end();
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0) return;
        viewport.update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
