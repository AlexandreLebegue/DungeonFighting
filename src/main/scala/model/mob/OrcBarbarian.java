package model.mob;

import model.weapon.GreatAxe;
import model.weapon.ThrowingAxe;

public class OrcBarbarian extends Mob {

    public OrcBarbarian(int id){
        this.id = id;
        this.name = "OrcBarbarian";
        this.team = 0;
        this.health = 42;
        this.armor = 15;
        this.weapons.add(new GreatAxe(this));
        this.weapons.add(new ThrowingAxe(this));
        this.speed = 30;
    }
}
