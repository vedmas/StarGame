package ru.my.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.my.game.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private static final float SPEED_LEN = 0.5f;

    private Texture img, img1;
    private Vector2 touch;
    private Vector2 speed;
    private Vector2 pos;
    private Vector2 buf;

    @Override
    public void show() {
        super.show();
        img1 = new Texture("Cosmos.jpg");
        img = new Texture("NLO.jpg");
        touch = new Vector2();
        speed = new Vector2();
        pos = new Vector2();
        buf = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        buf.set(touch);
        if(buf.sub(pos).len() > SPEED_LEN) {
            pos.add(speed);
        }
        else {
            pos.set(touch);
        }

        Gdx.gl.glClearColor(0f, 0f, 0.139f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img1, 0, 0);
        batch.draw(img, pos.x, pos.y);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img1.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        System.out.println("touch X = " + touch.x + " touch Y = " + touch.y);
        speed.set(touch.cpy().sub(pos)).setLength(SPEED_LEN);
        return false;
    }
}
