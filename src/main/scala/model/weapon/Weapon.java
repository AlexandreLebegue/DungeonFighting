package model.weapon;

import model.Dice;
import model.mob.Mob;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Weapon {

    private String name;
    private ArrayList<Integer> precision;
    private int damage;
    private HashMap<String, Integer>  specialAbility;
    private Dice d20 = new Dice(20);

    public Weapon(){}

    public Weapon(String name, ArrayList<Integer> precision, int damage, HashMap<String, Integer> specialAbility) {
        this.name = name;
        this.precision = precision;
        this.damage = damage;
        this.specialAbility = specialAbility;
    }

    public boolean canTouch(Mob enemy){
        double x = Math.abs(this.mob.getPosition()[0] - enemy.getPosition()[0]);
        double y = Math.abs(this.mob.getPosition()[1] - enemy.getPosition()[1]);
        double z = Math.abs(this.mob.getPosition()[2] - enemy.getPosition()[2]);
        distXY=sqrt(x*x + y*y);
        dist=sqrt(distXY*distXY+z*z);
        //return if

        return true;
    }

    public void attackMob(Mob enemy){
        for(int i=0; i<precision.size(); i++){
            int roll = (precision.get(i) + d20.getAThrow());
            if(roll > enemy.getArmor()) {
                int damage = calculDamage();
                enemy.takeDamage(damage);
                System.out.println("Hit : " + damage);
            } else {System.out.println("Miss with " + roll + "," +enemy.getArmor());}
        }
    }

    private int calculDamage(){
        return damage;
    }


    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public ArrayList<Integer> getPrecision() {return precision;}
    public void setPrecision(ArrayList<Integer> precision) {this.precision = precision;}

    public int getDamage() {return damage;}
    public void setDamage(int damage) {this.damage = damage;}

    public HashMap<String, Integer> getSpecialAbility() {return specialAbility;}
    public void setSpecialAbility(HashMap<String, Integer> specialAbility) {this.specialAbility = specialAbility;}

}
