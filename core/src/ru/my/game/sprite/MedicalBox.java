package ru.my.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.my.game.base.ScaledTouchUpButton;
import ru.my.game.math.Rect;
import ru.my.game.math.Rnd;

public class MedicalBox extends ScaledTouchUpButton {

    MainShip ship;
    Rect worldBounds;

    public MedicalBox(TextureAtlas atlas, MainShip ship, Rect worldBounds) {
        super(atlas.findRegion("MedicalKit"));
        this.ship = ship;
        this.worldBounds = worldBounds;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.08f);
        setLeft(worldBounds.getRight());

    }

    public void generationPosMedicalBox(Rect worldBounds) {
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getTop(), worldBounds.getBottom());
        pos.set(posX, posY);
        if(getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
        }
        if(getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
        }
        if(getTop() > worldBounds.getTop()) {
            setTop(worldBounds.getTop());
        }
        if(getBottom() < worldBounds.getBottom()) {
            setBottom(worldBounds.getBottom());
        }
    }

    @Override
    public void action() {
        ship.setHp(ship.getHp() + 30);
        setLeft(worldBounds.getRight());
    }
}
