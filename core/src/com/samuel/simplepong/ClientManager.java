package com.samuel.simplepong;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

import java.net.InetAddress;

/**
 * Created by SamuelDong on 2/16/16.
 */
public class ClientManager extends NetworkManager {
    private Client client;

    public ClientManager(ReadyListener listener) {
        super(listener);
        client = new Client();
        client.getKryo().register(Float.class);
    }

    @Override
    public boolean getSide() {
        return false;
    }

    @Override
    public void startUp() {
        client.start();
        client.addListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                InetAddress discovered = client.discoverHost(7777, 10000);
                try {
                    client.connect(10000, discovered, 5555, 7777);
                    Gdx.app.debug("ES", "ES");
                    client.sendTCP(-1.0f);
                } catch (Exception e) {
                    Gdx.app.debug("S", e.getMessage());
                    listener.onReady(false);
                }
            }
        }).start();
    }

    @Override
    public void shutDown() {
        client.removeListener(this);
        client.close();
    }

    @Override
    public void sendLocation(float location) {
        client.sendTCP(location);
    }

    @Override
    public void received(Connection connection, Object o) {
        if (o instanceof Float) {
            float received = (Float) o;
            Gdx.app.debug("Network", received + "");
            if (received == -1.0f) {
                listener.onReady(true);
            } else if (paddleListener != null) {
                paddleListener.movePaddle(received);
            }
        }
    }
}
