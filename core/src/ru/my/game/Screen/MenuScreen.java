package ru.my.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.my.game.base.BaseScreen;
import ru.my.game.sprite.Nlo;

public class MenuScreen extends BaseScreen {



    private Texture ship, background;
    private Nlo nlo;


    @Override
    public void show() {
        super.show();
        background = new Texture("Cosmos.jpg");
        ship = new Texture("NLO.jpg");
        nlo = new Nlo(new TextureRegion(ship));


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        nlo.update(delta);
        Gdx.gl.glClearColor(0.26f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, -1f, -1f, 2f, 2f);
        nlo.draw(batch);
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
        nlo.touchDown(touch, pointer, button);
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
