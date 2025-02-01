package map;

import character.Guard;
import character.Maid;
import character.Player;
import data_structures.BST;
import interfaces.Inspectable;
import items.Item;
import character.Character;

import java.util.ArrayList;
/**
 * Stores all information for Escape Utica National game, Rooms have guards, maids, items and are connected to other
 * Rooms via Doors
 */
public class Room implements Inspectable {
    // Instance variables
    //private boolean isEntered; // need this later
    private Player player;
    private ArrayList<Character> pplInRoom;
    private BST<String, Item> itemsInRoom;
    private ArrayList<Door> doors = new ArrayList<>();
    private final String name;
    private String description;
    private Door north, south, east, west;
    // private boolean entered;

    // Constructor
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.pplInRoom = new ArrayList<>();
        this.itemsInRoom = new BST<>();
    }

    // Method to add a character to the room
    public void addNPC(Character character) {
        pplInRoom.add(character);
        character.setRoom(this);
    }
    // method to add items to the room
    public void addItem(Item item) {
        itemsInRoom.insert(item.getName(),item);
        item.setRoom(this);
    }

    public ArrayList<Guard> divideGuardList(){
        ArrayList<Guard> guardList =new ArrayList<>();
        for (Character character : pplInRoom){
            if (character instanceof Guard) {
                guardList.add((Guard) character);

            }
        }
        return guardList;
    }
    public ArrayList<Maid> divideMaidList(){
        ArrayList<Maid> maidList =new ArrayList<>();
        for (Character character : pplInRoom){
            if (character instanceof Maid) {
                maidList.add((Maid) character);

            }
        }
        return maidList;
    }
    public ArrayList<Character> getPplInRoom(){
        return pplInRoom;
    }

    public void setPlayer(Player p){
        player= p;
    }
    public BST<String, Item> getItemsInRoom(){
        return itemsInRoom;
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }

    public Player getPlayer(){
        return player;
    }
    /*  public void setEntered() {
          if (player != null) {
              entered = true;
          } else {
              entered = false;
          }
      }

     */
    // Override toString method to represent the room's aspects
    @Override
    public String toString() {
        return name;
    }

    // Setter methods for doors
    public void addDoor(Door door, String direction) {
        if (door == null) return; // If the door object is null, do not proceed.

        switch (direction.toLowerCase()) {
            case "north":
                this.north = door;
                break;
            case "south":
                this.south = door;
                break;
            case "west":
                this.west = door;
                break;
            case "east":
                this.east = door;
                break;
            default:
        }

        door.setRoom(this); // Set the current room as the door's location.
        doors.add(door); // Add the door to the list of doors in the room.
    }
    @Override
    public String inspectString() { //room description
        String roomString ="---------------------------------------------------" + "\nRoom: "
                + name + "\n- Description: " + description+"\n" + "\nPeople in the current room:\n";

        if (pplInRoom.isEmpty()) { //returns list of the NPCs in the room
            roomString+="- There are no people in here";
        } else {
            for (Character character : pplInRoom) {
                roomString+="- "+character.toString()+"\n";
            }
        }
        return roomString;
    }
    public void scanString() { //putting the 2 methods together for the parser (probably unnecessary since i could call both methods but whatever)
        String roomString ="---------------------------------------------------" + "\nRoom: "
                + name + "\n- Description: " + description+"\n" + "\nPeople in the current room:\n";

        if (pplInRoom.isEmpty()) { //returns list of the NPCs in the room
            roomString+="- There are no people in here";
        } else {
            for (Character character : pplInRoom) {
                roomString+="- "+character.toString()+"\n";
            }
        }
        String itemString ="Items in the current room:\n";

        if (itemsInRoom.isEmpty()) {
            itemString+="- There are no items in this room";
        } else {
            for (Item item : itemsInRoom) {
                itemString+="- "+item.toString()+"\n";
            }
        }
        System.out.println(roomString+itemString);
    }
    public String inspectForItems() {
        StringBuilder itemString = new StringBuilder("\nItems in the current room:\n");

        if (itemsInRoom.isEmpty()) {
            itemString.append("- There are no items in this room\n");
        } else {
            for (Item item : itemsInRoom) {
                if (!item.getHidden()) {
                    itemString.append("- ").append(item.toString()).append("\n");

                }
            }
        }
        return itemString.toString();
    }



    public String inspectForDoors(){ //returns what doors there are and their direction
        String doorsCheck = "\nDoors: ";
        if(north != null){
            doorsCheck+="\n- "+north.getName()+" to the north";
        }
        if(south != null){
            doorsCheck+= "\n- "+south.getName()+" to the south";
        }
        if(east != null){
            doorsCheck+= "\n- "+east.getName()+" to the east";
        }
        if(west != null){
            doorsCheck+= "\n- "+west.getName()+" to the west";
        }
        if(north == null && south == null && east == null && west == null ){ // just for laughs
            doorsCheck+="\n- There are no Doors... How did you get in there??";
        }
        return doorsCheck+"\n";
    }
    public String getNeighboringRoomsString(){
        StringBuilder result = new StringBuilder();

        if(north != null){
            if(north.getRoom1() != this)
                result.append("- ").append(north.getRoom1().getName()).append(" to the North\n");
            else if(north.getRoom2() != this)
                result.append("- "+north.getRoom2().getName()).append(" to the North\n");
        }
        if(south != null){
            if(south.getRoom1() != this)
                result.append("- "+south.getRoom1().getName()).append(" to the South\n");
            else if(south.getRoom2() != this)
                result.append("- "+south.getRoom2().getName()).append(" to the South\n");
        }
        if(east != null){
            if(east.getRoom1() != this)
                result.append("- "+east.getRoom1().getName()).append(" to the East\n");
            else if(east.getRoom2() != this)
                result.append("- "+east.getRoom2().getName()).append(" to the East\n");
        }
        if(west != null){
            if(west.getRoom1() != this)
                result.append("- "+west.getRoom1().getName()).append(" to the West\n");
            else if(west.getRoom2() != this)
                result.append("- "+west.getRoom2().getName()).append(" to the West\n");
        }
        if(north == null && south == null && east == null && west == null ){
            result.append("- "+"There are no doors... or other rooms connected... how did you get in here");
        }

        return result.toString();
    }
    public Room getOtherRoom() {
        if (north != null) {
            if (north.getRoom1() != this)
                return north.getRoom1();
            else if (north.getRoom2() != this)
                return north.getRoom2();
        }
        if (south != null) {
            if (south.getRoom1() != this)
                return south.getRoom1();
            else if (south.getRoom2() != this)
                return south.getRoom2();
        }
        if (east != null) {
            if (east.getRoom1() != this)
                return east.getRoom1();
            else if (east.getRoom2() != this)
                return east.getRoom2();
        }
        if (west != null) {
            if (west.getRoom1() != this)
                return west.getRoom1();
            else if (west.getRoom2() != this)
                return west.getRoom2();
        }
        // If there are no neighboring rooms
        return null;
    }
    public Room getOtherRoomInNorth() {
        if (north != null) {
            if (north.getRoom1() != this)
                return north.getRoom1();
            else if (north.getRoom2() != this)
                return north.getRoom2();
        }
        return null;
    }

    public Room getOtherRoomInSouth() {
        if (south != null) {
            if (south.getRoom1() != this)
                return south.getRoom1();
            else if (south.getRoom2() != this)
                return south.getRoom2();
        }
        return null;
    }

    public Room getOtherRoomInEast() {
        if (east != null) {
            if (east.getRoom1() != this)
                return east.getRoom1();
            else if (east.getRoom2() != this)
                return east.getRoom2();
        }
        return null;
    }

    public Room getOtherRoomInWest() {
        if (west != null) {
            if (west.getRoom1() != this)
                return west.getRoom1();
            else if (west.getRoom2() != this)
                return west.getRoom2();
        }
        return null;
    }



    public String getName(){
        return name;
    }
    //getters for doors
    public Door getNorthDoor() {
        return north;
    }
    public Door getSouthDoor() {
        return south;
    }
    public Door getWestDoor() {
        return west;
    }
    public Door getEastDoor() {
        return east;
    }

}
