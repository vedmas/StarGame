package ru.my.game.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public abstract class SpritesPool<T extends Sprite> {

    protected final ArrayList<T> activeObjects = new ArrayList<T>();
    protected final ArrayList<T> freeObjects = new ArrayList<T>();

    protected abstract T newObject();

    public T obtain() {
        T object;
        if(freeObjects.isEmpty()) {
            object = newObject();
        } else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
        return object;
    }

    public void updateActiveSprites(float delta) {
        for (T sprite : activeObjects) {
            if(!sprite.isDesttroyed()) {
                sprite.update(delta);
            }
        }
    }

    public void allDestroyActiveObjects() {  // метод переносит все активные объекты в список свободных
        for (int i = 0; i < activeObjects.size(); i++) {
            T sprite = activeObjects.get(i);
            if (activeObjects.remove(sprite)) {
                freeObjects.add(sprite);
                i--;
            }
        }
    }

    public void drowActiveSprites(SpriteBatch batch) {
        for (T sprite : activeObjects) {
            if(!sprite.isDesttroyed()) {
                sprite.draw(batch);
            }
        }
    }

    public void disposeAllSprites() {
        for (T sprite : activeObjects) {
            sprite.dispose();
        }
        for (T sprite : freeObjects) {
            sprite.dispose();
        }

    }

    public void freeAllDestroyedActiveSprites() {
        for (int i = 0; i < activeObjects.size(); i++) {
            T sprite = activeObjects.get(i);
            if(sprite.isDesttroyed()) {
                if(activeObjects.remove(sprite)) {
                    freeObjects.add(sprite);
                }
                i--;
                sprite.flashDestroy();
            }
        }
    }


    public ArrayList<T> getActiveObjects() {
        return activeObjects;
    }

    public void despose() {
        disposeAllSprites();
        activeObjects.clear();
        freeObjects.clear();
    }
}
