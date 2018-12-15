package model.weapon;

import model.Dice;
import model.mob.Mob;

import java.util.ArrayList;
import java.util.Collections;

public class Warhammer extends Weapon {

    private Dice d8 = new Dice(8);

    public Warhammer(Mob mob) {
        super(mob);
        name = "Warhammer";
        precision = new ArrayList<Integer>();
        Collections.addAll(precision, 26,21,16);
        damage = 14;
        range  = 10;
    }

    @Override
    protected int calculDamage(){
        return d8.getAThrow()+ damage;
    }
}
