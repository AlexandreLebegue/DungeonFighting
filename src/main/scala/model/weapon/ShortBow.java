package model.weapon;

import model.Dice;
import model.mob.Mob;
import model.mob.WorgRider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ShortBow extends Weapon {

    private Dice d6 = new Dice(6);


    public ShortBow(Mob mob) {
        super(mob);
        name = "ShortBow";
        precision = new ArrayList<Integer>();
        Collections.addAll(precision, 4);
        damage = 4;
        range  = 20;
    }

    @Override
    protected int calculDamage(){
        return d6.getAThrow() * 3;
    }
}
