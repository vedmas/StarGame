package ru.my.game.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.my.game.Screen.GameScreen;
import ru.my.game.base.ScaledTouchUpButton;
import ru.my.game.math.Rect;

public class Button_New_Game extends ScaledTouchUpButton {

//    GameScreen gameScreen;
        private Game game;

    public Button_New_Game(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("button_new_game"));
//        this.gameScreen = gameScreen;
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.07f);
        setBottom(worldBounds.getBottom() + 0.06f);
    }

    @Override
    public void action() {
//        gameScreen.resetGame();
        game.setScreen(new GameScreen(game));
    }
}
