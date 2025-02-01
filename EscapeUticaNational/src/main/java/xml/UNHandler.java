package xml;

import character.Guard;
import character.Maid;
import character.Player;
import data_structures.BST;
import items.Item;
import items.Key;
import items.Weapon;
import map.Door;
import map.Room;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.Stack;

import java.util.HashMap;

public class UNHandler extends DefaultHandler {
    private ArrayList<Room> rooms = new ArrayList<>();
    private HashMap<String, Door> doors = new HashMap<>();
    private Room currentRoom;
    private Maid currentMaid;
    private Guard currentGuard;
    private Item currentItem;
    private Key currentKey;
    private Player thePlayer;
    private Stack<Item> itemStack = new Stack<>();
    BST<String, Room> bst = new BST<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName.toLowerCase()) {
            case "room":
                String name = attributes.getValue("name");
                String description = attributes.getValue("description");
                currentRoom = new Room(name, description);
                rooms.add(currentRoom);
                bst.insert(name, currentRoom);
                break;
            case "door":
                handleDoor(attributes);
                break;
            case "maid":
                currentMaid = new Maid(attributes.getValue("name"), attributes.getValue("description"), Boolean.parseBoolean(attributes.getValue("cleaning")),Integer.parseInt(attributes.getValue("type")),Integer.parseInt(attributes.getValue("patience")));
                currentRoom.addNPC(currentMaid);
                break;
            case "player":
                thePlayer = new Player(attributes.getValue("name"), attributes.getValue("description"));
                break;
            case "guard":
                int power = attributes.getValue("power") != null ? Integer.parseInt(attributes.getValue("power")) : 0;
                currentGuard = new Guard(attributes.getValue("name"), attributes.getValue("description"), Boolean.parseBoolean(attributes.getValue("sleeping")),power);
                currentRoom.addNPC(currentGuard);
                break;
            case "item":
                boolean isItemKey = attributes.getValue("isKey") != null ? Boolean.parseBoolean(attributes.getValue("isKey")) : false;
                handleItem(attributes, isItemKey, false, false); // Regular item, specify if it's a key or not
                break;
            case "guarditem":
                boolean isGuardKey = attributes.getValue("isKey") != null ? Boolean.parseBoolean(attributes.getValue("isKey")) : false;
                handleItem(attributes, false, true, isGuardKey); // Guard item, specify if it's a key or not
                break;
        }
    }

    private void handleItem(Attributes attributes, boolean isKey, boolean isGuardItem, boolean isGuardKey) {
        String itemName = attributes.getValue("name");
        String itemDescription = attributes.getValue("description");
        int weight = Integer.parseInt(attributes.getValue("weight"));
        boolean carryable = Boolean.parseBoolean(attributes.getValue("carryable"));
        boolean hidden = Boolean.parseBoolean(attributes.getValue("hidden"));
        int power = attributes.getValue("power") != null ? Integer.parseInt(attributes.getValue("power")) : 0;
        int weapon = attributes.getValue("weapon") != null ? Integer.parseInt(attributes.getValue("weapon")) : 0;
        Item item;
        if (isKey || isGuardKey) {
            item = new Key(itemName, itemDescription, weight, carryable, hidden, attributes.getValue("door"));
        } else if (weapon == 1) {
            // Create a weapon item
            item = new Weapon(itemName, itemDescription, weight, power);
        } else {
            // Create a normal item
            item = new Item(itemName, itemDescription, weight, carryable, hidden, 0);
        }

        if (isGuardItem && currentGuard != null) {
            currentGuard.addToGuardPocket(item); // Add item to guard's pocket
        } else {
            // Check if the item is inside another item
            if (!itemStack.isEmpty()) {
                itemStack.peek().addLocalItem(item);
            }
            currentRoom.addItem(item);
            itemStack.push(item);
        }
    }



    private void handleDoor(Attributes attributes) {
        String doorName = attributes.getValue("name");
        String description = attributes.getValue("description");
        boolean locked = Boolean.parseBoolean(attributes.getValue("locked"));
        String direction = attributes.getValue("direction");
        Door door = doors.get(doorName);

        if (door == null) {
            door = new Door(doorName, description);
            door.setLocked(locked);
            doors.put(doorName, door);
        }
        currentRoom.addDoor(door, direction);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("item") || qName.equals("key")) {
            if (!itemStack.isEmpty()) {
                itemStack.pop();
            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("--------Document successfully read--------");
        thePlayer.setFirstRoom(rooms.get(0));
        thePlayer.setMapBST(bst);
    }


    public void getRooms() {
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    public void searchRoomByName(String roomName) {
        boolean found = false;
        for (Room room : rooms) {
            if (room.getName().equalsIgnoreCase(roomName)) { //equalsIgnoreCase for user to type in upper or lower case
                System.out.println(room);
                room.scanString();
                System.out.println("Neighboring Rooms: \n" + room.getNeighboringRoomsString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Room: '" + roomName + "' does not exist in Utica National");
        }
    }

    public Player getThePlayer() {
        return thePlayer;
    }
}

