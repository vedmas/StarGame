package ru.my.game.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.my.game.math.Rect;
import ru.my.game.pool.BulletPool;
import ru.my.game.pool.ExplosionPool;
import ru.my.game.sprite.Bullet;
import ru.my.game.sprite.Explosion;

public abstract class BaseShip extends Sprite {

    protected TextureRegion bulletRegion;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected Rect worldBounds;

    protected Vector2 v;
    protected Vector2 v0;
    protected Vector2 bulletV;

    protected float reloadInterval;
    protected float reloadTimer;

    protected Sound shootSound;
    protected Sound boom;

    protected int damage;
    protected int hp;

    protected float bulletHeight;

    private float damageAnimateInterval = 0.1f;
    private float damageAnimateTimer = damageAnimateInterval;

    public BaseShip() {
    }

    public BaseShip(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }


    protected void shoot() {
            Bullet bullet = bulletPool.obtain();
            bullet.set(
                    this,
                    bulletRegion,
                    pos,
                    bulletV,
                    bulletHeight,
                    worldBounds,
                    damage);
            shootSound.play();
    }

    @Override
    public void update(float delta) {
        damageAnimateTimer += delta;
        if(damageAnimateTimer >= damageAnimateInterval) {
            frame = 0;
        }
    }

    public void damage(int damage) {
        frame = 1;
        damageAnimateTimer = 0f;
        hp -= damage;
        if(hp <= 0) {
            destroy();
        }
    }

    @Override
    public void destroy() {
        hp = 0;
        boom();
        super.destroy();
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
        boom = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        boom.play();
    }

    @Override
    public void dispose() {
        shootSound.dispose();
        //boom.dispose();
    }


}
