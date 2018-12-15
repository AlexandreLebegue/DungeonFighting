package model.mob;

import model.weapon.GreatSword;
import model.weapon.LongBow;

import java.io.Serializable;

public class RedGreatWyrmDragon extends Mob implements Serializable {
    public RedGreatWyrmDragon(){
        this.name = "RedGreatWyrmDragon";
        this.health = 391;
        this.armor = 37;
        //this.weapons.add(new GreatSword(this));
        //this.weapons.add(new LongBow(this));
        //bite and tail sweep
    }


    public boolean haveToMove(){
        return false;
    }



}
