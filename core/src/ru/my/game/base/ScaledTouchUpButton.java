package ru.my.game.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class ScaledTouchUpButton extends Sprite {
    public static final float PRESS_SCALE = 0.9f;
    public boolean pressed;
    private int pointer;

    public ScaledTouchUpButton(TextureRegion region) {
        super(region);
        pressed = false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(pressed || !isMe(touch)) {
            return false;
        }
        this.pointer = pointer;
        scale = PRESS_SCALE;
        pressed = true;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(this.pressed != pressed || !pressed) {
            return false;
        }
            if(isMe(touch)) {
                action();
            }
            pressed = false;
            scale = 1f;

        return false;
    }
    public abstract void action();
}
