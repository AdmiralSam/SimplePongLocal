package com.samuel.simplepong;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by SamuelDong on 2/16/16.
 */
public class Background extends Actor {
    private TextureRegion background;

    public Background() {
        background = new TextureRegion(SimplePong.PongTexture, 1000, 500);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(background, 0, 0);
    }
}
