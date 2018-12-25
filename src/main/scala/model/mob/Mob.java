package model.mob;

import model.weapon.Weapon;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents a creature
 */
public abstract class Mob implements Serializable
{
    // Here are some attributes defining a Mob ; some of them have default values
    protected int id;
    protected String name;
    protected int team;
    protected int speed = 15;
    protected int health = 50;
    protected int initialHealth = 50;
    protected int armor = 10;
    protected ArrayList<Weapon> weapons = new ArrayList<>();
    protected boolean canFly = false;
    protected boolean canHeal = false;
    protected int attacksPerRound = 1;
    private double[] position;
    private boolean isAlive = true;


    public Mob() {}

    public Mob(String name, int health, int armor, ArrayList<Weapon> weapons)
    {
        this.name = name;
        this.health = health;
        this.armor = armor;
        this.weapons = weapons;
    }


    /**
     * Represents the artificial intelligence of the mobs
     * This AI is quite simple: it chooses the action depending on priorities:
     * - if a member of the team needs healing, then "heal" is the chosen action
     * - else, if an enemy mob can be attacked, then "attack" is the chosen action
     * - else, "move" is the chosen action, to get closer to the enemies
     * @param allMobs ArrayList of all mobs of the fight
     * @return String representing the action to do: "move", "attack", or "heal"
     */
    public String think(ArrayList<Mob> allMobs)
    {
        if(canHeal)
        {
            List<Mob> allies = determineAlliesToHeal(allMobs);
            if(!allies.isEmpty())
            {
                this.canHeal = false; // We can use the heal only once
                return "heal";
            }
        }

        List<Mob> enemies = determineEnemiesToAttack(allMobs);
        for(Mob enemy : enemies)
        {
            for(Weapon weapon : weapons) {
                if (weapon.canTouch(enemy)) return "attack";
            }
        }

        return "move";
    }


    /**
     * Determines the allies that this mob should heal
     * The creature always heal all creatures that have lov health
     * @param allMobs The list of all mobs of the fight
     * @return A List of the mobs that this creature can heal
     */
    public List<Mob> determineAlliesToHeal(ArrayList<Mob> allMobs)
    {
        ArrayList<Mob> aliveAllies = new ArrayList<>();
        if(this.health <= this.initialHealth/3) {aliveAllies.add(this);       //if(name.equals("Solar")) System.out.println("hasHealedHimself");
        }
        for(Mob ally : allMobs)
        {
            // Third condition checks if life is inferior to 1/5 of the initial life of the mob
            if(ally.getTeam() == this.team && !ally.isDead() && ally.health <= (ally.initialHealth/3)) aliveAllies.add(ally);
        }

        return aliveAllies;
    }


    /**
     * Determines the enemies that this mob can currently attack
     * The creature always chooses to attack the creatures with the lowest health level
     * @param allMobs The list of all mobs of the fight
     * @return A List of the mobs that this creature can attack
     */
    public List<Mob> determineEnemiesToAttack(ArrayList<Mob> allMobs)
    {
        // To achieve that, we order the enemies by health level from the lowest to the highest
        // Then we send the best ones, with a maximum of [attacksPerRound] values

        ArrayList<Mob> aliveEnemies = new ArrayList<>();
        for(Mob enemy : allMobs)
        {
            if(enemy.getTeam() != this.team && !enemy.isDead() && canTouchEnemy(enemy)) aliveEnemies.add(enemy);
        }
        aliveEnemies.sort(new MobHealthComparator());

        if(aliveEnemies.size() > attacksPerRound)
            return aliveEnemies.subList(0, attacksPerRound);
        else
            return aliveEnemies;
    }


    /**
     * Determines the enemy towards which the creature should move
     * @param allMobs The list of all mobs of the fight
     * @return The closest mob
     */
    private Mob determineEnemyToComeCloser(ArrayList<Mob> allMobs){
        int lowestDistance = Integer.MAX_VALUE;
        Mob closestEnemy = null;
        for(Mob enemy : allMobs) {
            if(enemy.getTeam() == this.team || enemy.isDead()) continue;
            // Compute the distance between the two mobs
            double dx = enemy.position[0] - this.position[0];
            double dy = enemy.position[1] - this.position[1];
            double dz = enemy.position[2] - this.position[2];
            double distance = Math.sqrt(dx*dx + dy*dy + dz*dz);
            if(distance < lowestDistance){ closestEnemy = enemy; }
        }
        return closestEnemy;
    }


    /**
     * Determines the weapon to use to attack an enemy
     * @param mobToAttack The enemy to attack
     * @return The weapon to use for this attack
     */
    public Weapon determineWeaponToUse(Mob mobToAttack)
    {
        Weapon mostEfficientWeapon = weapons.get(0);
        for(int i=1; i<weapons.size(); i++)
        {
            Weapon weapon = weapons.get(i);
            if(weapon.canTouch(mobToAttack) && weapon.getDamage() > mostEfficientWeapon.getDamage())
                mostEfficientWeapon = weapon;
        }
        return mostEfficientWeapon;
    }


    /**
     * @param enemy The enemy to attack
     * @return true if the creature can touch the enemy, else false
     */
    private boolean canTouchEnemy(Mob enemy)
    {
        for (Weapon weapon : weapons)
        {
            if(weapon.canTouch(enemy)) return true;
        }
        return false;
    }


