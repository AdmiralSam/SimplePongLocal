package com.samuel.simplepong;

/**
 * Created by SamuelDong on 2/16/16.
 */
public class NetworkController implements PaddleListener {
    private Paddle clientPaddle,serverPaddle;

    public NetworkController(Paddle clientPaddle) {
        this.clientPaddle = clientPaddle;
    }
    public NetworkController(Paddle clientPaddle,Paddle serverPaddle) {
        this.clientPaddle=clientPaddle;
        this.serverPaddle=serverPaddle;
    }

    @Override
    public void moveClientPaddle(float location) {
        clientPaddle.setLocation(location);
    }


    @Override
    public void moveServerPaddle(float location) {
        serverPaddle.setLocation(location);
    }
}
