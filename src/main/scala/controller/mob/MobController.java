package controller.mob;

import model.mob.Mob;

import java.util.ArrayList;

public class MobController {

    Mob mob;
    ArrayList<Mob> enemies;

    public MobController(Mob mob, ArrayList<Mob> enemies){

        this.mob = mob;
        this.enemies = enemies;

    }

    public void think(){
     System.out.println("Début du tour de "+ mob.getName());

/*
    GetAllEnemyLife
    DetermineWhichEnemyToAttack ---> get an ennemy with the lowest life and can attack and not dead yet else move to the best enemy
                                ---> return int represent the index of the enemy in arrayList and if i = -5 then move
    DoAction


 */


    System.out.println("Fin du tour de "+ mob.getName());


    }

    public ArrayList<Integer> getAllEnemyLife(){

        ArrayList<Integer> result = new ArrayList<>();

       // for(Mob enemy :  enemies)
           // result.add(enemy.get)
        return null;
    }

}
