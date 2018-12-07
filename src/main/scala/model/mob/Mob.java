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
    private ArrayList<Mob> enemies = new ArrayList<>();
    private double[] position;

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

    public void attack(Mob ennemy, Weapon weapon){
        weapon.attackMob(ennemy);
    }

    /*
    public void action(Mob[] enemyList){
        //Mob[] enemyList = getEnemyList();
        for(Mob enemy : enemyList) {
            if (this.getWeapons().get(0).canTouch(enemy)) {
                this.getWeapons().get(0).attackMob(enemy);
            } else if ( this.getWeapons().get(0).canTouch(enemy)){
                this.getWeapons().get(1).attackMob(enemy);
            }else if(this.haveToMove()){
                this.move();
            }
        }
    }*/

    protected String think(Mob enemy){
        System.out.println("Début du tour de "+ name);
       // Mob enemy = determineEnemyToAttack();
        for(Weapon weapon : weapons) {
            if (weapon.canTouch(enemy)) {
                attack(enemy, weapon);
                return "attaque";
            }
        }
        move(); //else, move the character
        return "move";
    }


    private Mob determineEnemyToAttack(){
        int lowestLife = Integer.MAX_VALUE;
        Mob currentEnemy = null;
        for(Mob enemy : enemies) {
            if(enemy.isDead()) continue;
            if(enemy.getHealth() < lowestLife){currentEnemy = enemy; }
        }
        return currentEnemy;
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

    public double[] getPosition() {return position;}
    public void setPosition(double[] position) {this.position = position;}

    public void setEnemies(ArrayList<Mob> enemies){this.enemies = enemies;}

    abstract public boolean haveToMove();
    public void move(){
        double movedist=10;
        double[] pos = this.getPosition();
        this.setPosition(pos);
        double angle1=Math.tan(pos[1]/pos[0]);
        double distxy = Math.sqrt(pos[1]*pos[1]+pos[0]*pos[0]);
        double moveground ;
        if(pos[2]!= 0) {
            double angle2 = Math.tan(distxy/pos[2]);
            //double distTot = Math.sqrt(distxy*distxy + pos[2]*pos[2]);
            pos[2] = pos[2]-movedist*Math.acos(angle2);
            moveground = pos[2] * Math.atan(angle2);
        }else{
            moveground = distxy;
        }
        pos[1] = moveground*Math.acos(angle1);
        pos[0] = moveground*Math.asin(angle1);
        this.setPosition(pos);

    }

    //public Mob[] getEnemyList(){}oieeoh

}
