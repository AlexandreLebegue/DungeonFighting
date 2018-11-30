package model.mob;

import model.weapon.DoubleAxe;
import model.weapon.LongBow;

public class BarbareOrc extends Mob {
    public BarbareOrc(){
        this.name = "BarbareOrc";
        this.health = 142;
        this.armor = 17;
        this.weapons.add(new DoubleAxe(this));
        this.weapons.add(new LongBow(this));
    }


    public boolean haveToMove(){
        return true;
    }



}
