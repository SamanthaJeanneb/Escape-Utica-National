package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BST<K extends Comparable<K>, V> implements Iterable<V> {
    private Node<K, V> root;
    private int size;
    public BST() {
        root = null;
        size = 0;
    }
    public V find(K key) {
        return find(key, root);
    }
    public boolean isEmpty() {
        return root == null;
    }
    private V find(K key, Node<K, V> currentRoot) {
        while (currentRoot != null) {
            int cmp = key.compareTo(currentRoot.key);
            if (cmp == 0) {
                return currentRoot.value;
            } else if (cmp < 0) {
                currentRoot = currentRoot.left;
            } else {
                currentRoot = currentRoot.right;
            }
        }
        return null;
    }
    public V findFirst() { // for first room
        if (root == null) {
            return null;
        }
        Node<K, V> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }
    public void insert(K key, V value) {
        if (root == null) {
            root = new Node<>(key, value);
        } else {
            Node<K, V> parent = null;
            Node<K, V> current = root;
            while (current != null) {
                int cmp = key.compareTo(current.key);
                if (cmp == 0) {
                    current.value = value;
                    return;
                } else if (cmp < 0) {
                    parent = current;
                    current = current.left;
                } else {
                    parent = current;
                    current = current.right;
                }
            }
            if (key.compareTo(parent.key) < 0) {
                parent.left = new Node<>(key, value);
            } else {
                parent.right = new Node<>(key, value);
            }
        }
        size++;
    }
    public void remove(K key) {
        root = remove(key, root);
    }
    private Node<K, V> remove(K key, Node<K, V> currentRoot) {
        Node<K, V> parent = null;
        Node<K, V> node = currentRoot;
        while (node != null) {
            int cmp = key.compareTo(node.key); //compareTo will work for alphabetical order (built into string)
            if (cmp == 0) {
                break;
            } else if (cmp < 0) {
                parent = node;
                node = node.left;
            } else {
                parent = node;
                node = node.right;
            }
        }
        if (node == null) {
            return currentRoot;
        }
        if (node.left == null && node.right == null) {
            if (parent == null) {
                return null;
            }
            if (node == parent.left) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else if (node.left == null) {
            if (parent == null) {
                return node.right;
            }
            if (node == parent.left) {
                parent.left = node.right;
            } else {
                parent.right = node.right;
            }
        } else if (node.right == null) {
            if (parent == null) {
                return node.left;
            }
            if (node == parent.left) {
                parent.left = node.left;
            } else {
                parent.right = node.left;
            }
        } else {
            Node<K, V> successorParent = node;
            Node<K, V> successor = node.right;
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }
            if (successorParent != node) {
                successorParent.left = successor.right;
                successor.right = node.right;
            }
            successor.left = node.left;
            if (parent == null) {
                return successor;
            }
            if (node == parent.left) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }
        }
        size--; //decrement size!!
        return currentRoot;
    }
    public String toString() {
        if (root != null) {
            return root.toString();
        } else {
            return "Empty tree :'(";
        }
    }
    // Attemp at Iterable, I think stack is the easiest way to do in place without copying
    @Override
    public Iterator<V> iterator() {
        return new BSTIterator();
    }

    private class BSTIterator implements Iterator<V> {
        private Node<K, V> current;
        private Stack<Node<K, V>> stack;
        public BSTIterator() {
            stack = new Stack<>();
            current = root;
        }
        @Override
        public boolean hasNext() {
            return !stack.isEmpty() || current != null;
        }
        @Override
        public V next() {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            Node<K, V> node = stack.pop();
            V value = node.value;
            current = node.right;
            return value;
        }
    }
    private static class Node<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = right = null;
        }
    }
}