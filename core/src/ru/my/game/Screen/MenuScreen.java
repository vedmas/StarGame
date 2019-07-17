package ru.my.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.my.game.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private static final float SPEED_LEN = 0.005f;

    private Texture ship, background;
    private Vector2 click;
    private Vector2 speed;
    private Vector2 pos;
    private Vector2 buf;

    @Override
    public void show() {
        super.show();
        background = new Texture("Cosmos.jpg");
        ship = new Texture("NLO.jpg");
        click = new Vector2();
        speed = new Vector2();
        pos = new Vector2();
        buf = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //pos.add(speed);
        buf.set(click);
        if(buf.sub(pos).len() > SPEED_LEN) {
            pos.add(speed);
        }
        else {
            pos.set(click);
        }

        Gdx.gl.glClearColor(0.26f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, -1f, -1f, 2f, 2f);
        batch.draw(ship, pos.x, pos.y, 0.15f, 0.15f);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        background.dispose();
        ship.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        click.set(touch);
        speed.set(click.cpy().sub(pos)).setLength(SPEED_LEN);
        return false;
    }

    //    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
//        System.out.println("touch X = " + touch.x + " touch Y = " + touch.y);
//        speed.set(touch.cpy().sub(pos)).setLength(SPEED_LEN);
//        return false;
//    }
}
