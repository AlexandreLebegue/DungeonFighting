package model.mob;

import model.weapon.DoubleAxe;
import model.weapon.LongBow;

public class BarbareOrc extends Mob {
    public BarbareOrc(int id){
        this.id = id;
        this.name = "BarbareOrc";
        this.team = 0;
        this.health = 142;
        this.armor = 17;
        this.weapons.add(new DoubleAxe(this));
        this.weapons.add(new LongBow(this));
        this.speed = 40;
    }
}
