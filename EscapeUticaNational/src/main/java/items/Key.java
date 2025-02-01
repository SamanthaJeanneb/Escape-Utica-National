package items;

import map.Door;
/**
 * Extends Item class, can be used to unlock doors
 */
public class Key extends Item{

    private String door;
    private final String doorRef; //String representation of door key belongs to
    public Key(String name, String description, double weight, boolean carry_able, boolean hidden, String door) {
        super(name, description, weight, carry_able, hidden, 0);
        this.door=door;
        doorRef= door.toString();
    }


    //inspect the key to see what door it is for
    @Override
    public String toString(){
        return getName() + " for "+door+ "\n  - "+ getDescription();
    }

    @Override
    public String inspectString(){
        return "Item: " + getName() + "\n  Description: " + getDescription() + "\n  Weight: " + getWeight() + " lbs" + "\n" + this.isCarryableString() +  "\nThis key is for: "+ "\n"+ door;
    }
    public String getDoor() {
        return door;
    }
}
