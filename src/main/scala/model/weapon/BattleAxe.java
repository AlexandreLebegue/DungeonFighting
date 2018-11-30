package model.weapon;

import model.Dice;
import model.mob.Mob;
import model.mob.WorgRider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class BattleAxe extends Weapon{

    private Dice d8 = new Dice(8);


    public BattleAxe(Mob mob) {
        super(mob);
        name = "BattleAxe";
        precision = new ArrayList<Integer>();
        Collections.addAll(precision, 6);
        damage = 4;
        range  = 10;
    }

    @Override
    protected int calculDamage(){
        return (d8.getAThrow()+2) * 3;
    }

}
