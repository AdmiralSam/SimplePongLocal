package com.samuel.simplepong;

import com.esotericsoftware.kryonet.Listener;

/**
 * Created by SamuelDong on 2/16/16.
 */
public abstract class NetworkManager extends Listener {
    protected State currentState;
    protected ReadyListener listener;
    protected ActorListener actorListener;

    protected NetworkManager(ReadyListener listener) {
        currentState = State.Waiting;
        this.listener = listener;
    }

    public abstract void update(float deltaTime);

    public abstract boolean getSide();

    public abstract void startUp();

    public abstract void shutDown();

    public abstract void sendLocation(Packet packet);

    public void setActorListener(ActorListener actorListener) {
        this.actorListener = actorListener;
    }

    protected enum State {Waiting, Ready}
}
