package com.samuel.simplepong;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Samuel on 2/18/2016.
 */
public class LatencyManager extends NetworkManager {
    private NetworkManager networkManager;
    private Queue<Float> messages, times;
    private float currentTime;
    private float mean, variance;
    private Random random;

    public LatencyManager(NetworkManager networkManager, float mean, float variance) {
        super(null);
        this.networkManager = networkManager;
        currentTime = 0;
        this.mean = mean;
        this.variance = variance;
        messages = new LinkedList<Float>();
        times = new LinkedList<Float>();
        random = new Random();
    }

    @Override
    public void update(float deltaTime) {
        currentTime += deltaTime;
        while (!times.isEmpty() && times.element() < currentTime) {
            times.remove();
            networkManager.sendLocation(messages.remove());
        }
    }

    @Override
    public boolean getSide() {
        return networkManager.getSide();
    }

    @Override
    public void startUp() {
        networkManager.startUp();
    }

    @Override
    public void shutDown() {
        networkManager.shutDown();
    }

    @Override
    public void sendLocation(float location) {
        messages.add(location);
        times.add(currentTime + (mean + (random.nextFloat() * 2.0f - 1.0f) * variance));
    }
}
