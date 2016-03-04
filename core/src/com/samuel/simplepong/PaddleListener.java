package com.samuel.simplepong;

/**
 * Created by SamuelDong on 2/16/16.
 */
public interface PaddleListener {
    void moveClientPaddle(float location);
    void moveServerPaddle(float location);
}
