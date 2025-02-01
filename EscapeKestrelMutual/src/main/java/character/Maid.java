package character;

import java.util.Random;

/**
 * NPC Character that cannot move between rooms, can be cleaning or not cleaning
 */
public class Maid extends NPC{
    private int characterType;
    private int patience;
    private boolean isCleaning;
    public Maid(String name, String description, boolean isCleaning, int characterType, int patience) {
        super(name, description);
        this.isCleaning=isCleaning;
        this.characterType = characterType;
        this.patience = patience;
    }

    public boolean getStatus(){//will need this later
        return isCleaning;
    }
    public int setWithChance() {
        if (!isCleaning) {
            Random random = new Random();
            int randomNumber = random.nextInt(101);
            if (randomNumber <= 20) {
                System.out.println("The maid jumps as she sees you, she has the nearest guard take you back to the start");
                return 1;
            }
           
        }
        return 0;
    }
    public void reducePatience(){
        patience--;
    }

    @Override
    public String inspectString(){
        setWithChance();
        String maidString;
        if (isCleaning == true){
            maidString= "This maid is cleaning ";
        }
        else {
            maidString = getName() +" is walking around the room, you're afraid she might see you";
        }
        return "Maid's name: " + getName() + "\n- Description: " + getDescription()+"\n"+ "- Status: "+ maidString; //similar to guard but also says if they are cleaning
    }

    @Override
    public String toString(){
        return getName() + "\n    Description: \n     - "+ getDescription();
    }

    public int getcharacterType() {
        return characterType;
    }

    public int getPatience() {
        return patience;
    }
}
