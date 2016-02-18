package com.samuel.simplepong;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by SamuelDong on 2/16/16.
 */
public class PersonController extends InputListener{
    private boolean dragging;
    private float x, y;
    private float location;
    private NetworkManager networkManager;
    private Paddle paddle;

    public PersonController(NetworkManager networkManager, Paddle paddle) {
        this.networkManager = networkManager;
        this.paddle = paddle;
        dragging = false;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        dragging = true;
        this.x = x;
        this.y = y;
        location = paddle.getLocation();
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        dragging = false;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if (dragging) {
            if (Math.abs(this.x - x) > 50) {
                dragging = false;
            }
            else {
                paddle.setLocation(location + y - this.y);
                networkManager.sendLocation(paddle.getLocation());
            }
        }
    }
}
