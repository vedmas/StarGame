package ru.my.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.my.game.Screen.GameScreen;
import ru.my.game.base.ScaledTouchUpButton;
import ru.my.game.math.Rect;

public class ButtonNewGame extends ScaledTouchUpButton {

    private GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.07f);
        setBottom(worldBounds.getBottom() + 0.06f);
    }

    @Override
    public void action() {
        gameScreen.resetGame();
    }
}
