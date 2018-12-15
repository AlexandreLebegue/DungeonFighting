package model.mob;

import model.weapon.GreatSword;
import model.weapon.LongBow;

public class MovanicDeva extends Mob{
    public MovanicDeva(){
        this.name = "MovanicDeva";
        this.health = 126;
        this.armor = 24;
        this.weapons.add(new GreatSword(this));
    }


    public boolean haveToMove(){
        return false;
    }

}
