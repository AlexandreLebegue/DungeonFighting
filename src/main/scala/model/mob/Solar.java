package model.mob;

import model.weapon.GreatSword;
import model.weapon.LongBow;
import model.weapon.Weapon;

public class Solar extends Mob {

    public Solar(){
        this.name = "Solar";
        this.health = 363;
        this.armor = 44;
        this.weapons.add(new GreatSword(this));
        this.weapons.add(new LongBow(this));
    }


    public boolean haveToMove(){
        return false;
    }


    @Override
    protected String think(Mob enemy) {
        System.out.println("Début du tour de "+ name);
        // Mob enemy = determineEnemyToAttack();
        if(this.health <= (363/10)){
            heal(this);
        }

        for(Weapon weapon : weapons) {
            if (weapon.canTouch(enemy)) {
                attack(enemy, weapon);
                return "attaque";
            }
        }
        move(); //else, move the character
        return "move";

    }

    private void heal(Mob mob){
        mob.setHealth(getHealth()+50);
    }
}
