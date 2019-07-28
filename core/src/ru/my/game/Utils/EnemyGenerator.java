package ru.my.game.Utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import javax.swing.plaf.synth.Region;

import ru.my.game.math.Rect;
import ru.my.game.math.Rnd;
import ru.my.game.pool.EnemyPool;
import ru.my.game.sprite.Enemy;

public class EnemyGenerator {

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.2f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_HP = 1;

    private float generateInterval = 4f;
    private float generateTimer;
    private TextureRegion [] enemySmallRegions;
    private Vector2 enemySmallV = new Vector2(0, -0.1f);
    private TextureRegion bulletRegion;
    private EnemyPool enemyPool;
    private Rect worldBounds;

    public EnemyGenerator(EnemyPool enemyPool, TextureAtlas atlas, Rect worldBounds) {
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;
        TextureRegion region0 = atlas.findRegion("enemy0");
        enemySmallRegions = Regions.split(region0, 1, 2, 2);
        bulletRegion = atlas.findRegion("bulletEnemy");
    }

    public void generate(float delta) {
        generateTimer += delta;
        if(generateTimer > generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            enemy.set(
                    enemySmallRegions,
                    enemySmallV,
                    bulletRegion,
                    ENEMY_SMALL_BULLET_HEIGHT,
                    ENEMY_SMALL_BULLET_VY,
                    ENEMY_SMALL_DAMAGE,
                    ENEMY_SMALL_RELOAD_INTERVAL,
                    ENEMY_SMALL_HEIGHT,
                    ENEMY_SMALL_HP
            );
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
