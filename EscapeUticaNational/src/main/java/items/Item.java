package items;

import character.Guard;
import character.Maid;
import interfaces.Inspectable;
import map.Room;

import java.util.ArrayList;

public class Item implements Inspectable {
    private final String name;
    private final String description;
    private final double weight;
    private boolean carry_able;
    private boolean hidden;
    private ArrayList<Item> localItems;
    private Room room; //need a ref to room to get NPCS that send a player back when inspecting items
    private boolean isWeapon;
    private int weapon;
    // constructor
    public Item(String name, String description, double weight, boolean carry_able, boolean hidden, int weapon) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.carry_able = carry_able;
        this.hidden = hidden;
        localItems = new ArrayList<>();
        this.weapon= weapon;
        if(weapon == 0 ){
            this.isWeapon = false;
        }
        else {
            this.isWeapon = true;
        }

    }
    public boolean getisWeapon(){
        return isWeapon;
    }
    public void addLocalItem(Item item) {
        localItems.add(item);
    }

    // check if the item is carryable

    public String isCarryableString() {
        if (carry_able) {
            return "  This item is able to be picked up";
        } else {
            return "  This item is not able to be picked up";
        }
    }

    public void setRoom(Room r) {
        room = r;
    }

    public String subItemString() {
        StringBuilder sub = new StringBuilder();
        if (!localItems.isEmpty()) {
            sub.append("There was something hidden behind that item, you can now see: \n");
            for (Item item : localItems) {
                sub.append(item.toString()).append("\n");
                item.getRoom().addItem(item);
            }
        }
        return sub.toString();
    }


    @Override
    public String toString() {
        return name + "\n  - " + description;
    }

    public String getDescription() {
        return description;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public Room getRoom() {
        return room;
    }

    public boolean getHidden() {
        return hidden;
    }

    public ArrayList<Item> getSubItems() {
        return localItems;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }


    @Override
    public String inspectString() {
        for (Item item : localItems) {
            item.setHidden(false);
            item.getRoom().addItem(item);
        }
        for (Guard guard : room.divideGuardList()) {
            guard.setWithChance();
        }
        for (Maid maid : room.divideMaidList()) {
            maid.setWithChance();
        }
        //subItemViewable = true;
        StringBuilder inspectionStringBuilder = new StringBuilder();
        inspectionStringBuilder.append("Item: ").append(name).append("\n")
                .append("  Description: ").append(description).append("\n")
                .append("  Weight: ").append(weight).append(" lbs\n")
                .append(isCarryableString()).append("\n");
        if(isWeapon){
            inspectionStringBuilder.append("\n    - This item is a weapon with power ").append(weapon);
        }
        // Append sub-items
        inspectionStringBuilder.append(subItemString());

        boolean firstHiddenItem = true;
        for (Item item : localItems) {
            if (item.getHidden()) {
                if (firstHiddenItem) {
                    inspectionStringBuilder.append("Oh! You found a hidden item, you can now see: ");
                    firstHiddenItem = false;
                }
            }
        }

        // Remove the trailing comma and space if there were hidden items
        if (!firstHiddenItem) {
            inspectionStringBuilder.delete(inspectionStringBuilder.length() - 2, inspectionStringBuilder.length());
        }

        // Return the inspection string
        return inspectionStringBuilder.toString();
    }
}

