package ru.my.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.my.game.base.Sprite;
import ru.my.game.math.Rect;

public class Ship extends Sprite {

    private static final float SPEED_LEN = 0.005f;
    private Vector2 click, speed, buf;

    public Ship(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
        click = new Vector2();
        speed = new Vector2();
        buf = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        setLeft(0.0f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        buf.set(click);
        if(buf.sub(pos).len() > SPEED_LEN) {
            pos.add(speed);
        }
        else {
            pos.set(click);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        click.set(touch);
        speed.set(click.cpy().sub(pos)).setLength(SPEED_LEN);
        return false;
    }
}
