package model.mob;

import model.weapon.Warhammer;


public class AstralDeva extends Mob{
    public AstralDeva(int id){
        this.id = id;
        this.name = "AstralDeva";
        this.team = 1;
        this.health = 172;
        this.armor = 29;
        this.weapons.add(new Warhammer(this));
        this.canFly = true;
        this.speed = 50;
    }
}
