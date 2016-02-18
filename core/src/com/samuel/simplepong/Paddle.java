package com.samuel.simplepong;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by SamuelDong on 2/16/16.
 */
public class Paddle extends Actor {
    private TextureRegion paddleRegion;
    private float x, y;

    public Paddle(float x, float y) {
        this.x = x;
        this.y = y;
        paddleRegion = new TextureRegion(SimplePong.PongTexture, 400, 500, 50, 200);
        setBounds(x - 25, y - 100, 50, 200);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(paddleRegion, x - 25, y - 100);
    }

    public void setLocation(float y) {
        this.y = Math.max(Math.min(y, 400), 100);
        setBounds(x - 25, y - 100, 50, 200);
    }

    public float getLocation() {
        return y;
    }
}
