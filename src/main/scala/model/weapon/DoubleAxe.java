package model.weapon;

import model.Dice;
import model.mob.BarbareOrc;
import model.mob.Mob;

import java.util.ArrayList;
import java.util.Collections;

public class DoubleAxe extends Weapon {
    private Dice d8 = new Dice(8);

    public DoubleAxe(Mob mob) {
        super(mob);
        name = "DoubleAxe";
        precision = new ArrayList<Integer>();
        Collections.addAll(precision, 19,14,9);
        damage = 10;
        range  = 10;
    }

    @Override
    protected int calculDamage(){
        return (d8.getAThrow()+ damage)*3;
    }

}
