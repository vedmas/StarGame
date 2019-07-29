package ru.my.game;


import com.badlogic.gdx.Game;
import ru.my.game.Screen.MenuScreen;

public class StarGame extends Game {
	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
