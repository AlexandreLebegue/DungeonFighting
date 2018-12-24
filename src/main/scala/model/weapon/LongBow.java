package model.weapon;

import model.Dice;
import model.mob.Mob;

import java.util.ArrayList;
import java.util.Collections;

public class LongBow extends Weapon {

    private Dice d6 = new Dice(6);


    public LongBow(Mob mob) {
        super(mob);
        name = "LongBow";
        precision = new ArrayList<>();
        Collections.addAll(precision, 31, 26, 21, 16);
        damage = 14;
        range = 100;
    }

    @Override
    protected int computeDamage(){
        return d6.getAThrow() + d6.getAThrow()+ damage;
    }

}
