package model.mob;


import model.weapon.DoubleAxe;
import model.weapon.LongBow;

public class AngelSlayer extends Mob{
    public AngelSlayer(int id){
        this.id = id;
        this.name = "AngelSlayer";
        this.team = 0;
        this.health = 112;
        this.initialHealth = 112;
        this.armor = 26;
        this.weapons.add(new LongBow(this));
        this.weapons.add(new DoubleAxe(this));
        this.speed = 40;
        this.attacksPerRound = 3; // The AngelSlayer can attack 3 creatures in the same round
    }
}
