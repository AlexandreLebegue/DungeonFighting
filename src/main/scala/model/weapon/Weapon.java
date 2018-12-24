package model.weapon;

import model.Dice;
import model.mob.Mob;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a weapon, or an attack like bite or tail sweep
 */
public abstract class Weapon implements Serializable
{
    private static int CRITIQUE = 20;

    protected String name;
    protected ArrayList<Integer> precision;
    protected int damage;
    protected int range = 10;
    private Dice d20 = new Dice(CRITIQUE);
    private Mob weaponOwner;

    public Weapon(){}

    public Weapon(Mob mob) { weaponOwner = mob; }

    public Weapon(String name, ArrayList<Integer> precision, int damage) {
        this.name = name;
        this.precision = precision;
        this.damage = damage;
    }


    /**
     * @param enemy The enemy to attack
     * @return true if the enemy can be touched, else false
     */
    public boolean canTouch(Mob enemy)
    {
        double x = Math.abs(this.weaponOwner.getPosition()[0] - enemy.getPosition()[0]);
        double y = Math.abs(this.weaponOwner.getPosition()[1] - enemy.getPosition()[1]);
        double z = Math.abs(this.weaponOwner.getPosition()[2] - enemy.getPosition()[2]);
        double distance = Math.sqrt(x*x + y*y + z*z);
        return distance < this.range;
    }


    /**
     * @param enemy The enemy to attack
     * @return The damage the enemy has to take (can be 0 if the enemy has been missed)
     */
    public int attackMob(Mob enemy){
        int bestDamage=0;
        for(int i=0; i<precision.size(); i++){
            int dice = d20.getAThrow();

            int roll = (precision.get(i) + dice);
            if((roll > enemy.getArmor()) || (roll == CRITIQUE)) {
                int damage = computeDamage();
                if(damage > bestDamage) bestDamage = damage;
                return damage;
            } // else { System.out.println("Miss with " + roll + "," + enemy.getArmor()); }
        }
        return bestDamage;
    }


    ////////// Getters and setters //////////

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public ArrayList<Integer> getPrecision() {return precision;}
    public void setPrecision(ArrayList<Integer> precision) {this.precision = precision;}

    public int getDamage() {return damage;}
    public void setDamage(int damage) {this.damage = damage;}
    protected int computeDamage() { return damage; }
}
