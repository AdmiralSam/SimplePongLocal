package com.samuel.simplepong;

/**
 * Created by SamuelDong on 2/16/16.
 */
public interface ActorListener {
    void moveClientPaddle(float location);
    void moveClientPaddleByOffset(float offset);
    void moveServerPaddle(float location);
    void moveBall(float locationX,float locationY);
    void moveBallByOffset(float offsetX,float offsetY);

    void setBall(Ball ball);
    void setBall(float ballX, float ballY, float balldX, float balldY);

}
