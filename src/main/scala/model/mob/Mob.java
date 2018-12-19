package model.mob;

import model.weapon.Weapon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public abstract class Mob implements Serializable {

    protected String name = "Uknown"; //default value ...
    protected int speed = 15; //default value ...
    protected String type = "Uknown"; //default value ...
    protected int health = 50 ; //default value ...
    protected int armor = 10; //default value ...
    protected ArrayList<Weapon> weapons = new ArrayList<>();
    protected String state = "alive"; //default value ...
    protected boolean canFly=false; //default value ...
    private int team = 0;
    private ArrayList<Mob> enemies = new ArrayList<>();
    private double[] position;
    public static ArrayList<Mob> everyone = new ArrayList<>();

    public Mob(){
        //System.out.println("New mob instancied");
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

    public boolean canTouchEnemy(Mob enemy)
    {
        for (Weapon weapon : weapons) {
            if(weapon.canTouch(enemy)) return true; }
        return false;
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

    public String think(ArrayList<Mob> enemy){
        //System.out.println("Début du tour de "+ name);*
        //System.out.println("Enemy = " + enemy.getName());
        // Mob enemy = determineEnemyToAttack();
        for(Weapon weapon : weapons) {
            if (weapon.canTouch(enemy.get(0))) {
                //attack(enemy.get(0), weapon);
                return "attack";
            }
        }
        //move(); //else, move the character
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

    private Mob determineEnemyToComeCloser(){
        int lowestDistance = Integer.MAX_VALUE;
        Mob closestEnemy = null;
        for(Mob enemy : enemies) {
            if(enemy.isDead()) continue;
            // Compute the distance between the two mobs
            double dx = enemy.position[0] - this.position[0];
            double dy = enemy.position[1] - this.position[1];
            double dz = enemy.position[2] - this.position[2];
            double distance = Math.sqrt(dx*dx + dy*dy + dz*dz);
            if(distance < lowestDistance){closestEnemy = enemy; }
        }
        return closestEnemy;
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

    public int getTeam() {return team;}
    public void setTeam(int team) {this.team = team;}


    /**
     * Moves the mob depending on the enemy to approach
     * TODO Gérer cas où deux mobs sont pile poil à la même position (mais vu qu'on est sur des doubles, c'est rarissime, donc à voir à la fin)
     * @return The new position of the current mob
     */
    public double[] move()
    {
        // Choose the enemy to move closer to
        Mob enemy = determineEnemyToComeCloser();

        // Compute the movement vector
        double x = enemy.position[0] - this.position[0];
        double y = enemy.position[1] - this.position[1];
        double z = enemy.position[2] - this.position[2];
        double norm = Math.sqrt(x*x + y*y + z*z);
        double[] movement = { this.speed*x/norm , this.speed*y/norm , this.speed*z/norm };

        double newX = this.position[0] + movement[0];
        double newY = this.position[1] + movement[1];
        double newZ;
        if(canFly)
            newZ = this.position[2] + movement[2];
        else
            newZ = 0;
        double[] newPosition = { newX , newY , newZ };

        this.setPosition(newPosition);
        return newPosition;
    }

    public void generatePos(ArrayList<Mob> everyone){
        double z=0;


        if (this.canFly) {
            double minZ = 0;
            double maxZ = 50;
            z = Math.random() * maxZ + minZ;
        }
        double minX=2;
        double maxX=50;

        double minY=-50;
        double maxY=50;

        boolean positionUnAble = true;
        while(positionUnAble){

            double x=Math.random() * maxX + minX;
            double y=Math.random() * (maxY-minY) + minY;
            double[] pos={x,y,z};
            if (!isSomeOneThere(pos, everyone)){
                this.setPosition(pos);
                positionUnAble = false;
            }
        }

    }

    public boolean isSomeOneThere(double[] pos, ArrayList<Mob> everyone){
        int distMin=1;
        for(Mob mob : everyone) {
            double[] othermob = mob.getPosition();
            double[] me = this.getPosition();
            double distX=me[0]-othermob[0];
            double distY=me[1]-othermob[1];
            double distZ=me[2]-othermob[2];
            if(distX*distX + distY*distY + distZ*distZ== distMin*distMin){
                return true;
            }
        }
        return false;
    }


}
