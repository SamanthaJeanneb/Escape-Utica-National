package items;

public class Weapon extends Item {
    private int power; // Power of the weapon

    public Weapon(String name, String description, double weight, int power) {
        super(name, description, weight, true, false,1); // Call the superclass constructor
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    @Override
    public String inspectString() {
        // Customize inspectString() for weapons if needed
        return super.inspectString() + "\n  - Power: " + power;
    }
}
