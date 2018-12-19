package model.mob;

import model.weapon.GreatSword;
import model.weapon.Warhammer;


public class AstralDeva extends Mob{
    public AstralDeva(){
        this.name = "AstralDeva";
        this.health = 172;
        this.armor = 29;
        this.weapons.add(new Warhammer(this));
        this.canFly = true;
        this.speed = 50;
        //warhammer
    }


    public boolean haveToMove(){
        return false;
    }

}
