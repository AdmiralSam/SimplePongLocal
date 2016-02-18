package com.samuel.simplepong;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by SamuelDong on 2/17/16.
 */
public class Ball extends Actor {
    private TextureRegion ball;
    public float x, y, dx, dy;
    public Ball() {
        ball = new TextureRegion(SimplePong.PongTexture, 300, 500, 100, 100);
        x = 500;
        y = 250;
        dx = 200;
        dy = 200;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(ball, x - 50, y - 50);
    }

    @Override
    public void act(float delta) {
        x += dx * delta;
        y += dy * delta;
    }
}