    /**
     * Gets the move to perform depending on the enemy to approach
     * @return The new position of the current mob
     */
    public double[] getMoveToPerform(ArrayList<Mob> allMobs)
    {
        Mob enemy = determineEnemyToComeCloser(allMobs); // Choose the enemy to move closer to

        double deltaX = enemy.position[0] - this.position[0];
        double deltaY = enemy.position[1] - this.position[1];
        double deltaZ = enemy.position[2] - this.position[2];
        double goalDistance = Math.sqrt(deltaX*deltaX + deltaY*deltaY + deltaZ*deltaZ);
        if(goalDistance > speed) // The enemy is too far
        {
            double ratio = speed / goalDistance;
            double xMove = ratio * deltaX;
            double yMove = ratio * deltaY;
            double zMove = ratio * deltaZ;
            double newX = this.position[0] + xMove;
            double newY = this.position[1] + yMove;
            double newZ = 0.0;
            if(canFly) newZ = this.position[2] + zMove;
            return new double[] { newX, newY, newZ };
        }
        else // The enemy can be reached in one move
        {
            // The following test avoids us to return the enemy Z position (which could be different from 0)
            // if the current mob cannot fly
            // But in both cases, we will decrease x and y positions of the enemy by 1, to avoid collisions between the two
            if(canFly)
                return new double[] { enemy.position[0] - 1, enemy.position[1] - 1, enemy.position[2] };
            else
                return new double[] { enemy.position[0] - 1, enemy.position[1] - 1, 0 };
        }
    }


    /**
     * Performs a move by updating the position of the mob
     * @param newPosition The new position of the mob
     */
    public void move(double[] newPosition) { this.setPosition(newPosition); }


    /**
     * Gets the damage to give to the enemy for an attack
     * @param enemy The enemy to attack
     * @param weapon The weapon to use for the attack
     * @return Damage
     */
    public int getDamageForAttack(Mob enemy, Weapon weapon)
    {
        return weapon.attackMob(enemy);
    }


    /**
     * Performs an attack by updating the health of the mob with the damage it should receive
     * @param damage The damage to receive by this mob
     */
    public void takeDamage(int damage)
    {
        if(damage >= health) health = 0;
        else health -= damage;
    }


    /**
     * @return Healed health to give to an ally for a heal
     */
    public int getHealthToHeal()
    {
        return 100;
    } // Default value for healing


    /**
     * Performs a heal by updating the health of the mob
     * @param healedHealth The healed health to receive by this mob
     */
    public void heal(int healedHealth)
    {
        if(this.health + healedHealth >= this.initialHealth) this.health = initialHealth;
        else this.health += healedHealth;
    }


    /**
     * Generates initial random positions for the mobs of a fight
     * @param everyone The list of all mobs of the fight
     */
    public void generatePosition(ArrayList<Mob> everyone)
    {
        double z=0;

        if (this.canFly)
        {
            double minZ = 0;
            double maxZ = 50;
            z = Math.random() * maxZ + minZ;
        }

        double minX = -200;
        double maxX = 200;

        double minY = -200;
        double maxY = 200;

        boolean positionUnAble = true;
        while(positionUnAble)
        {
            double x = Math.random() * maxX + minX;
            double y = Math.random() * (maxY-minY) + minY;
            double[] pos = {x,y,z};
            if (!isSomeOneThere(pos, everyone))
            {
                this.setPosition(pos);
                positionUnAble = false;
            }
        }

    }


    /**
     * @param pos Desired position
     * @param everyone List of all mobs of the fight which already have a position
     * @return true if a mob is already present at this position, else false
     */
    private boolean isSomeOneThere(double[] pos, ArrayList<Mob> everyone){
        int distMin = 1;
        for(Mob mob : everyone)
        {
            double[] otherMob = mob.getPosition();
            double distX = pos[0] - otherMob[0];
            double distY = pos[1] - otherMob[1];
            double distZ = pos[2] - otherMob[2];
            if(distX*distX + distY*distY + distZ*distZ == distMin*distMin)
                return true;
        }
        return false;
    }


    ////////// Getters, setters, toString & equals methods //////////


    public int getId() { return id; }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getHealth() {return health;}
    public void setHealth(int health) {this.health = health;}
    //public boolean isDead(){ return health <= 0; }

    public int getArmor() {return armor;}
    public void setArmor(int armor) {this.armor = armor;}

    public ArrayList<Weapon> getWeapons() {return weapons;}
    public void setWeapons(ArrayList<Weapon> weapons) {this.weapons = weapons;}

    public double[] getPosition() {return position;}
    public void setPosition(double[] position) {this.position = position;}

    public int getTeam() {return team;}
    public void setTeam(int team) {this.team = team;}

    public boolean isDead() { return !isAlive; }
    public void updateLifeState() { if(health <= 0) isAlive = false; else isAlive = true; }

    @Override
    public String toString()
    {
        return "[" + this.id + "] " + this.name + " : " + this.health + " - ("
                + this.position[0] + " ; " + this.position[1] + " ; " + this.position[2] + ")";
    }

    @Override
    public boolean equals(Object mobToCompare)
    {
        Mob mob = (Mob)mobToCompare;
        return mob.name.equals(this.name) && mob.health == this.health && mob.team == this.team && mob.position[0] == this.position[0]
                && mob.position[1] == this.position[1] && mob.position[2] == this.position[2];
        // No need to compare every attribute, just the main ones
    }



    /**
     * This inner class is used to order a list of mobs from the lowest health to the highest health
     */
    class MobHealthComparator implements Comparator<Mob>
    {
        @Override
        public int compare(Mob m1, Mob m2) {
            if(m1.getHealth() > m2.getHealth()){
                return 1;
            } else {
                return -1;
            }
        }
    }

}
