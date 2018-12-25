package model.mob;

import model.weapon.GreatSword;

public class MovanicDeva extends Mob{
    public MovanicDeva(int id){
        this.id = id;
        this.name = "MovanicDeva";
        this.team = 1;
        this.health = 126;
        this.initialHealth = 126;
        this.armor = 24;
        this.weapons.add(new GreatSword(this));
        this.canFly = true;
        this.speed = 40;
    }
}
