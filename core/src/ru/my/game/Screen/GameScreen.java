package ru.my.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.my.game.Utils.EnemyGenerator;
import ru.my.game.base.BaseScreen;
import ru.my.game.math.Rect;
import ru.my.game.pool.BulletPool;
import ru.my.game.pool.EnemyPool;
import ru.my.game.sprite.Background;
import ru.my.game.sprite.Bullet;
import ru.my.game.sprite.Enemy;
import ru.my.game.sprite.MainShip;
import ru.my.game.sprite.Star;


public class GameScreen extends BaseScreen {
    private Texture backg;
    private Background background;
    private TextureAtlas atlas;
    private Star[] starArray;
    private static final int STAR_COUNT = 64;
    private MainShip ship;
    private BulletPool bulletPool;

    private EnemyPool enemyPool;
    private EnemyGenerator enemyGenerator;

    @Override
    public void show() {
        super.show();
        backg = new Texture("textures/Backg.jpg");
        background = new Background(new TextureRegion(backg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        starArray = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT ; i++) {
            starArray[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(bulletPool, worldBounds);
        enemyGenerator = new EnemyGenerator(enemyPool, atlas, worldBounds);
        ship = new MainShip(atlas, bulletPool);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        freeAllDestroyedActiveSprites();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : starArray) {
            star.resize(worldBounds);
        }
        ship.resize(worldBounds);
    }

    @Override
    public void dispose() {
        backg.dispose();
        atlas.dispose();
        bulletPool.despose();
        enemyPool.despose();
        super.dispose();
    }

    public void update(float delta) {
        for (Star star : starArray) {
            star.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyGenerator.generate(delta);
        ship.update(delta);
    }

    // Проверка на пересечение вражеского корабля и корабля игрока
    public void checkCollision() {
        for (Enemy activeObject : enemyPool.getActiveObjects()) {
            if (!activeObject.isOutside(ship)) {
                activeObject.destroy();
            }
        }

        // Проверка попадания пули во вражеский корабль
        for (Bullet bullet : bulletPool.getActiveObjects()) {
            for (Enemy enemy : enemyPool.getActiveObjects()) {
                if(!bullet.isOutside(enemy)) {
                    bullet.destroy();
                    enemy.destroy();

                }
            }

        }
    }

    private void freeAllDestroyedActiveSprites() {
        enemyPool.freeAllDestroyedActiveSprites();
        bulletPool.freeAllDestroyedActiveSprites();
    }

    public void draw() {
        Gdx.gl.glClearColor(0.26f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : starArray) {
            star.draw(batch);
        }
        bulletPool.drowActiveSprites(batch);
        enemyPool.drowActiveSprites(batch);
        ship.draw(batch);
        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        ship.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        ship.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        ship.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        ship.keyUp(keycode);
        return false;
    }
}
