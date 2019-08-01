package ru.my.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.my.game.base.ScaledTouchUpButton;
import ru.my.game.math.Rect;

public class LogoShip extends ScaledTouchUpButton {

    public LogoShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
    }

    public void resize(Rect worldBounds) {
        setHeightProportion(0.20f);
        pos.set(0, 0);
    }

    @Override
    public void action() {

    }
}
