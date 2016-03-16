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
        client.getKryo().register(Packet.class);

    }

    @Override
    public boolean getSide() {
        return false;
    }

    @Override
    public void update(float deltaTime) {
        
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
                    //client.sendTCP(new Packet(true));
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

    public void sendLocation(Packet packet) {
        client.sendTCP(packet);
    }

    @Override
    public void received(Connection connection, Object o) {
        if (o instanceof Float) {
            Float receivedFloat = (Float) o;
            Gdx.app.debug("Network", receivedFloat + "");
            if (receivedFloat == -1.0f) {
                listener.onReady(true);
            }
        }
        else if (o instanceof Packet) {
            Packet receivedPacket = (Packet) o;
//            if (receivedPacket.isReady()) {
//                listener.onReady(true);
//            } else

            if (actorListener != null) {
                    Gdx.app.debug("Client", "ClientPaddle");
                //process the received packet: extract timestamp and find the corresponding one in queue,
                //discard the obsolete ones, and do the math with current timestamp, update the current state
                if(SimplePong.clientPredictionOn) processPacketWithPredictionOn(receivedPacket);
                else processPacketWithPredictionOff(receivedPacket);
            }
        }
    }

    private void processPacketWithPredictionOff(Packet packet)
    {
        actorListener.moveServerPaddle(packet.getServerPaddlelocation());
        actorListener.moveClientPaddle(packet.getClientPaddlelocation());
        actorListener.setBall(packet.getBallX(), packet.getBallY()
                , packet.getBalldX(), packet.getBalldY());
    }

    private void processPacketWithPredictionOn(Packet packet)
    {
        actorListener.moveServerPaddle(packet.getServerPaddlelocation());
        actorListener.setBall(packet.getBallX(),packet.getBallY()
                ,packet.getBalldX(),packet.getBalldY());

        float tempTimestamp=packet.getTimestamp();
        while(SimplePong.timestamp>=tempTimestamp) {
            SimplePong.ball.act(0.01f);
            tempTimestamp+=0.01f;
        }

        while(!SimplePong.stateQueue.isEmpty())
        {
            ClientState clientState=SimplePong.stateQueue.poll();
            if(clientState.getTimestamp()<packet.getTimestamp()) continue;
            else if(clientState.getTimestamp()==packet.getTimestamp())
            {
                float offset=packet.getClientPaddlelocation()-
                        clientState.getClientPaddle().getLocation();

                actorListener.moveClientPaddleByOffset(offset);
//
//                float offsetX=packet.getBallLocationX()-
//                        clientState.getBall().x;
//                float offsetY=packet.getBallLocationY()-
//                        clientState.getBall().y;
//
//                actorListener.moveBallByOffset(offsetX,offsetY);

                SimplePong.stateQueue.clear();
                Gdx.app.debug("QueueElementNumber in ClientManager",SimplePong.stateQueue.size()+" ");
            }
        }

    }
}
