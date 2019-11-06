import java.util.HashMap;
import java.util.Map;

//TODO Name this better because it is not exactly a linked hashmap
//In the way it works

/**
 * An implementation of HashMap that stores it data in a predictable manner
 * @param <K> The type of the key
 * @param <V> The type of the value
 */
public class LinkedHashMap<K, V> {
    //Ask about implementing List

    //TODO Add _
    private final Map<K, Node<K, V>> map;
    private Node<K, V> head, tail;
    private int size;

    /**
     * Constructs a LinkedHashMap
     */
    LinkedHashMap() {
        map = new HashMap<>();
        size = 0;
    }

    /**
     * Adds a key value pair to the HashMap and puts it at the head of the LinkedList
     * @param key The key to store the value under
     * @param value The value to be stored
     */
    void put(final K key, final V value) {
        size++; //Increments size
        Node<K, V> node = new Node<>(key, value); //Constructs a node with the input data
        if (head == null) { //Sets head and tail to null
            head = node;
            tail = node;
        } else { //Sets the created node as the new head
            node.next = head;
            head.prev = node;
            head = node;
        }
        map.put(key, node); //Adds the node to the HashMap
    }

    /**
     * Returns a value for a particular key and moves it to the head of the LinkedList
     * @param key The key of the value to get
     * @return The value stored under the key
     */
    V get(final K key) {
        Node<K, V> node = map.get(key); //Gets the node from the map
        if (node != null) { //Moves the node to the head of the LinkedList and returns its value
            moveToHead(node);
            return node.value;
        } else {
            return null;
        }
    }

    /**
     * Moves a specified node to the head of the linked list
     * @param node The node to move
     */
    private void moveToHead(final Node<K, V> node) {
        if(node == head){ //If the node is already at the head do nothing
            //The node is already at the head of the LinkedList
        }else if (node == tail && size != 1) { //Move the node to the head if the node is at the tail
            tail = node.prev;
            tail.next = null;
            node.prev = null;
            node.next = head;
            head.prev = node;
            head = node;
        } else if (size != 1) { //Mode the node to head
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.prev = null;
            node.next = head;
            head = node;
        }
    }

    //TODO Make sure it gets removed from memory
    //TODO Fix when size is 1
    /**
     * Removed the tail of the LinkedList. This was always be the value that was used least recently
     */
    void evictTail() {
        if (size == 1) {
            map.remove(head.key);
            head = null;
            tail = null;
            size--;
        }else{
            size--;
            tail.prev.next = null;
            map.remove(tail.key);
            tail = tail.prev;
        }
    }

    /**
     * Returns the current size of the LinkedHashMap
     * @return The size of the LinkedHashMap
     */
    int getSize() {
        return size;
    }

    /**
     * TODO Comment
     * @param key
     * @return
     */
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    /**
     * A Node that holds a key, value, next node, and previous node. Used for the doubly linked list
     * @param <K> The type of the key
     * @param <V> The type of the value
     */
    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> prev, next;

        /**
         * Constructs a new node with a key and a value
         * @param key The key for the node
         * @param value The value for the node
         */
        private Node(final K key, final V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        /**
         * Returns a String representation of the node
         * @return A String that represents the node
         */
        public String toString() {
            return key + " " + value +
                    " Prev: " + (prev == null ? null : prev.key) +
                    " Next: " + (next == null ? null : next.key);
        }
    }
}
