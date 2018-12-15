package model.weapon;

import model.Dice;
import model.mob.Mob;
import model.mob.Solar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class LongBow extends Weapon {

    private Dice d6 = new Dice(6);


    public LongBow(Mob mob) {
        super(mob);
        name = "LongBow";
        precision = new ArrayList<Integer>();
        Collections.addAll(precision, 31, 26, 21, 16);
        damage = 14;
        range  = Integer.MAX_VALUE;
    }

    @Override
    protected int calculDamage(){
        return d6.getAThrow() + d6.getAThrow()+ damage;
    }

}
