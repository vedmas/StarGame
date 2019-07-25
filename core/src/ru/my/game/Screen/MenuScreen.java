package ru.my.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.my.game.base.BaseScreen;
import ru.my.game.math.Rect;
import ru.my.game.sprite.Background;
import ru.my.game.sprite.ButtonExit;
import ru.my.game.sprite.ButtonPlay;
import ru.my.game.sprite.Star;

public class MenuScreen extends BaseScreen {

    private Texture backg;
    private Background background;
    private TextureAtlas atlas;
    private Star[] starArray;
    private static final int STAR_COUNT = 64;
    private ButtonExit btExit;
    private ButtonPlay btPlay;
    private Game game;



    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        backg = new Texture("textures/Backg.jpg");
        background = new Background(new TextureRegion(backg));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        starArray = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT ; i++) {
            starArray[i] = new Star(atlas);
        }
        btExit = new ButtonExit(atlas);
        btPlay = new ButtonPlay(atlas, game);

    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : starArray) {
            star.resize(worldBounds);
        }
        btExit.resize(worldBounds);
        btPlay.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        backg.dispose();
        atlas.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        btExit.touchDown(touch, pointer, button);
        btPlay.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        btExit.touchUp(touch, pointer, button);
        btPlay.touchUp(touch, pointer, button);
        return false;
    }

    public void update(float delta) {
        for (Star star : starArray) {
            star.update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(0.26f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : starArray) {
            star.draw(batch);
        }
        btExit.draw(batch);
        btPlay.draw(batch);
        batch.end();
    }
}