package ru.my.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.my.game.base.BaseShip;
import ru.my.game.math.Rect;
import ru.my.game.pool.BulletPool;
import ru.my.game.pool.ExplosionPool;

public class Enemy extends BaseShip {

    private enum State {DESCENT, FIGHT}
    private State state;
    private Vector2 descentV = new Vector2(0f, -0.5f);
    private Sound boom;

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        boom = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        v = new Vector2();
        v0 = new Vector2();
        bulletV = new Vector2();
        this.worldBounds = worldBounds;
        state = State.DESCENT;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        switch (state) {
            case DESCENT:
                if(getTop() <= worldBounds.getTop()) {
                    v.set(v0);
                    state = State.FIGHT;
                }
                break;
            case FIGHT:
                reloadTimer += delta;
                if(reloadTimer >= reloadInterval) {
                    reloadTimer = 0f;
                    shoot();
                }
                if (getTop() < worldBounds.getBottom()) {
                    setBottom(worldBounds.getTop());
                }
                break;
        }
    }

    public void set(TextureRegion [] regions,
                    Vector2 v0,
                    TextureRegion bulletRegion,
                    float bulletHeight,
                    float bulletVY,
                    int damage,
                    float reloadInterval,
                    float height,
                    int hp) {
        this.regions = regions;
        this.v0 = v0;
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        reloadTimer = reloadInterval;
        v.set(descentV);
        state = State.DESCENT;
    }

    public boolean isbulletCollision (Rect bullet) {
        return !(
                bullet.getRight() < getLeft()
                || bullet.getLeft()  > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
                );
    }

    @Override
    public void destroy() {
        boom();
        super.destroy();
    }

    public void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
        boom.play();
    }

    @Override
    public void dispose() {
        boom.dispose();
    }
}
