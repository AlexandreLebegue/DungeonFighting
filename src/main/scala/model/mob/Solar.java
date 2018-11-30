package model.mob;

import model.weapon.GreatSword;
import model.weapon.LongBow;

public class Solar extends Mob {

    public Solar(){
        this.name = "Solar";
        this.health = 363;
        this.armor = 44;
        this.weapons.add(new GreatSword(this));
        this.weapons.add(new LongBow(this));
    }


    public boolean haveToMove(){
        return false;
    }



}
