package com.samuel.simplepong;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 * Created by SamuelDong on 2/16/16.
 */
public class ServerManager extends NetworkManager {
    private Server server;
    private Connection connection;

    public ServerManager(ReadyListener listener) {
        super(listener);
        server = new Server();
        server.getKryo().register(Float.class);
    }

    @Override
    public boolean getSide() {
        return true;
    }

    @Override
    public void startUp() {
        server.start();
        server.addListener(this);
        try {
            server.bind(5555, 7777);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutDown() {
        server.removeListener(this);
        server.close();
    }

    @Override
    public void sendLocation(float location) {
        connection.sendTCP(location);
    }

    @Override
    public void received(Connection connection, Object o) {
        this.connection = connection;
        if (o instanceof Float) {
            float received = (Float) o;
            Gdx.app.debug("Network", received + "");
            if (received == -1.0f) {
                listener.onReady(true);
                connection.sendTCP(-1.0f);
            }
            else if (paddleListener != null){
                paddleListener.movePaddle(received);
            }
        }
    }
}
