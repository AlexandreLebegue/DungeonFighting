package model.weapon;

import model.Dice;
import model.mob.Mob;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Weapon implements Serializable  {

    private static int CRITIQUE = 20;

    protected String name;
    protected ArrayList<Integer> precision;
    protected int damage;
    protected int range = 10;
    protected HashMap<String, Integer>  specialAbility;
    private Dice d20 = new Dice(CRITIQUE);
    private Mob weaponOwner;
    private double distAttack;

    public Weapon(){}

    public Weapon(Mob mob){
        weaponOwner = mob;
    }

    public Weapon(String name, ArrayList<Integer> precision, int damage, HashMap<String, Integer> specialAbility) {
        this.name = name;
        this.precision = precision;
        this.damage = damage;
        this.specialAbility = specialAbility;
    }

    public boolean canTouch(Mob enemy){
        double x = Math.abs(this.weaponOwner.getPosition()[0] - enemy.getPosition()[0]);
        double y = Math.abs(this.weaponOwner.getPosition()[1] - enemy.getPosition()[1]);
        double z = Math.abs(this.weaponOwner.getPosition()[2] - enemy.getPosition()[2]);
        double distXY=Math.sqrt(x*x + y*y);
        double dist=Math.sqrt(distXY*distXY+z*z);
        return dist<this.distAttack;
    }

    public void attackMob(Mob enemy){
        for(int i=0; i<precision.size(); i++){
            int dice = d20.getAThrow();

            int roll = (precision.get(i) + dice);
            if((roll > enemy.getArmor()) || (roll == CRITIQUE)) {
                int damage = calculDamage();
                enemy.takeDamage(damage);
                System.out.println("Hit : " + damage);
            } else {System.out.println("Miss with " + roll + "," +enemy.getArmor());}
        }
    }

    protected int calculDamage(){
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
