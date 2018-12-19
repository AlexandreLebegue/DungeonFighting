package model.mob;


import model.weapon.DoubleAxe;
import model.weapon.LongBow;

public class AngelSlayer extends Mob{
    public AngelSlayer(){
        this.name = "AngelSlayer";
        this.health = 112;
        this.armor = 26;
        this.weapons.add(new LongBow(this));
        this.weapons.add(new DoubleAxe(this));
        this.speed = 40;
    }


    public boolean haveToMove(){
        return false;
    }

}
