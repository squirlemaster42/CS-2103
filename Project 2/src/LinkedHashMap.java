import java.util.HashMap;
import java.util.Map;

//TODO Name this better because it is not exactly a linked hashmap
//In the way it works
public class LinkedHashMap<K, V> {
    //Needs to be a doubly linked because it will be easier to remove nodes
    //Ask about implementing List

    //TODO Add _
    private final Map<K, Node<K, V>> map;
    private Node<K, V> head, tail;
    private int size;

    LinkedHashMap() {
        map = new HashMap<>();
        size = 0;
    }

    //Creates new nodes and adds them to the head
    void put(final K key, final V value) {
        size++;
        Node<K, V> node = new Node<>(key, value);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        map.put(key, node);
    }

    V get(final K key) {
        Node<K, V> node = map.get(key);
        if (node != null) {
            moveToHead(node);
            return node.value;
        } else {
            return null;
        }
    }

    //TODO Test when not moving from tail
    private void moveToHead(final Node<K, V> node) {
        if(node == head){
            return;
        }else if (node == tail && size != 1) {
            tail = node.prev;
            tail.next = null;
            node.prev = null;
            node.next = head;
            head.prev = node;
            head = node;
        } else if (size != 1) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.prev = null;
            node.next = head;
            head = node;
        }
    }

    //TODO Make sure it gets removed from memory
    //TODO Fix when size is 1
    void evictTail() {
        if (size == 1) {
            head = tail = null;
            size--;
            return;
        }
        size--;
        tail.prev.next = null;
        map.remove(tail.key);
        tail = tail.prev;
    }

    int getSize() {
        return size;
    }

    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> prev, next;

        private Node(final K key, final V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + " " + value +
                    " Prev: " + (prev == null ? null : prev.key) +
                    " Next: " + (next == null ? null : next.key);
        }
    }
}
