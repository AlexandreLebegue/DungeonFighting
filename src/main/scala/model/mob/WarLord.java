package model.mob;

import model.weapon.GreatSword;
import model.weapon.ThrowingAxe;

public class WarLord extends Mob {

    public WarLord(){
        this.name = "WarLord";
        this.health = 141;
        this.armor = 27;
        //this.weapons.add(new GreatSword()); //Add fist competence ?
        this.weapons.add(new ThrowingAxe());
    }

}
