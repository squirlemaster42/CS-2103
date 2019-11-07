import java.util.HashMap;
import java.util.Map;
/**
 * An implementation of HashMap that stores it data in a predictable manner
 * @param <K> The type of the key
 * @param <V> The type of the value
 */
public class LinkedHashMap<K, V> {

    private final Map<K, Node<K, V>> _map;
    private Node<K, V> _head, _tail;
    private int _size;

    /**
     * Constructs a LinkedHash_map
     */
    LinkedHashMap() {
        _map = new HashMap<>();
        _size = 0;
    }

    /**
     * Adds a key value pair to the HashMap and puts it at the head of the LinkedList
     * @param key The key to store the value under
     * @param value The value to be stored
     */
    void put(final K key, final V value) {
        _size++; //Increments size
        Node<K, V> node = new Node<>(key, value); //Constructs a node with the input data
        if (_head == null) { //Sets head and tail to null
            _head = node;
            _tail = node;
        } else { //Sets the created node as the new head
            node.next = _head;
            _head.prev = node;
            _head = node;
        }
        _map.put(key, node); //Adds the node to the HashMap
    }

    /**
     * Returns a value for a particular key and moves it to the head of the LinkedList
     * @param key The key of the value to get
     * @return The value stored under the key
     */
    V get(final K key) {
        Node<K, V> node = _map.get(key); //Gets the node from the map
        //Moves the node to the head of the LinkedList and returns its value
        moveToHead(node);
        return node.value;
    }

    /**
     * Moves a specified node to the head of the linked list
     * @param node The node to move
     */
    private void moveToHead(final Node<K, V> node) {
        if(node == _head){ //If the node is already at the head do nothing
            //The node is already at the head of the LinkedList
        }else if (node == _tail && _size != 1) { //Move the node to the head if the node is at the tail
            _tail = node.prev;
            _tail.next = null;
            node.prev = null;
            node.next = _head;
            _head.prev = node;
            _head = node;
        } else if (_size != 1) { //Mode the node to head
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.prev = null;
            node.next = _head;
            _head = node;
        }
    }

    /**
     * Removed the tail of the LinkedList. This was always be the value that was used least recently
     */
    void evictTail() {
        if (_size == 1) { //When the size is 1, remove head, sets head and tail to null and decrease the size by 1
            _map.remove(_head.key);
            _head = null;
            _tail = null;
            _size--;
        }else{ //When the size is greater than 1, decrease the size by 1, set the next value of the previous value of the tail to null.
            _size--;
            _tail.prev.next = null;
            _map.remove(_tail.key);
            _tail = _tail.prev;
        }
    }

    /**
     * Returns the current size of the LinkedHashMap
     * @return The size of the LinkedHashMap
     */
    int getSize() {
        return _size;
    }

    /**
     * Checks that the specified key exists within the linked list
     * @param key the key to check for
     * @return true if it exists within the linked list, false otherwise
     */
    public boolean containsKey(K key) {
        return _map.containsKey(key);
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
