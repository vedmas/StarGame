package ru.my.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.my.game.base.Sprite;
import ru.my.game.math.Rect;
import ru.my.game.math.Rnd;

public class Star extends Sprite {
    private Vector2 v = new Vector2();
    private Rect worldBounds;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        v.set(Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.5f, -0.1f));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.01f);
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getTop(), worldBounds.getBottom());
        pos.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkBounds();
    }

    private void checkBounds() {
        if(getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
        if(getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
        if(getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }

    }
}
