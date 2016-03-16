package com.samuel.simplepong;

import com.badlogic.gdx.Gdx;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Samuel on 2/18/2016.
 */
public class LatencyManager{
    public NetworkManager networkManager;
    private Queue<Packet> messages;
    private Queue<Float>  times;
    private float currentTime;
    private float mean, variance;
    private Random random;

    public LatencyManager(NetworkManager networkManager, float mean, float variance) {
        //super(null);
        this.networkManager = networkManager;
        currentTime = 0;
        this.mean = mean;
        this.variance = variance;
        messages = new LinkedList<Packet>();
        times = new LinkedList<Float>();
        random = new Random();
    }

    public void update(float deltaTime) {
        currentTime += deltaTime;
        //Gdx.app.debug("currentTime", currentTime+" ");

        while (!times.isEmpty() && times.element() < currentTime) {
            //Gdx.app.debug("times.element", times.remove() + " ");
            times.remove();
            networkManager.sendLocation(messages.remove());
        }
    }

    public boolean getSide() {
        return networkManager.getSide();
    }

    public void startUp() {
        networkManager.startUp();
    }

    public void shutDown() {
        networkManager.shutDown();
    }

    public void storeLocation(Packet packet) {

        //Gdx.app.debug("storeLocation", "true");

        messages.add(packet);
        times.add(currentTime + (mean + (random.nextFloat() * 2.0f - 1.0f) * variance));
    }
}
