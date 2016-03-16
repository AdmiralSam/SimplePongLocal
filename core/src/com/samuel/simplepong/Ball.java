package com.samuel.simplepong;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by SamuelDong on 2/17/16.
 */
public class Ball extends Actor {
    public float x, y, dx, dy;
    private TextureRegion ball;

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
    public void act(float deltaTime) {
        x += dx * deltaTime;
        y += dy * deltaTime;

        if (x < -50 || x > 1050) {
            x = 500;
            y = 250;
            dx = 200;
            dy = 200;
        } else {
            if (y < 50) {
                y = 50;
                dy *= -1;
            }
            if (y > 450) {
                y = 450;
                dy *= -1;
            }
            if (x < 175 && x > 125 && Math.abs(SimplePong.serverPaddle.getLocation() - y) < 150) {
                x = 175;
                dx *= -1;
            }
            if (x > 825 && x < 875 && Math.abs(SimplePong.clientPaddle.getLocation() - y) < 150) {
                x = 825;
                dx *= -1;
            }
        }
    }

    public void setLocation(float locationX, float locationY) {
        x=locationX;
        y=locationY;
    }
}
