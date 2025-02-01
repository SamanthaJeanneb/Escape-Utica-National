package data_structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BSTTest {

    @Test
    void find() {
        BST<String, String> bst = new BST<>();
        bst.insert("A", "A");
        bst.insert("B", "B");

        assertEquals("A", bst.find("A"));
        assertEquals("B", bst.find("B"));
        assertNull(bst.find("C")); // Key not present in bst sothis should be null
    }

    @Test
    void insert() {
        BST<String, String> bst = new BST<>();
        bst.insert("A", "A");
        bst.insert("B", "B");

        assertEquals("A", bst.find("A"));
        assertEquals("B", bst.find("B"));
    }

    @Test
    void remove() {
        BST<String, String> bst = new BST<>();
        bst.insert("A", "A");
        bst.insert("B", "B");

        bst.remove("A");
        assertNull(bst.find("A")); // Key removed
        assertEquals("B", bst.find("B")); // Other key still present
    }
}