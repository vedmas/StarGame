package ru.my.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.my.game.base.BaseScreen;
import ru.my.game.math.Rect;
import ru.my.game.sprite.Background;

public class MenuScreen extends BaseScreen {

    private Texture backg;
    private Background background;

    @Override
    public void show() {
        super.show();
        backg = new Texture("textures/Backg.jpg");
        background = new Background(new TextureRegion(backg));
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(backg);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update();
        draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        backg.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    public void update() {

    }

    public void draw() {
        Gdx.gl.glClearColor(0.26f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.end();
    }
}