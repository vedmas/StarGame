package ru.my.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.my.game.base.Sprite;
import ru.my.game.math.Rect;

public class NameGame extends Sprite {

    public NameGame(TextureAtlas atlas) {
        super(atlas.findRegion("X55"));
    }

    public void resize(Rect worldBounds) {
        setHeightProportion(0.6f);
        pos.set(0f, 0.15f);
    }
}
