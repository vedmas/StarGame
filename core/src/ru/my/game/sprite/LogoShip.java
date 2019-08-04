package ru.my.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.my.game.base.BaseShip;
import ru.my.game.math.Rect;
import ru.my.game.pool.BulletPool;
import ru.my.game.pool.ExplosionPool;

public class LogoShip extends BaseShip {

    public LogoShip (TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        bulletRegion = atlas.findRegion("bulletMainShip");
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        reloadInterval = 0.2f;
        v = new Vector2();
        v0 = new Vector2(0.3f, 0);
        bulletV = new Vector2(0, 0.5f);
        bulletHeight = 0.015f;
        damage = 1;
    }

    public void resize(Rect worldBounds) {
        setHeightProportion(0.20f);
        pos.set(0, 0);
    }

}
