package com.samuel.simplepong;

/**
 * Created by Johnny on 04/03/2016.
 */
public class Packet {

    private float timestamp;
    private float clientPaddlelocation;
    private float serverPaddlelocation;
    private float ballX;
    private float ballY;
    private float balldX;
    private float balldY;
    private boolean side;

    public Packet()
    {
    }

    public Packet(float serverPaddlelocation,float clientPaddlelocation
    ,float ballX,float ballY, float balldX, float balldY, float timestamp)
    {
        setBalldX(balldX);
        setBalldY(balldY);
        setBallX(ballX);
        setBallY(ballY);
        setClientPaddlelocation(clientPaddlelocation);
        setServerPaddlelocation(serverPaddlelocation);
        setTimestamp(timestamp);
    }

    public Packet(float clientPaddlelocation, float timestamp)
    {
        setClientPaddlelocation(clientPaddlelocation);
    }

    public void setTimestamp(float timestamp) {
        this.timestamp = timestamp;
    }

    public float getTimestamp() {
        return timestamp;
    }

    public float getBalldX() {
        return balldX;
    }

    public float getBalldY() {
        return balldY;
    }

    public float getBallX() {
        return ballX;
    }

    public float getBallY() {
        return ballY;
    }

    public void setBalldX(float balldX) {
        this.balldX = balldX;
    }

    public void setBalldY(float balldY) {
        this.balldY = balldY;
    }

    public void setBallX(float ballX) {
        this.ballX = ballX;
    }

    public void setBallY(float ballY) {
        this.ballY = ballY;
    }

    public float getClientPaddlelocation() {
        return clientPaddlelocation;
    }

    public void setClientPaddlelocation(float clientPaddlelocation) {
        this.clientPaddlelocation = clientPaddlelocation;
    }

    public float getServerPaddlelocation() {
        return serverPaddlelocation;
    }

    public void setServerPaddlelocation(float serverPaddlelocation) {
        this.serverPaddlelocation = serverPaddlelocation;
    }
}
