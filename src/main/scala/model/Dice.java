package model;

import java.io.Serializable;

public class Dice implements Serializable {


    private int size;

    public Dice(int size) {
        this.size = size;
    }

    public int getAThrow(){
        double returnValue = (Math.random() * this.size);
        return (int) returnValue;
    }

}
