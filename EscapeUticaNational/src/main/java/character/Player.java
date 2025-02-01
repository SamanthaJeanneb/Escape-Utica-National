package character;

import data_structures.BST;
import items.Inventory;
import items.Item;
import items.Key;
import map.Door;
import map.Room;
import mechanics.Actions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import items.*;
public class Player extends Character {
    private ArrayList<Guard> guards;
    private ArrayList<Maid> maids;
    private BST<String, Item> items;
    private ArrayList<Door> doors;
    private BST<String, Room> map;
    private Room room;
    private Inventory inventory = new Inventory();
    private Room firstRoom;
    private boolean cheats;
    private Scanner scanner;
    private Actions actions; 
    private int healthval;
    private int defguards;
    private int weaponsfound;
    public Player(String name, String description) {
        super(name, description);
        cheats = false;
        actions = new Actions(this);
        healthval = 20;//default health is 20
        defguards = 0;
        weaponsfound =0;
    }
    public int getHealthStatus(){
        return healthval;
    }
    public boolean isAlive(){
        if(healthval==0){
            return false;
        }
        else if(healthval<0){
            return false;
        }
        else{
            return true;
        }
    }
    public void takedamage(int dam){
        healthval-=dam;
    }
    public void incdef(){
        defguards+=1;
    }
    public void incwep(){
        weaponsfound+=1;
    }


