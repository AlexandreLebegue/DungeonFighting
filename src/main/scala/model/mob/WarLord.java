package model.mob;

import model.weapon.GreatSword;
import model.weapon.ThrowingAxe;

public class WarLord extends Mob {

    public WarLord(){
        this.name = "WarLord";
        this.health = 141;
        this.armor = 27;
        //this.weapons.add(new GreatSword()); //Add fist competence ?
        this.weapons.add(new ThrowingAxe(this));
    }

    public boolean haveToMove(){
        return true;
    }

    public void move(){
        int[] pos = this.getPosition();
        pos[0]= pos[0]-10;
        pos[1]= pos[1]-10;
        this.setPosition(pos);
    }


}
