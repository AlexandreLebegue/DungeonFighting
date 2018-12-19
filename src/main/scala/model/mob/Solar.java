package model.mob;

import model.weapon.GreatSword;
import model.weapon.LongBow;
import model.weapon.Weapon;

import java.util.ArrayList;

public class Solar extends Mob {

    public Solar(){
        this.name = "Solar";
        this.health = 363;
        this.armor = 44;
        this.weapons.add(new GreatSword(this));
        this.weapons.add(new LongBow(this));
        this.canFly = true;
        this.speed = 50;
    }

    public boolean haveToMove(){
        return false;
    }


    @Override
    public String think(ArrayList<Mob> enemy) {
        //System.out.println("Début du tour de "+ name);
        // Mob enemy = determineEnemyToAttack();
        if(this.health <= (363/10)){
            heal(this);
        }

        for(Weapon weapon : weapons) {
            if (weapon.canTouch(enemy.get(0))) {
                //attack(enemy.get(0), weapon);
                return "attack";
            }
        }
        //move(); //else, move the character
        return "move";

    }

    private void heal(Mob mob){
        mob.setHealth(getHealth()+50);
    }
}
