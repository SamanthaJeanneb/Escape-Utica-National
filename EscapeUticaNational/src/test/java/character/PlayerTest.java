package character;

import map.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void moveTo() {
        Player p1 = new Player("name","description");
        Room r1 = new Room("name","description");
        Room r2 = new Room("name","description");
        p1.setFirstRoom(r1);
        p1.moveTo(r2);
        assertEquals(r2,p1.getRoom());
    }
    //idk how to test noticed because its random, i will come to office hours to discuss i cant figure it out
}