package model.mob;

import model.Dice;
import model.weapon.DoubleAxe;
import model.weapon.GreatAxe;
import model.weapon.LongBow;
import model.weapon.ThrowingAxe;

public class OrcBarbarian extends Mob {

    Dice d12 = new Dice(12);


    public OrcBarbarian(){
        this.name = "OrcBarbarian";
        this.health = d12.getAThrow()+d12.getAThrow()+d12.getAThrow()+d12.getAThrow()+16;
        this.armor = 15;
        this.weapons.add(new GreatAxe(this));
        this.weapons.add(new ThrowingAxe(this));
    }

    public boolean haveToMove(){
        return true;
    }

}
