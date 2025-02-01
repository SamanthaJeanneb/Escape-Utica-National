package mechanics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import character.Guard;
import character.Maid;
import character.Player;/* Starting design of actions, a first attempt at splitting up the play method, and also allowing
attacking NPCs */
import items.Item;
import items.Weapon;
import map.Door;
import items.*;
import items.Inventory;
public class Actions {
    private Player player;

    public Actions(Player player) {
        this.player = player;
    }


    // Performs an attack action on the specified target if the player has a weapon
    // If the player has no weapon, notifies the user.

public void harassMaid(Maid maid, Player p ){
        maid.reducePatience();
        if (maid.getcharacterType()==1 && maid.getPatience() == 1) {
            player.printWithDelay("\nThe maid is annoyed by your harassment, she decides to give you information\n\n\"FINE! the server room is to the West, " +
                    "\nthen North, then North again, West through the cubicles, then West again\"\n\n",10);
        } else if (maid.getcharacterType()==2 && maid.getPatience() == 1){
            player.printWithDelay("\nThe maid is annoyed by your harassment, she decides to give you information\n\n\"The highest damage weapon is held by guard Bob, \nnow leave me alone im watching Raw\"",10);
        } else if (maid.getcharacterType()==3 && maid.getPatience() == 1) {
            player.printWithDelay("\nThe maid is annoyed by your harassment, she decides to give you information\n\n\"FINE! Guard Craig has the key to security office, \n please leave me alone\" ", 10);
        } else if (maid.getcharacterType()==4 && maid.getPatience() == 2) {
            player.printWithDelay("\n\"What do you want?\" ", 10);
        } else if (maid.getcharacterType()==4 && maid.getPatience() == 1) {
                player.printWithDelay("\n\"Scram kid\" ", 10);
        } else if ( maid.getPatience() == 0) {
            System.out.println(maid.getName()+" slaps you.. you take 1 damage");
            System.out.println("Your health: ");
            player.takedamage(1);
            for (int i = 0; i < player.getHealthStatus(); i++) {
                System.out.print("*");
            }        } else{
            System.out.println("The maid doesnt speak, maybe if i try again...");
        }
    }

    public void inspectGuard(Guard guard, Player p) {
        String guardInspection = guard.inspectString();
        System.out.println(guardInspection);
        //Check if the guard is asleep
        if (!guard.getAwake()) {
            // If the guard is awake, show weapons immediately

            System.out.println("Oh no the guard noticed you!, he is going to throw you out, quick, defend yourself!");
            if (p.getInventory().isContainsWeapon()) {
                // Display the weapons from the player's inventory
                fightGuard(p, guard);

            } else {
                System.out.println("You don't have any weapons to fight with.");
                System.out.println("Youve been thrown back to the start");
                p.takedamage(1);
                p.printHealth();
                p.moveTo(p.getFirstRoom());

            }

        } else {
            // If the guard is asleep, prompt the user to fight
            System.out.println("Do you want to fight the guard? (yes/no)");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();


            if (choice.equalsIgnoreCase("yes")) {

                // Check if the player has any weapons in their inventory
                if (p.getInventory().isContainsWeapon()) {
                    // Display the weapons from the player's inventory
                    fightGuard(p, guard);

                } else {
                    System.out.println("You don't have any weapons to fight with.");
                    System.out.println(guard.getName()+" threw you back to the entrance");
                    p.takedamage(1);
                    p.printHealth();
                    p.moveTo(p.getFirstRoom());
                }
            }

        }
    }
    public int fightGuard(Player player, Guard guard) {
        // Display initial health status
        System.out.println("Player health: " + (player.getHealthStatus()));
        for (int i = 0; i < player.getHealthStatus(); i++) {
            System.out.print("*");
        }
        System.out.println();
        System.out.println("Guard health: " + guard.getHealth());
        for (int i = 0; i < guard.getHealth(); i++) {
            System.out.print("*");
        }
        System.out.println();
        // Select a weapon
        int weaponIndex =  showWeapons(player);
        // Get the selected weapon
        int playerpower;
        Item selectedItem = player.getInventory().getItemList().get(weaponIndex);
        Weapon selectedWeapon = (Weapon) selectedItem;
        playerpower = selectedWeapon.getPower();
        // Fight until either the player or the guard runs out of health
        while (player.isAlive() && guard.isAlive()) {
            // Player attacks the guard
            int playerDamage = playerpower;
            guard.damage(playerDamage);
    
            // Guard retaliates
            int guardDamage = guard.attack();
            player.takedamage(guardDamage);
            System.out.println();
            System.out.println("The guard retaliates");
            // Display updated health status after each attack
            System.out.println();
            System.out.println("Player health: ");
            for (int i = 0; i < player.getHealthStatus(); i++) {
                System.out.print("*");
            }
            
            try {
                Thread.sleep(1000); // Wait for 2000 milliseconds (2 seconds)
            } catch (InterruptedException e) {
                // Handle interrupted exception if needed
                e.printStackTrace();
            }
            System.out.println();
            System.out.println("\nYou hit the guard with "+ selectedWeapon.getName());
            System.out.println();
            
            System.out.println("Guard health: ");
            for (int i = 0; i < guard.getHealth(); i++) {
                System.out.print("*");
            }
            System.out.println();
            try {
                Thread.sleep(1000); // Wait for 2000 milliseconds (2 seconds)
            } catch (InterruptedException e) {
                // Handle interrupted exception if needed
                e.printStackTrace();
            }
            
        }
    
        // Determine the winner
        if (player.isAlive()) {
            player.incdef();
            guard.gameEnded();
            System.out.println("\nCongratulations! You defeated the guard.\n");
            if(!guard.getPocketItems().isEmpty()) {
                System.out.println("During the struggle "+ guard.getName()+ " dropped: ");
                for (Item i : guard.getPocketItems()) {
                    i.setHidden(false);
                    i.setRoom(player.getRoom());
                    System.out.println(i.inspectString());
                    player.getRoom().addItem(i);
                }
            }
            else{
                System.out.println("This guard didnt have any items");
            }
            return 1;
        } else {
            System.out.println("Game over! The guard defeated you");
            player.moveTo(player.getFirstRoom());
            return 0;
        }
        
    }

