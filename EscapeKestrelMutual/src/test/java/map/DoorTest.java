package map;

import items.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoorTest {

    Room r1 = new Room("name","description");
    Room r2 = new Room("name","description");
    Door d1 = new Door("name","description");
    Key k1 = new Key("name", "description", 10,true,false,"name");
    @Test
    void unlock() {
        d1.setLocked(true);
        d1.unlock(k1);
        assertFalse(d1.isLocked());
    }

    @Test
    void lock() {
        d1.setLocked(false);
        d1.lock(k1);
        assertTrue(d1.isLocked());
    }

    @Test
    void getOtherRoom() {
        d1.setRoom(r1);
        d1.setRoom(r2);
        assertEquals(r2,d1.getOtherRoom(r1));
    }
}