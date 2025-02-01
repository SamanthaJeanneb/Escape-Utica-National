package items;

import java.util.LinkedList;

public class Inventory {
    private LinkedList<Item> itemList;
    private double totalWeight;
    private static final double maxWeight = 25.0;
    private boolean added;
    private boolean containsWeapon;

    public Inventory(){
        totalWeight=0;
        itemList = new LinkedList<>();
        containsWeapon = false;
    }

    public void remove(Item i){
        itemList.remove(i);
        totalWeight = totalWeight - i.getWeight();
    }
    public void add(Item i){
        double weightNum = totalWeight+i.getWeight();
        if(weightNum>maxWeight){
            System.out.println("This item is too heavy, try dropping another item");
            setAdded(false);
        }
        else{
            totalWeight=weightNum;
            itemList.add(i);
            if (i.getisWeapon()){
                containsWeapon = true;
            }
            setAdded(true);
        }
    }

    public boolean isContainsWeapon() {
        return containsWeapon;
    }

    public void setAdded(boolean b){
        added = b;
    }

    public boolean isAdded() {
        return added;
    }

    public static double getMaxWeight() {
        return maxWeight;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public LinkedList<Item> getItemList() {
        return itemList;
    }

    public String toString(){
        return "---------------------------\n Inventory \n  -Capacity: "+maxWeight;
    }

}
