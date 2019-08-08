package ru.my.game.pool;

import ru.my.game.base.SpritesPool;
import ru.my.game.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
