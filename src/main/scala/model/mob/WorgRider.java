package model.mob;

import model.weapon.BattleAxe;
import model.weapon.ShortBow;

public class WorgRider extends Mob {

    public WorgRider(){
        this.name = "WorgRider";
        this.health = 13;
        this.armor = 18;
        this.weapons.add(new BattleAxe());
        this.weapons.add(new ShortBow());
    }


    public void action(){
        //recuper solar
        /*
        Solar solar = getSolar();
        if (getWeapons().get(0).canTouch(solar)){
            getWeapons().get(0).attackMob(solar);
        }else if(getWeapons().get(1).canTouch(solar)){
            getWeapons().get(1).attackMob(solar);
        }
        //ajouter deplacement
        */
    }
}

