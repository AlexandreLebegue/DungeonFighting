package model.mob;

import model.weapon.Bite;
import model.weapon.TailSweep;

import java.io.Serializable;

public class RedGreatWyrmDragon extends Mob implements Serializable {
    public RedGreatWyrmDragon(int id){
        this.id = id;
        this.name = "RedGreatWyrmDragon";
        this.team = 0;
        this.health = 449;
        this.initialHealth = 449;
        this.armor = 39;
        this.canFly = true;
        this.speed = 40;
        this.weapons.add(new Bite(this));
        this.weapons.add(new TailSweep(this));
    }

}
