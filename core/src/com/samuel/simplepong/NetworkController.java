package com.samuel.simplepong;

/**
 * Created by SamuelDong on 2/16/16.
 */
public class NetworkController implements PaddleListener {
    private Paddle paddle;

    public NetworkController(Paddle paddle) {
        this.paddle = paddle;
    }

    @Override
    public void movePaddle(float location) {
        paddle.setLocation(location);
    }
}