    public void setFirstRoom(Room r) {
        firstRoom = r;
        moveTo(r);
    }
    public Room getFirstRoom(){
        return firstRoom;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void moveTo(Room r) {
        room = r;
        room.setPlayer(this);
        guards = room.divideGuardList();
        maids = room.divideMaidList();
        items = room.getItemsInRoom();
        doors = room.getDoors();

    }

    public void setMapBST(BST<String, Room> bst) {
        this.map = bst;
    }


    public boolean isGameOver() {
        // Check if the game is over based on certain conditions
        // For example, if the player's health reaches zero
        
        return !isAlive(); // This assumes that the player is considered dead when their health reaches zero
    }
    @Override
    public Room getRoom() {
        return room;
    }
    public void showstas(){
        System.out.println("Total weapons found");
        System.out.print(weaponsfound);
        System.out.print("Total guards defeated");
        System.out.print(defguards);
    }
    public boolean wincodition(){
    //check condition
    Inventory inventory = this.getInventory();
        LinkedList<Item> itemList = inventory.getItemList();

        for (Item item : itemList) {
            if (item.getName().equalsIgnoreCase("server") && getRoom() == firstRoom) {
                return true;
            }
        }
        return false;
    }
public void play(Scanner s) {
    System.out.println("\u001B[92m" + "\n----------------------------- \nYou are now playing Escape Utica National\nType 'objective', 'howtoplay' or type 'help' for a simple list of available commands \n-----------------------------");
    String userInput = "";
    this.scanner = s;
    do{
        //winning condition 
        if(wincodition()){
            showstas();
            System.out.println("Congratulations you won the gameeee");
            break;
        }
        if (!isGameOver()) {
            System.out.print("[Enter Command] > ");
            
            if (scanner.hasNextLine()) {
                userInput = scanner.nextLine().trim();
                processCommand(userInput);
            } else {
                System.out.println("No input provided");
                break;
            }
        } else {
            break; // Exit the loop if the game is over
        }
    } while (!userInput.equalsIgnoreCase("quit") && !userInput.equalsIgnoreCase("exit"));
}
    private void processCommand(String command) {
        if (command.equalsIgnoreCase("help")) {
            displayHelp();
        } else if (command.equalsIgnoreCase("look")) {
            look();
        } else if (command.equalsIgnoreCase("inventory")) {
            showInventory();
        } else if (command.equalsIgnoreCase("north") || command.equalsIgnoreCase("east") ||
                command.equalsIgnoreCase("south") || command.equalsIgnoreCase("west")) {
            move(command);
        } else if (command.toLowerCase().startsWith("inspect")) {
            inspect(command);
        } else if (command.toLowerCase().startsWith("lock")) {
            lock(command);
        } else if (command.toLowerCase().startsWith("unlock")) {
            unlock(command);
        } else if (command.toLowerCase().startsWith("pickup")) {
            pickup(command);
        } else if (command.toLowerCase().startsWith("drop")) {
            drop(command);
        } else if (command.toLowerCase().startsWith("harass")) {
            harass(command);
        } else if (command.toLowerCase().startsWith("objective")) {
            objective();
        } else if (command.equalsIgnoreCase("cheatmode")) {
            enableCheatMode(scanner);
        }  else if (command.equalsIgnoreCase("howtoplay")) {
            howToPlay();
        } else if (command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("exit")) {
            quitGame();
        } else {
            System.out.println("Unknown command: " + command + ". Type 'help' for a list of available commands");
        }
    }

    private void quitGame() {
        System.out.println("Exiting the game. Goodbye!");
        System.exit(0);
    }
    public void howToPlay() {
        printWithDelay("""
            Welcome to Escape Utica National!

            Objective:
            - Infiltrate Utica National Insurance Group's Allied Security (UNIGAS) building.
            - Steal valuable data servers and stay undetected.
            - Navigate through the office, avoid guards, and reach the server room.

            How to Play:
            - Type 'look' to observe your surroundings.
            - Type 'inventory' to check your inventory.
            - Use 'north', 'east', 'south', 'west' to move in respective directions.
            - 'inspect:[entity]' to inspect guards, maids, items, or doors for information.
            - 'lock:[doorname]' to lock a specific door.
            - 'unlock:[doorname]' to unlock a specific door.
            - 'pickup:[itemname]' to pick up an item in the room.
            - 'drop:[itemname]' to drop an item from your inventory.
            - 'harass:[maidname]' to interact with maids for information.
            - 'objective' to review game objectives.
            - 'quit' or 'exit' to leave the game.
            - Use 'cheatmode' if you suck at the game.
            """, 10);
    }

    public void objective() {
        printWithDelay("""
                It's 2024, and Utica, New York, is a tough place to earn a living wage.
                After your friend is fired from his job at Utica National Insurance Group\s
                Allied Security (UNIGAS), the two of you hatch a daring plan to turn your\s
                fortunes around. Using his insider knowledge of the company's security protocols
                he'll help you infiltrate the building.
                
                Your mission: steal valuable data servers housed within, and stay undetected.

                Navigate through the labyrinthine office, avoid detection, and outsmart the security
                guards. The stakes are high and the layout is complex, but your friend's guidance 
                makes the front door the least of your worries. The real challenge lies in reaching 
                the server room and dealing with guard Will Stockhauser —the only person with the necessary
                keycard to grant you access.

                Objectives:
                -  Find Will
                -  Avoid Guards
                -  Get Server Room KeyCard 
                -  Make it back to your friend with the server
                """,10);

    }
    private void displayHelp() {
            printWithDelay("""
                    List of available commands:
                    - 'help': Display this list of commands
                    - 'look': Look around the room
                    - 'inventory': Check your inventory
                    - 'north': Move north
                    - 'east': Move east
                    - 'south': Move south
                    - 'west': Move west
                    - 'quit': quit the game
                    - 'exit': exit the game
                    - 'cheatmode': enter cheatmode
                        -'look:all': looks at all rooms
                        -'look:[roomname]': looks for specific room and prints its contents
                        -'unlockalldoors': unlocks every door
                        -'goto:[roomname]': go to any room in the map
                    - 'inspect:[entity]': Inspect a guard, maid, or item,
                        - inspect NPCs to gain the ability to fight
                    - 'lock:[doorname]': Lock the specified door
                    - 'unlock:[doorname]': Unlock the specified door
                    - 'pickup:[itemname]': Pick up the specified item
                    - 'drop:[itemname]': Drop the specified item
                    - 'harass:[maidname]': harass maids into giving you information
                    - 'objective': game objectives""",10);


    }
    public void printWithDelay(String message, int millisPerChar) { // looks very cool and awesome
        for (char ch : message.toCharArray()) { // Cast string to a character array
            System.out.print(ch); // Print a single character
            try {
                Thread.sleep(millisPerChar); // Delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Failed to complete");
            }
        }
        System.out.println(); // move to the next line after the message is fully printed
    }
    private void look() {
        System.out.println(room.inspectString() + room.inspectForItems() + room.inspectForDoors() + "\nNeighboring Rooms: \n" + room.getNeighboringRoomsString());
    }

    public void showInventory() {
        System.out.println("Available space: " + (Inventory.getMaxWeight() - inventory.getTotalWeight()) + " lbs" + "\nItems in your inventory: ");
        if (inventory.getItemList().isEmpty()) {
            System.out.println("  - There are no items in your inventory");
        }
        for (Item item : inventory.getItemList()) {
            System.out.println(item.toString());
        }
    }

    private void move(String direction) {
        Door door = null;
        switch (direction.toLowerCase()) {
            case "north":
                door = room.getNorthDoor();
                break;
            case "east":
                door = room.getEastDoor();
                break;
            case "south":
                door = room.getSouthDoor();
                break;
            case "west":
                door = room.getWestDoor();
                break;
        }
        if (door != null) {
            if (!door.isLocked()) {
                moveTo(door.getOtherRoom(room));
                System.out.println("You open the door and enter the " + room + ".");
            } else {
                System.out.println("The " + direction + " door is locked, unlock it first to pass");
            }
        } else {
            System.out.println("There is no door in that direction.");
        }
    }
    public void printHealth(){
        System.out.println("Your health: ");
        for (int i = 0; i < this.getHealthStatus(); i++) {
            System.out.print("*");
        }
        System.out.println(" ");
    }
    private void harass(String command) {
        String[] parts = command.split(":", 2);
        if (parts.length == 2) {
            String entityToInspect = parts[1].trim();
            boolean found = false;
            for (Maid maid : maids) {
                if (maid.getName().equalsIgnoreCase(entityToInspect)) {
                    actions.harassMaid(maid,this);
                    found = true;
                    break;
                }
            }
        }
    }
    private void inspect(String command) {
        String[] parts = command.split(":", 2);
        if (parts.length == 2) {
            String entityToInspect = parts[1].trim();
            boolean found = false;
            for (Guard guard : guards) {
                if (guard.getName().equalsIgnoreCase(entityToInspect)) {
                    actions.inspectGuard(guard, this);
                    found = true;
                    break;
                }
            }
            for (Maid maid : maids) {
                if (maid.getName().equalsIgnoreCase(entityToInspect)) {
                    actions.inspectmaid(maid, this);
                    found = true;
                    break;
                }
            }

            for (Item item : items) {
                if (item.getName().equalsIgnoreCase(entityToInspect)) {
                    System.out.println(item.inspectString());
                    found = true;

                    Random rand = new Random();
                    int randomNumber = rand.nextInt(100);
                    if (room.divideGuardList() != null && !room.divideGuardList().isEmpty() && randomNumber < 20) { // 20% chance
                        Guard noticingGuard = room.divideGuardList().getFirst();
                        System.out.println("A guard noticed you inspecting the item!");
                        System.out.println("Do you want to fight the guard? (yes/no)");
                        Scanner scanner = new Scanner(System.in);
                        String choice = scanner.nextLine();
                        if (choice.equalsIgnoreCase("yes")) {
                            if (this.getInventory().isContainsWeapon()) {
                                actions.inspectGuard(noticingGuard, this);
                            } else {
                                System.out.println("You don't have any weapons to fight with.");
                                System.out.println("You've been thrown back to the start.");
                                this.takedamage(1);
                                this.printHealth();
                                this.moveTo(this.getFirstRoom());
                            }
                        } else {
                            System.out.println("You chose not to fight the guard.");
                        }
                    }

                    break;
                }
            }

            for (Door door : doors) {
                if (door.getName().equalsIgnoreCase(entityToInspect)) {
                    System.out.println(door.inspectString());
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Unknown entity: " + entityToInspect);
            }
        } else {
            System.out.println("Invalid inspect command");
        }
    }

    
    private void lock(String command) {
        String[] parts = command.split(":", 2);
        if (parts.length == 2) {
            String doorName = parts[1].trim();
            boolean found = false;
            for (Door door : doors) {
                if (door.getName().equalsIgnoreCase(doorName)) {
                    if (!door.isLocked()) {
                        boolean hasKey = false;
                        for (Item item : inventory.getItemList()) {
                            if (item instanceof Key key) {
                                if (key.getDoor().equalsIgnoreCase(door.getName())) {
                                    door.lock(key);
                                    System.out.println("You lock the door.");
                                    hasKey = true;
                                    break;
                                }
                            }
                        }
                        if (!hasKey) {
                            System.out.println("You don't have the right key");
                        }
                    } else {
                        System.out.println("The door is already locked");
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("No door named " + doorName + " found.");
            }
        } else {
            System.out.println("Invalid lock command");
        }
    }
    
    private void unlock(String command) {
        String[] parts = command.split(":", 2);
        if (parts.length == 2) {
            String doorName = parts[1].trim();
            boolean found = false;
            for (Door door : doors) {
                if (door.getName().equalsIgnoreCase(doorName)) {
                    if (door.isLocked()) {
                        for (Item item : inventory.getItemList()) {
                            if (item instanceof Key key) {
                                if (key.getDoor().equalsIgnoreCase(door.getName())) {
                                    door.unlock(key);
                                    System.out.println("You unlock the door.");
                                    found = true;
                                    break;
                                }
                            }
                        }
                        if (!found) {
                            System.out.println("You don't have the right key");
                        }
                    } else {
                        System.out.println("The door is already unlocked");
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("No door named " + doorName + " found.");
            }
        } else {
            System.out.println("Invalid unlock command");
        }
    }

    public void pickup(String userInput) {
        // Pickup command
        String[] parts = userInput.split(":", 2);
        if (parts.length == 2) {
            String itemName = parts[1].trim();
            boolean found = false;
            for (Item item : items) {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    inventory.add(item);
                    if (inventory.isAdded()) {
                        System.out.println("Picked up " + itemName);
                        room.getItemsInRoom().remove(itemName);
                    }
                    if(item instanceof Weapon){
                        incwep();
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("No item named " + itemName + " found in your current room");
            }
        } else {
            System.out.println("Invalid pickup command");
        }
    }

    private void drop(String command) {
        String[] parts = command.split(":", 2);
        if (parts.length == 2) {
            String itemName = parts[1].trim();
            boolean found = false;
            for (Item item : inventory.getItemList()) {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    inventory.remove(item);
                    System.out.println("Dropped " + itemName);
                    room.addItem(item);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("You don't have " + itemName + " in your inventory.");
            }
        } else {
            System.out.println("Invalid drop command");
        }
    }


    private void enableCheatMode(Scanner scanner) {
        cheats = true;
        while (cheats) {
            System.out.print("[Enter Cheat Command] > ");
            String userInput = scanner.nextLine().trim();
            if (userInput.equalsIgnoreCase("nocheatmode")) {
                cheats = false;
                System.out.println("Exiting cheat mode.");
            } else if (userInput.toLowerCase().startsWith("look")) {
                // Implement look cheat command
                String[] parts = userInput.split(":", 2);
                if (parts.length == 2) {
                    String roomName = parts[1].trim();
                    if (roomName.equalsIgnoreCase("all")) {
                        // Print contents of all rooms using in-order traversal
                        for (Room value : map) {
                            System.out.println(value.inspectString() + value.inspectForItems() + value.inspectForDoors() + "\nNeighboring Rooms: \n" + value.getNeighboringRoomsString());
                        }
                    } else {
                        // Look at specific room
                        boolean found = false;
                        for (Room room : map) {
                            if (room.getName().equalsIgnoreCase(roomName)) {
                                found = true;
                                System.out.println(room.inspectString());
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("No room named " + roomName + " found in the building");
                        }
                    }
                } else {
                    System.out.println("Invalid look command");
                }
            } else if (userInput.equalsIgnoreCase("unlockalldoors")) {
                unlockAllDoors(); // Call method to unlock all doors
            } else if (userInput.toLowerCase().startsWith("goto")) {
                moveToMethod(userInput);
            } else {
                System.out.println("Not a cheat command, use 'nocheatmode' to exit.");
            }
        }
    }

    private void moveToMethod(String userInput) {
        String[] parts = userInput.split(":", 2);
        if (parts.length == 2) {
            String roomName = parts[1].trim();
            boolean found = false;
            for (Room room : map) {
                if (room.getName().equalsIgnoreCase(roomName)) {
                    this.moveTo(room);
                    System.out.println("Moved player to room '" + room.getName() + "'.");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("No room named '" + roomName + "' found.");
            }
        } else {
            System.out.println("Invalid goto command");
        }
    }

    private void unlockAllDoors() {
        for (Room room : map) {
            for (Door door : room.getDoors()) {
                if (door.isLocked()) {
                    if (door.getLocked()) {
                        System.out.println("Door '" + door.getName() + "' unlocked.");
                        door.setLocked(false);
                    }
                }
            }
        }
    }

    @Override
    public String inspectString() {
        return "Player 1 Name: \n" + getName() + "\nDescription: \n" + getDescription();
    }
}
