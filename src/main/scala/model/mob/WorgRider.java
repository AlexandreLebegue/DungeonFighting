package model.mob;

import model.weapon.BattleAxe;
import model.weapon.ShortBow;

public class WorgRider extends Mob {

    public WorgRider(int id){
        this.id = id;
        this.name = "WorgRider";
        this.team = 0;
        this.health = 13;
        this.armor = 18;
        this.weapons.add(new BattleAxe(this));
        this.weapons.add(new ShortBow(this));
        this.speed = 20;
    }

}

