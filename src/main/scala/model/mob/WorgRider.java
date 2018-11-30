package model.mob;

import model.weapon.BattleAxe;
import model.weapon.ShortBow;

public class WorgRider extends Mob {

    public WorgRider(){
        this.name = "WorgRider";
        this.health = 13;
        this.armor = 18;
        this.weapons.add(new BattleAxe(this));
        this.weapons.add(new ShortBow(this));
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

