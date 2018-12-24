package model.mob;

import model.weapon.ThrowingAxe;

public class WarLord extends Mob {

    public WarLord(int id){
        this.id = id;
        this.name = "WarLord";
        this.team = 0;
        this.health = 141;
        this.armor = 27;
        this.weapons.add(new ThrowingAxe(this));
        this.speed = 30;
    }

}
