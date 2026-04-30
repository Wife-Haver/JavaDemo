package com.dev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {

    Main game;
    Texture backgroundTexture;
    TextureRegion backgroundRegion;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Player player;
    Array<Bullet> bullets;
    Array<Enemy> enemies;
    float enemySpawnTimer;
    float enemySpawnInterval;
    int playerHealth;
    Texture heartTexture;
    boolean isGameOver;


    public GameScreen(Main game) {
        this.game = game;

        backgroundTexture = new Texture("bg_tile.png");
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        backgroundRegion = new TextureRegion(backgroundTexture);
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 6);
        player = new Player();
        bullets = new Array<>();
        enemies = new Array<>();
        enemySpawnTimer = 0;
        enemySpawnInterval = 1;//1 second
        playerHealth = 5;
        heartTexture = new Texture("heart.png");
        heartTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
    }

    private void input() {
        player.input();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Bullet bullet = new Bullet();
            bullet.setPosition(
                player.getX() + player.getWidth(),
                player.getY() + player.getHeight() / 2 - bullet.getHeight() / 2 - 0.15f
            );
            bullets.add(bullet);
        }
    }

    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update(worldWidth, worldHeight);

            if (bullet.isOutOfBounds(worldWidth, worldHeight)) {
                bullet.dispose();
                bullets.removeIndex(i);
                continue;
            }

            for (int j = enemies.size - 1; j >= 0; j--) {
                Enemy enemy = enemies.get(j);
                if (bullet.getRectangle().overlaps(enemy.getRectangle())) {
                    bullet.dispose();
                    bullets.removeIndex(i);
                    enemy.startDying();
                    break;
                }
            }
        }



        enemySpawnTimer += Gdx.graphics.getDeltaTime();
        if (enemySpawnTimer >= enemySpawnInterval) {
            spawnEnemy();
            enemySpawnTimer = 0;
        }

        for (int i = enemies.size - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            enemy.update();
            if (enemy.isDeadAndGone()) {
                enemy.dispose();
                enemies.removeIndex(i);
            } else if (!enemy.isDying && enemy.isOutOfBounds(worldWidth, worldHeight)) {
                enemy.dispose();
                updateHealth(-1);
                enemies.removeIndex(i);
            }
        }

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
        for (Bullet bullet : bullets) bullet.draw(spriteBatch);
        for (Enemy enemy : enemies) enemy.draw(spriteBatch);

        for (int i = 0;i<playerHealth;i++){
            spriteBatch.draw(heartTexture, 0.1f + (i * 0.6f), 5.3f, 0.5f, 0.5f);
        }


        spriteBatch.end();
    }

    private void updateHealth(int amt){
        playerHealth += amt;
        if (playerHealth <= 0){
            game.setScreen(new LoseScreen(game));

        }
    }
    private void spawnEnemy() {
        String[] enemyTypes = {"fast bug", "zippy bug", "worm"};
        String randomEnemy = enemyTypes[MathUtils.random(enemyTypes.length - 1)];
        Enemy enemy = new Enemy(randomEnemy);
        enemy.setPosition(viewport.getWorldWidth(), MathUtils.random(0, viewport.getWorldHeight() - enemy.getHeight()));
        enemies.add(enemy);
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0) return;
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        spriteBatch.dispose();
        player.dispose();
        heartTexture.dispose();
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {
    }
}
