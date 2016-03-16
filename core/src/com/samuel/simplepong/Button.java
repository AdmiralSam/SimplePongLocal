package com.samuel.simplepong;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by SamuelDong on 2/16/16.
 */
public class Button extends Actor {
    private TextureRegion button;
    private float x, y;

    public Button(int x, int y, float centerX, float centerY) {
        button = new TextureRegion(SimplePong.ButtonTexture, x, y, 200, 75);
        this.x = centerX;
        this.y = centerY;
        setBounds(centerX - 100, centerY - 37.5f, 200, 75);
    }

    public void setColor(int x, int y)
    {
        button = new TextureRegion(SimplePong.ButtonTexture, x, y, 200, 75);
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return super.hit(x, y, touchable);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(button, x - 100, y - 37.5f);
    }
}
