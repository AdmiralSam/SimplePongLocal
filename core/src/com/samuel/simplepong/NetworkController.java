package com.samuel.simplepong;

/**
 * Created by SamuelDong on 2/16/16.
 */
public class NetworkController implements ActorListener {
    private Paddle clientPaddle,serverPaddle;
    private Ball ball;

    public NetworkController(Paddle clientPaddle) {
        this.clientPaddle = clientPaddle;
    }
    public NetworkController(Paddle clientPaddle,Paddle serverPaddle,Ball ball) {
        this.clientPaddle=clientPaddle;
        this.serverPaddle=serverPaddle;
        this.ball=ball;
    }

    @Override
    public void setBall(Ball ball)
    {
        this.ball.x=ball.x;
        this.ball.y=ball.y;
        this.ball.dx=ball.dx;
        this.ball.dy=ball.dy;
    }

    public void setBall(float ballX, float ballY, float balldX, float balldY)
    {
        this.ball.x=ballX;
        this.ball.y=ballY;
        this.ball.dx=balldX;
        this.ball.dy=balldYco;
    }

    @Override
    public void moveClientPaddle(float location) {
        clientPaddle.setLocation(location);
    }

    @Override
    public void moveClientPaddleByOffset(float offset)
    {
        clientPaddle.setLocation(clientPaddle.getLocation()+offset);
    }

    @Override
    public void moveServerPaddle(float location) {
        serverPaddle.setLocation(location);
    }

    @Override
    public void moveBall(float locationX,float locationY) {
        ball.setLocation(locationX,locationY);
    }

    @Override
    public void moveBallByOffset(float offsetX, float offsetY)
    {
        ball.setLocation(ball.x+offsetX,ball.y+offsetY);
    }
}
