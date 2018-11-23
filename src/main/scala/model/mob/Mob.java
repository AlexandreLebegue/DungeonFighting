package model.mob;

import model.weapon.Weapon;

import java.util.ArrayList;

public abstract class Mob {

    protected String name = "Uknown"; //default value ...
    protected int speed = 15; //default value ...
    protected String type = "Uknown"; //default value ...
    protected int health = 50 ; //default value ...
    protected int armor = 10; //default value ...
    protected ArrayList<Weapon> weapons;
    protected String state = "alive"; //default value ...
    private int[] position;

    public Mob(){
        System.out.println("New mob instancied");
    }

    public Mob(String name, int health, int armor, ArrayList<Weapon> weapons, String type) {
        this.name = name;
        this.health = health;
        this.armor = armor;
        this.weapons = weapons;
        this.type = type;
    }

    public void takeDamage(int dmg){
        if(dmg>=health) {
            state = "dead";
            health = 0;
        }else health -= dmg;
    }

    public void attack(Mob ennemy, String pWeapon){
        Weapon weapon = weapons.get(weapons.indexOf(pWeapon));
        weapon.attackMob(ennemy);
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public boolean isDead(){return state.equals("dead");}

    public int getHealth() {return health;}
    public void setHealth(int health) {this.health = health;}

    public int getArmor() {return armor;}
    public void setArmor(int armor) {this.armor = armor;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public ArrayList<Weapon> getWeapons() {return weapons;}
    public void setWeapons(ArrayList<Weapon> weapons) {this.weapons = weapons;}
    public void addWeapon(Weapon weapon){weapons.add(weapon);}

    public int[] getPosition() {return position;}
    public void setPosition(int[] position) {this.position = position;}

}
