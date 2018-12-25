package model.mob;

import model.weapon.GreatSword;

public class Planetar extends Mob{

    public Planetar(int id){
        this.id = id;
        this.name = "Planetar";
        this.team = 1;
        this.health = 229;
        this.initialHealth = 229;
        this.armor = 32;
        this.weapons.add(new GreatSword(this));
        this.canFly = true;
        this.speed = 30;
    }
}
