package model;

import java.io.Serializable;

/**
 * This class represents a Dice
 * It is used when performing attacks
 */
public class Dice implements Serializable
{
    private int size;

    public Dice(int size) { this.size = size; }

    /**
     * @return Value of the dice
     */
    public int getAThrow()
    {
        double returnValue = (Math.random() * this.size);
        return (int) returnValue;
    }
}
