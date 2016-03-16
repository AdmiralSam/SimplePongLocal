package com.samuel.simplepong;

/**
 * Created by Johnny on 15/03/2016.
 */
public class ClientState {

    private float timestamp;
    private Ball ball;
    private Paddle clientPaddle;
    private Paddle serverPaddle;

    public ClientState(Ball ball, Paddle clientPaddle, Paddle serverPaddle, float timestamp)
    {
        setBall(ball);
        setServerPaddle(serverPaddle);
        setClientPaddle(clientPaddle);
        setTimestamp(timestamp);
    }

    public float getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(float timestamp) {
        this.timestamp = timestamp;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Paddle getClientPaddle() {
        return clientPaddle;
    }

    public void setClientPaddle(Paddle clientPaddle) {
        this.clientPaddle = clientPaddle;
    }

    public Paddle getServerPaddle() {
        return serverPaddle;
    }

    public void setServerPaddle(Paddle serverPaddle) {
        this.serverPaddle = serverPaddle;
    }
}

