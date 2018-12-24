package model.weapon;

import model.Dice;
import model.mob.Mob;

import java.util.ArrayList;
import java.util.Collections;

public class Bite extends Weapon
{
    private Dice d8 = new Dice(8);

    public Bite(Mob mob)
    {
        super(mob);
        name = "Bite";
        precision = new ArrayList<>();
        Collections.addAll(precision, 50); // Default value
        damage = 10; // Default value
        range = 50; // Default value
    }

    @Override
    protected int computeDamage(){
        return d8.getAThrow() + d8.getAThrow() + d8.getAThrow() + d8.getAThrow() + damage;
    }
}
