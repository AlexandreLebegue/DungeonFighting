package model;

public class Dice {


    private int size;

    public Dice(int size) {
        this.size = size;
    }

    public int getAThrow(){
        double returnValue = (Math.random() * this.size);
        return (int) returnValue;
    }

}
