package ru.my.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.my.game.base.BaseShip;
import ru.my.game.math.Rect;
import ru.my.game.pool.BulletPool;

public class Ship extends BaseShip {

    private static final int INVALID_POINTER = -1;

    private Vector2 bulletV = new Vector2(0, 0.5f);

    private boolean pressedLeft = false;
    private boolean pressedRight = false;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;
    private float reloadInterval;
    private float reloadTimer;
    private Sound laserSound;

    public Ship(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        bulletRegion = atlas.findRegion("bulletMainShip");
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        this.bulletPool = bulletPool;
        reloadInterval = 0.5f;
        v = new Vector2();
        v0 = new Vector2(0.3f, 0);

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(0.2f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if(reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        if(getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
        if(getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(touch.x < worldBounds.pos.x) {
            if(leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if(rightPointer != INVALID_POINTER) {
                moveRight();
            }
            else stop();
        } else
            if(pointer == rightPointer) {
                rightPointer = INVALID_POINTER;
                if(leftPointer != INVALID_POINTER) {
                    moveLeft();
                } else stop();
            }
        stop();
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
            case Input.Keys.A:
                moveLeft();
                pressedLeft = true;
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                moveRight();
                pressedRight = true;
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
            case Input.Keys.A:
                if(!pressedRight) {
                    stop();
                }
                pressedLeft = false;
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                if(!pressedLeft) {
                    stop();
                }
                pressedRight = false;
                break;
            case Input.Keys.UP:
                shoot();
                break;
        }
        return false;
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180); //развернули вектор движения с права на лево
    }

    private void stop() {
        v.setZero();
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, 0.015f, worldBounds, 1);
        laserSound.play();
    }

    public void dispose() {
        laserSound.dispose();

    }


}
