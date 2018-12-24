package model.weapon;

import model.Dice;
import model.mob.Mob;

import java.util.ArrayList;
import java.util.Collections;

public class ThrowingAxe extends Weapon {

    private Dice d6 = new Dice(6);

    public ThrowingAxe(Mob mob) {
        super(mob);
        name = "ThrowingAxe";
        precision = new ArrayList<>();
        Collections.addAll(precision, 19);
        damage = 5;
        range  = 20;
    }

    @Override
    protected int computeDamage(){
        return d6.getAThrow()+ damage;
    }
    


}
