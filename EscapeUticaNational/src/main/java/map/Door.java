package map;

import interfaces.Inspectable;
import items.Key;
/**
 * Doors are the connectors between Rooms, they have a name and description and can be locked or unlocked
 */
public class Door implements Inspectable {
    private final String name;
    private String description;
    private boolean locked;
    private Room r1;
    private Room r2;
    private Key key;
    //constructor
    public Door(String name, String description) {
        this.name = name;
        this.description = description;
    }

    //change key Door value to String doorName, then compare
    public void unlock(Key key) { //unlocked door
        if (key.getDoor().equals(this.name)) {
                locked = false; // checks if the key is for the right door
            } else { //if wrong key is used
                System.out.println("\nThis is not the right key...");
            }
        }
    public void lock(Key key) { //unlocked door
        if (key.getDoor().equals(this.name)) {
            locked = true; // checks if the key is for the right door
        } else { //if wrong key is used
            System.out.println("\nThis is not the right key...");
        }
    }
    public boolean getLocked(){
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setKey(Key k){
        key=k;
    }
    public Key getKey(){
        return key;
    }
    public String getKeyName(){
        return key.getName();
    }

    public void setRoom(Room room) { //setting the room if a door is placed in it, then the 2nd if another is placed
        if (r1 == null) {
            r1 = room;
        } else {
            r2 = room;
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public String getName(){
        return name;
    }

    public Room getRoom1(){
        return r1;
    }
    public Room getRoom2(){
        return r2;
    }
    public String getRooms() { // dont know if i will use this,made for testing door reference
        if (r2 != null) {
            return "- This door connects " + r1.toString() + " and " + r2.toString();
        } else {
            return "- This door is attached to: " + r1.toString();
        }
    }
    public Room getOtherRoom(Room r){
        if (r == r1){
            return r2;
        } else if (r==r2) {
            return r1;
        }
        System.out.println("There is no other room");
        return null;
    }

    @Override
    public String toString(){
        return "\n"+ name + "\n"+description;
    }

    @Override
    public String inspectString() { // inspecting the door shows whether it is locked or not and what rooms its connected to
        String inspect = "Door: " + name + "\n";
        if (locked == true){
            inspect+= "- Status: This door is locked";
        }
        else{
            inspect+= "- Status: This door is unlocked";
        }
        inspect+="\n"+this.getRooms();
        return inspect;
    }
}