package com.samuel.simplepong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by SamuelDong on 2/16/16.
 */
public class PersonController extends InputListener {
    private boolean dragging;
    private float x, y;
    private float location;
    private LatencyManager latencyManager;
    private Paddle paddle;

    public PersonController(LatencyManager latencyManager, Paddle paddle) {
        this.latencyManager = latencyManager;
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
            } else {
                if(latencyManager.getSide())
                {
                    //Gdx.app.debug("personController", "server");
                    paddle.setLocation(location + y - this.y);
                }
                else
                {
                    //Gdx.app.debug("personController", "client");
                    if(SimplePong.clientPredictionOn) paddle.setLocation(location + y - this.y);
                    latencyManager.storeLocation(new Packet(location + y - this.y,SimplePong.timestamp));
                }
            }
        }
    }
}
