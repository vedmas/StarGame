package ru.my.game.pool;

import ru.my.game.base.SpritesPool;
import ru.my.game.math.Rect;
import ru.my.game.sprite.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private BulletPool bulletPool;
    private Rect worldBounds;
    private ExplosionPool explosionPool;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, explosionPool, worldBounds);
    }
}
