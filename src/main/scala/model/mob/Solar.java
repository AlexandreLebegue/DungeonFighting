package model.mob;

import model.weapon.GreatSword;
import model.weapon.LongBow;

public class Solar extends Mob {

    public Solar(int id){
        this.id = id;
        this.name = "Solar";
        this.team = 1;
        this.health = 363;
        this.initialHealth = 363;
        this.armor = 44;
        this.weapons.add(new GreatSword(this));
        this.weapons.add(new LongBow(this));
        this.canFly = true;
        this.canHeal = true;
        this.speed = 50;
        this.attacksPerRound = 6; // The Solar can attack 6 creatures in the same round
    }
}
