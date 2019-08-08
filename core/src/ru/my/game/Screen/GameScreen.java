package ru.my.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import java.util.ArrayList;
import ru.my.game.Utils.EnemyGenerator;
import ru.my.game.base.BaseScreen;
import ru.my.game.base.Font;
import ru.my.game.math.Rect;
import ru.my.game.pool.BulletPool;
import ru.my.game.pool.EnemyPool;
import ru.my.game.pool.ExplosionPool;
import ru.my.game.sprite.Background;
import ru.my.game.sprite.Bullet;
import ru.my.game.sprite.ButtonNewGame;
import ru.my.game.sprite.Enemy;
import ru.my.game.sprite.ButtonGameOver;
import ru.my.game.sprite.MainShip;
import ru.my.game.sprite.MedicalBox;
import ru.my.game.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final String FRAGS = "Убито: ";
    private static final String HP = "Жизни: ";
    private static final String LEVEL = "Уровень: ";
    private static final int BONUSKILL = 30;

    private Texture backg;
    private Background background;
    private TextureAtlas atlas;
    private TextureAtlas otherAtlas;
    private Star[] starArray;
    private static final int STAR_COUNT = 64;
    private ButtonGameOver game_over;
    private ButtonNewGame button_New_Game;
    private MainShip ship;
    private MedicalBox medicalBox;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private EnemyGenerator enemyGenerator;
    private enum State {PLAYING, PAUSE, GAME_OVER}
    private State state;
    private State stateBuffer;
    private Game game;
    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;
    private int frags;
    private int bonusFrags;
    private float distCollisionMedicalBox;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        backg = new Texture("textures/Backg.jpg");
        background = new Background(new TextureRegion(backg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        otherAtlas = new TextureAtlas("textures/OtherAtlas.tpack");
        starArray = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT ; i++) {
            starArray[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        enemyGenerator = new EnemyGenerator(enemyPool, atlas, worldBounds);
        ship = new MainShip(atlas, bulletPool, explosionPool);
        game_over = new ButtonGameOver(atlas);
        button_New_Game = new ButtonNewGame(atlas, this);
        medicalBox = new MedicalBox(otherAtlas, ship, worldBounds);
        state = State.PLAYING;
        stateBuffer = State.PLAYING;
        font = new Font("Font/GameText.fnt", "Font/GameText.png");
        font.setSize(0.022f);
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
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

    public void resetGame() {
        state = State.PLAYING;
        frags = 0;
        ship.restore();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : starArray) {
            star.resize(worldBounds);
        }
        ship.resize(worldBounds);
        game_over.resize(worldBounds);
        button_New_Game.resize(worldBounds);
        medicalBox.resize(worldBounds);
    }

    @Override
    public void dispose() {
            atlas.dispose();
            backg.dispose();
            bulletPool.despose();
            explosionPool.despose();
            enemyPool.despose();
            ship.dispose();
            font.dispose();
            super.dispose();
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
            enemyGenerator.generate(delta, frags);
            ship.update(delta);
            bonus();
        }

    }

    public void bonus() {
        if(bonusFrags >= BONUSKILL) {
            medicalBox.generationPosMedicalBox(worldBounds);
            bonusFrags = 0;
        }
    }

    public void checkCollision() {
        // Проверка на пересечение вражеского корабля и корабля игрока
        ArrayList<Enemy> enemyList = enemyPool.getActiveObjects();
        ArrayList<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy activeEnemy : enemyList) {
            float distCollision = activeEnemy.getHalfHeight() + ship.getHalfHeight();
            distCollisionMedicalBox = activeEnemy.getHalfHeight() + medicalBox.getHalfHeight();
            if (!activeEnemy.isDesttroyed()) {
                if ((activeEnemy.pos.dst(ship.pos)) < distCollision) {
                    activeEnemy.damage(ship.getHp());
                    ship.damage(activeEnemy.getHp());
                    if(ship.isDesttroyed()) {
                        state = State.GAME_OVER;
                    }
                }
                if(activeEnemy.pos.dst(medicalBox.pos) < distCollisionMedicalBox) {
                    medicalBox.resize(worldBounds);
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
                            if(activeEnemy.isDesttroyed()) {
                                frags++;
                                bonusFrags++;
                            }
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
            medicalBox.draw(batch);
            bulletPool.drowActiveSprites(batch);
            enemyPool.drowActiveSprites(batch);
            ship.draw(batch);

        }
        if(state == State.GAME_OVER || state == State.PAUSE && stateBuffer == State.GAME_OVER) {
            game_over.draw(batch);
            button_New_Game.draw(batch);
        }
        printInfo();
        batch.end();
    }

    public void printInfo() {
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop() - 0.005f);
        font.draw(batch, sbHp.append(HP).append(ship.getHp()), worldBounds.pos.x, worldBounds.getTop() - 0.005f, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyGenerator.getLevel()), worldBounds.getRight(), worldBounds.getTop()- 0.005f, Align.right);
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
        if(state == State.GAME_OVER) {
            button_New_Game.touchDown(touch, pointer, button);
        }
        if(state == State.PLAYING) {
            ship.touchDown(touch, pointer, button);
            medicalBox.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(state == State.GAME_OVER) {
            button_New_Game.touchUp(touch, pointer, button);
        }

        if(state == State.PLAYING) {
            ship.touchUp(touch, pointer, button);
            medicalBox.touchUp(touch, pointer, button);
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