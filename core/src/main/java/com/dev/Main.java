package com.dev;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.Array;


public class Main implements ApplicationListener {

    Texture backgroundTexture;
    TextureRegion backgroundRegion;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Player player;
    Array<Bullet> bullets;
    Array<Enemy> enemies;
    float enemySpawnTimer;
    float enemySpawnInterval; // seconds between spawns




    @Override
    public void create() {
        backgroundTexture = new Texture("bg_tile.png");
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        backgroundRegion = new TextureRegion(backgroundTexture);
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 6);
        player = new Player();
        bullets = new Array<>();
        enemySpawnTimer = 0;
        enemySpawnInterval = 2f;
        enemies = new Array<>();
    }

    @Override
    public void resize(int width, int height) {
        if(width <= 0 || height <= 0) return;
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void input() {
        player.input();
        if (Gdx.input.isKeyJustPressed(Input.Keys.J)){
            Bullet bullet = new Bullet();
            bullet.setPosition(
                player.getX() + player.getWidth(),   // right edge of player
                player.getY() + player.getHeight() / 2 - bullet.getHeight() / 2 -0.15f//slightly lower
            );
            bullets.add(bullet);
        }

    }

    private void logic() {
        // Store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        //update each bullet
        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update(worldWidth, worldHeight);
            if (bullet.isOutOfBounds(worldWidth, worldHeight)) {
                bullet.dispose();
                bullets.removeIndex(i);
            }
        }
        enemySpawnTimer += Gdx.graphics.getDeltaTime();
        if (enemySpawnTimer >= enemySpawnInterval){
            spawnEnemy();
            enemySpawnTimer = 0;
        }
        for (int i = enemies.size - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            enemy.update();
            if (enemy.isOutOfBounds(worldWidth, worldHeight)) {
                enemy.dispose();
                enemies.removeIndex(i);
            }
        }

        // Clamp x to values between 0 and worldWidth
        player.setX(MathUtils.clamp(player.getX(), 0, worldWidth - player.getWidth()));
        player.setY(MathUtils.clamp(player.getY(), 0, worldHeight - player.getHeight()));

        player.update();

    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        backgroundRegion.setRegion(0, 0, (int)(worldWidth * 32), (int)(worldHeight * 32));

        spriteBatch.begin();

        spriteBatch.draw(backgroundRegion, 0, 0, worldWidth, worldHeight);
        player.draw(spriteBatch);

        for (Bullet bullet:bullets){
            bullet.draw(spriteBatch);
        }

        for (Enemy enemy:enemies){
            enemy.draw(spriteBatch);
        }

        spriteBatch.end();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        spriteBatch.dispose();
        player.dispose();
    }

    private void spawnEnemy(){

        String[] enemyTypes = {"fast bug","zippy bug","worm"};
        int randomIndex = MathUtils.random(enemyTypes.length-1);
        String randomEnemy = enemyTypes[randomIndex];


        Enemy enemy = new Enemy(randomEnemy);
        enemy.setPosition(viewport.getWorldWidth(), MathUtils.random(0, viewport.getWorldHeight() - enemy.getHeight()));
        enemies.add(enemy);

    }


}