    public int findWeaponIndex(Player player, String choice) {
    Inventory inventory = player.getInventory();
    LinkedList<Item> itemList = inventory.getItemList();
    
    for (int i = 0; i < itemList.size(); i++) {
        Item item = itemList.get(i);
        if (item instanceof Weapon && item.getName().equalsIgnoreCase(choice)) {
            return i; // Return the index if the weapon name matches the choice
        }
    }
    
    // Return -1 if the weapon with the specified name is not found
    return -1;
    }

    
    private int showWeapons(Player p) {
        // Display the weapons from the player's inventory
        System.out.println("You have the following weapons:");
       
        for (Item item : p.getInventory().getItemList()) {
            if (item instanceof Weapon) {
                System.out.println(item.getName() + ": " + item.getDescription());
            }
        }
        
        System.out.println("select a weapon:");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        int index = findWeaponIndex(p,choice);
        
        return index;
    }

    public void inspectmaid(Maid m,Player p){
        System.out.println(m.inspectString());
        
        System.out.println("do you want to attack the maid(yes/no)");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        
        
        if (choice.equalsIgnoreCase("yes")) {
            
            // Check if the player has any weapons in their inventory
            if (p.getInventory().isContainsWeapon()) {
                // Display the weapons from the player's inventory
                System.out.println("You strike the maid\n");
                player.printWithDelay("""
                        Shock grips you as you realize the gravity of your actions: 
                        
                        How could you hit a woman! 
                        
                        Overwhelmed, you fail to notice the guard rushing up from behind. 
                        In an instant, you're tackled to the ground and thrown back to the entrance
                        """,10);                player.printWithDelay("You've been thrown back to the entrance",10);
                p.takedamage(1);
                p.printHealth();
                p.moveTo(p.getFirstRoom());

            } else {
                System.out.println("You don't have any weapons to fight with, you strike her with your bare hands");
                player.printWithDelay("""
                        Shock grips you as you realize the gravity of your actions: 
                        
                        How could you hit a woman! 
                        
                        Overwhelmed, you fail to notice the guard rushing up from behind. 
                        In an instant, you're tackled to the ground and thrown back to the entrance
                        """,10);

            }
            p.takedamage(1);
            p.printHealth();
            p.moveTo(p.getFirstRoom());
            }
        }
}

