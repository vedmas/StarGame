package ru.my.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.my.game.base.Sprite;
import ru.my.game.math.Rect;

public class ButtonGameOver extends Sprite {

    public ButtonGameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    public void resize(Rect worldBounds) {
        setHeightProportion(0.08f);
        pos.set(0, 0);
    }
}
