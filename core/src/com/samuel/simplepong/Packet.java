package com.samuel.simplepong;

/**
 * Created by Johnny on 04/03/2016.
 */
public class Packet {
    private float location;
    private boolean side;

    public Packet(float location,boolean side)
    {
        this.location=location;
        this.side=side;
    }

    public float getLocation() {
        return location;
    }

    public boolean getSide(){
        return side;
    }

    public void setLocation(float location) {
        this.location = location;
    }

    public void setSide(boolean side) {
        this.side = side;
    }
}
