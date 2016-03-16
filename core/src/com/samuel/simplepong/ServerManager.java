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
        server.getKryo().register(Packet.class);
    }

    @Override
    public void update(float deltaTime) {

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

    public void sendLocation(Packet packet) {
        if(connection==null) return;
        connection.sendTCP(packet);
    }

    @Override
    public void received(Connection connection, Object o) {
        this.connection = connection;
        if (o instanceof Float) {
            float receivedFloat = (Float) o;
            Gdx.app.debug("Network", receivedFloat + "");
            if (receivedFloat == -1.0f) {
                listener.onReady(true);
                connection.sendTCP(-1.0f);
            }
        }
        else if (o instanceof Packet) {
            Packet receivedPacket = (Packet) o;

//            if (receivedPacket.isReady()) {
//                listener.onReady(true);
//                connection.sendTCP(new Packet(true));
//            } else

            if (actorListener != null) {
                actorListener.moveClientPaddle(receivedPacket.getClientPaddlelocation());
            }
        }
    }
}
