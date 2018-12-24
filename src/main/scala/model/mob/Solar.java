package model.mob;

import model.weapon.GreatSword;
import model.weapon.LongBow;

public class Solar extends Mob {

    public Solar(int id){
        this.id = id;
        this.name = "Solar";
        this.team = 1;
        this.health = 363;
        this.armor = 44;
        this.weapons.add(new GreatSword(this));
        this.weapons.add(new LongBow(this));
        this.canFly = true;
        this.canHeal = true;
        this.speed = 50;
        this.attacksPerRound = 4; // The Solar can attack 4 creatures in the same round
    }

    /*@Override
    public String think(ArrayList<Mob> enemy) {
        // Mob enemy = determineEnemiesToAttack();
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

        return "attack";

    }*/

    /*private void heal(Mob mob){
        mob.setHealth(getHealth()+50);
    }*/
}
