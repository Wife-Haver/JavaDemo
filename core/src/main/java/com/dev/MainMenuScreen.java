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

public class MainMenuScreen implements Screen {

    final Main game;
    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;


    public MainMenuScreen(Main game){
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );
        viewport = new FitViewport(25, 10);

        //font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height
        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
        font.getData().setScale(0.1f);
        font.setColor(Color.BLACK);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        //draw text. Remember that x and y are in meters

        font.draw(batch, "Welcome to Shooter Game!!! ", 1, 8);
        font.draw(batch, "Tap/Click anywhere to begin!", 1, 5);


        batch.end();
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
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
        batch.dispose();
        font.dispose();
    }
}
