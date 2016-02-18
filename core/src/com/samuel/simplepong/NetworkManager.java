package com.samuel.simplepong;

import com.esotericsoftware.kryonet.Listener;

/**
 * Created by SamuelDong on 2/16/16.
 */
public abstract class NetworkManager extends Listener {
    protected State currentState;
    protected ReadyListener listener;
    protected PaddleListener paddleListener;
    protected NetworkManager(ReadyListener listener) {
        currentState = State.Waiting;
        this.listener = listener;
    }

    public abstract boolean getSide();

    public abstract void startUp();

    public abstract void shutDown();

    public abstract void sendLocation(float location);

    public void setPaddleListener(PaddleListener paddleListener) {
        this.paddleListener = paddleListener;
    }

    protected enum State {Waiting, Ready}
}
