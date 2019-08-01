package ru.my.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.my.game.base.Sprite;
import ru.my.game.math.Rect;

public class Game_Over extends Sprite {

    public Game_Over (TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    public void resize(Rect worldBounds) {
        setHeightProportion(0.1f);
        pos.set(0, 0);
    }
}
