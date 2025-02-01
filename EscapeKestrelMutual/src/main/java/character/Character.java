package character;

import interfaces.Inspectable;
import map.Room;
/**
 * Character implements Inspectable Interface, defines what every character should have, Player or NPC
 */
public abstract class Character implements Inspectable {
    private final String name;
    private final String description;
    private Room room; // every character is in a room at some point - including the player

    public Character(String name, String description) {
        this.name=name;
        this.description=description;
    }
    // some getter methods that might need to be used
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public void setRoom(Room room) {
        this.room= room;
    }  //sets the room the character is in
    public Room getRoom(){
        return room;
    }

}