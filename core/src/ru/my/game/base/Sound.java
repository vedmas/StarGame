package ru.my.game.base;


import com.badlogic.gdx.Gdx;

public class Sound {

    private com.badlogic.gdx.audio.Sound bulletSound;
    private com.badlogic.gdx.audio.Sound laserSound;

    public void createSound() {
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    }

    public void playBulletSound() {
        createSound();
        bulletSound.play();
    }


}
