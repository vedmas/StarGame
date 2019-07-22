package ru.my.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.my.game.base.ScaledTouchUpButton;
import ru.my.game.math.Rect;

public class ButtonExit extends ScaledTouchUpButton {

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.20f);
        setBottom(worldBounds.getBottom() + 0.05f);
        setRight(worldBounds.getRight() - 0.05f);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
