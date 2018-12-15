package model.weapon;

import model.Dice;
import model.mob.Mob;

import java.util.ArrayList;
import java.util.Collections;

public class GreatAxe extends Weapon {

    private Dice d12 = new Dice(12);


    public GreatAxe(Mob mob){
        super(mob);
        name = "GreatAxe";
        precision = new ArrayList<Integer>();
        Collections.addAll(precision, 11);
        damage =10;
        range = 10;

    }

    @Override
    protected int calculDamage(){
        return d12.getAThrow() + damage;
    }


}
