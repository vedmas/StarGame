package ru.my.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.my.game.base.BaseScreen;
import ru.my.game.math.Rect;
import ru.my.game.pool.BulletPool;
import ru.my.game.sprite.Background;
import ru.my.game.sprite.Ship;
import ru.my.game.sprite.Star;


public class GameScreen extends BaseScreen {
    private Texture backg;
    private Background background;
    private TextureAtlas atlas;
    private Star[] starArray;
    private static final int STAR_COUNT = 64;
    private Ship ship;
    private BulletPool bulletPool;

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
        ship = new Ship(atlas, bulletPool);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
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
        super.dispose();
        backg.dispose();
        atlas.dispose();
        bulletPool.despose();
    }

    private void freeAllDestroyedActiveSprites() {
        bulletPool.freeAllDestroyedActiveSprites();
    }

    public void update(float delta) {
        for (Star star : starArray) {
            star.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        ship.update(delta);
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
