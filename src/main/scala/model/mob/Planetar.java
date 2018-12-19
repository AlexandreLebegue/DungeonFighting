package model.mob;

import model.weapon.GreatSword;
import model.weapon.LongBow;

public class Planetar extends Mob{

    public Planetar(){
        this.name = "Planetar";
        this.health = 229;
        this.armor = 32;
        this.weapons.add(new GreatSword(this));
        this.canFly = true;
        this.speed = 30;
    }

    public boolean haveToMove(){
        return false;
    }

}
