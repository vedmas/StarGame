package ru.my.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.my.game.base.ScaledTouchUpButton;
import ru.my.game.math.Rect;

public class Button_New_Game extends ScaledTouchUpButton {

    public Button_New_Game(TextureAtlas atlas) {
        super(atlas.findRegion("button_new_game"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.07f);
        setBottom(worldBounds.getBottom() + 0.06f);
    }

    @Override
    public void action() {

    }
}
