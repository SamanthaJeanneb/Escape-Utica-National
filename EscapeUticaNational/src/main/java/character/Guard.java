package character;

import data_structures.BST;
import items.Item;
import map.Room;
import mechanics.*;
import java.util.Random;

/**
 * NPC character that resides in Rooms, can be awake or asleep
 */
public class Guard extends NPC {
    // every guard needs a name, description and a room, and an awake or not variable
    private boolean isAwake;
    private int health;
    private boolean knocked;
    private int power;
    private BST<String, Item> guardPocket;

    public Guard(String name, String description, boolean isAwake,int power) {
        super (name, description);// constructor
        this.isAwake=isAwake;
        health = 10;
        this.power = power;
        guardPocket = new BST<>();
    }

    public void addToGuardPocket(Item i){
        guardPocket.insert(i.getName(),i);
    }
    public void damage(int playerdamage){
        this.health-=playerdamage;
    }

    public int getHealth(){
        return this.health;
    }

    public int attack(){
        return power;
    }

    public boolean isAlive(){
        if(health==0){
            return false;
        }
        else if(health<0){
            return false;
        }
        return true;
    }
//changed to int
    public int setWithChance() {
        if (!isAwake) {
            Random random = new Random();
            int randomNumber = random.nextInt(101);
            if (randomNumber <= 20) {
                System.out.println("Uh-oh.. the guard has noticed you, you've been kicked back to the start");
                return 1;

            }
        }
        return 0;
    }
    public boolean getAwake(){
        return isAwake;
    }

    public void gameEnded() {
        knocked = true;

        System.out.println("You've knocked this guard out, they are now peacefully sleeping");
    }

    @Override
    public String toString() {
        return getName() + "\n    - "+ getDescription();
    }

    public BST<String, Item> getPocketItems(){
        return guardPocket;
    }

    public String inspectString() {

        String guardString;
        if (!isAwake){ 
            guardString= "This guard is awake";
        }
        else {
            guardString = "This guard is asleep";
        }
        return "Guard's name: " + getName() + "\n- Description: " + getDescription()+"\n"+ "- Status: "+ guardString;
    }
}

