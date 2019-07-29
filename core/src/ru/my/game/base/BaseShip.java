package ru.my.game.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.my.game.math.Rect;
import ru.my.game.pool.BulletPool;
import ru.my.game.sprite.Bullet;

public abstract class BaseShip extends Sprite {

    protected TextureRegion bulletRegion;
    protected BulletPool bulletPool;
    protected Rect worldBounds;

    protected Vector2 v;
    protected Vector2 v0;
    protected Vector2 bulletV;

    protected float reloadInterval;
    protected float reloadTimer;

    protected Sound shootSound;

    protected int damage;
    protected int hp;

    protected float bulletHeight;

    public BaseShip() {
    }

    public BaseShip(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void dispose() {
        shootSound.dispose();
    }

    public void shoot() {
            Bullet bullet = bulletPool.obtain();
            bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
            shootSound.play();

}
}
