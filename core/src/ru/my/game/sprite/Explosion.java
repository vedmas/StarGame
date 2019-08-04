package ru.my.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.my.game.base.Sprite;

public class Explosion extends Sprite {

    private final float animateIntervale = 0.017f;
    private float animateTimer;

    public Explosion(TextureAtlas atlas) {
        super(atlas.findRegion("explosion"), 9, 9, 74);
    }

    public void set(float height, Vector2 pos) {
        this.pos.set(pos);
        setHeightProportion(height);
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if(animateTimer >= animateIntervale) {
            animateTimer = 0f;
            if(++frame == regions.length) {
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
