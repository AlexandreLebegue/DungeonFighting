package model.mob;

import model.weapon.GreatSword;
import model.weapon.LongBow;

public class Solar extends Mob {

    public Solar(){
        this.name = "Solar";
        this.health = 363;
        this.armor = 44;
        this.weapons.add(new GreatSword(this));
        this.weapons.add(new LongBow(this));
    }

    public void action(){
        //a decommenterquand on pourras avoir la list des enemie
        /*
        Mob[] enemyList = getEnemyList();
        for(Mob enemy : enemyList) {
        //enemyList.foreach{ enemy =>
            if (getWeapons().get(0).canTouch(enemy)) {
                getWeapons().get(0).attackMob(enemy);
            } else {
                getWeapons().get(0).attackMob(enemy);
            }
        }
        */

    }
}
