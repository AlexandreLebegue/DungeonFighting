package model.weapon;

import model.Dice;
import model.mob.Mob;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GreatSword extends Weapon {

    private Dice d6 = new Dice(6);


    public GreatSword(Mob mob){
        super(mob);
        name = "GreatSword";
        precision = new ArrayList<Integer>();
        Collections.addAll(precision, 35, 30, 25, 20);
        damage =18;
        range = 10;

    }

    private int calculDamage(){
        return d6.getAThrow() + d6.getAThrow() + d6.getAThrow() + damage;
    }
}
