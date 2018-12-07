package model.mob;

import model.weapon.GreatSword;


public class AstralDeva extends Mob{
    public AstralDeva(){
        this.name = "AstralDeva";
        this.health = 172;
        this.armor = 29;
        //this.weapons.add(new GreatSword(this));
        //warhammer
    }


    public boolean haveToMove(){
        return false;
    }

}
