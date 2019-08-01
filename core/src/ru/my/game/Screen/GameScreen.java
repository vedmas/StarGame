package ru.my.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import ru.my.game.Utils.EnemyGenerator;
import ru.my.game.base.BaseScreen;
import ru.my.game.math.Rect;
import ru.my.game.pool.BulletPool;
import ru.my.game.pool.EnemyPool;
import ru.my.game.pool.ExplosionPool;
import ru.my.game.sprite.Background;
import ru.my.game.sprite.Bullet;
import ru.my.game.sprite.Button_New_Game;
import ru.my.game.sprite.Enemy;
import ru.my.game.sprite.Game_Over;
import ru.my.game.sprite.MainShip;
import ru.my.game.sprite.Star;

public class GameScreen extends BaseScreen {
    private Texture backg;
    private Background background;
    private TextureAtlas atlas;
    private Star[] starArray;
    private static final int STAR_COUNT = 64;
    private Game_Over game_over;
    private Button_New_Game button_new_game;
    private MainShip ship;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private EnemyGenerator enemyGenerator;
    private enum State {PLAYING, PAUSE, GAME_OVER}
    private State state;
    private State stateBuffer;



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
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        enemyGenerator = new EnemyGenerator(enemyPool, atlas, worldBounds);
        ship = new MainShip(atlas, bulletPool, explosionPool);
        game_over = new Game_Over(atlas);
        button_new_game = new Button_New_Game(atlas);
        state = State.PLAYING;
        stateBuffer = State.PLAYING;
    }

    @Override
    public void render(float delta) {
        if(state == State.GAME_OVER) {
            enemyPool.allDestroyActiveObjects();
            bulletPool.allDestroyActiveObjects();
        }
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
        game_over.resize(worldBounds);
        button_new_game.resize(worldBounds);
    }

    @Override
    public void dispose() {
        if (state != State.PAUSE) {
            backg.dispose();
            atlas.dispose();
            bulletPool.despose();
            explosionPool.despose();
            enemyPool.despose();
            game_over.dispose();
            button_new_game.dispose();
            super.dispose();
        }
    }

    public void update(float delta) {
        if(state != State.PAUSE) {
            for (Star star : starArray) {
                star.update(delta);
            }
            explosionPool.updateActiveSprites(delta);
        }
        if(state == State.PLAYING) {
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyGenerator.generate(delta);
            ship.update(delta);
        }

    }


    public void checkCollision() {
        if(state != State.PLAYING) {
            return;
        }
        // Проверка на пересечение вражеского корабля и корабля игрока
        ArrayList<Enemy> enemyList = enemyPool.getActiveObjects();
        ArrayList<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy activeEnemy : enemyList) {
            float distCollision = activeEnemy.getHalfHeight() + ship.getHalfHeight();
            if (!activeEnemy.isDesttroyed()) {
                if ((activeEnemy.pos.dst(ship.pos)) < distCollision) {
                    activeEnemy.damage(ship.getHp());
                    ship.damage(activeEnemy.getHp());
                    if(ship.isDesttroyed()) {
                        state = State.GAME_OVER;
                    }
                }
            }
        }

        // Проверка попадания пули во вражеский корабль, уничтожение корабля по завершению HP
        for (Bullet bullet : bulletList) {
            if (bullet.isDesttroyed()) {
                continue;
            }
            if ((bullet.getOwner() != ship)) {
                if (ship.isBulletCollision(bullet)) {
                    ship.damage(bullet.getDamage());
                    if(ship.isDesttroyed()) {
                        state = State.GAME_OVER;
                    }
                    bullet.destroy();
                }
            } else {
                    for (Enemy activeEnemy : enemyList) {
                        if(activeEnemy.isDesttroyed()) {
                            continue;
                        }
                        if (activeEnemy.isbulletCollision(bullet)) {
                            activeEnemy.damage(bullet.getDamage());
                            bullet.destroy();
                        }
                    }
            }
        }
    }

    private void freeAllDestroyedActiveSprites() {
        enemyPool.freeAllDestroyedActiveSprites();
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    public void draw() {
        Gdx.gl.glClearColor(0.26f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : starArray) {
            star.draw(batch);
        }
        explosionPool.drowActiveSprites(batch);
        if(state == State.PLAYING || state == State.PAUSE && stateBuffer != State.GAME_OVER) {
            bulletPool.drowActiveSprites(batch);
            enemyPool.drowActiveSprites(batch);
            ship.draw(batch);
        }
        if(state == State.GAME_OVER) {
            game_over.draw(batch);
            button_new_game.draw(batch);
        }
        batch.end();
    }

    @Override
    public void pause() {
        super.pause();
        stateBuffer = state;
        state = State.PAUSE;
    }

    @Override
    public void resume() {
        super.resume();
        state = stateBuffer;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        button_new_game.touchDown(touch, pointer, button);
        if(state == State.PLAYING) {
            ship.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        button_new_game.touchUp(touch, pointer, button);
        if(state == State.PLAYING) {
            ship.touchUp(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.P){
            if(state != State.PAUSE) {
                pause();
            } else resume();
        }
        if(state == State.PLAYING) {
            ship.keyDown(keycode);
        }
        if(keycode == Input.Keys.R) {
            if(state == State.GAME_OVER) {
                explosionPool.allDestroyActiveObjects();
                ship.restore();
                state = State.PLAYING;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(state == State.PLAYING) {
            ship.keyUp(keycode);
        }
        return false;
    }
}
