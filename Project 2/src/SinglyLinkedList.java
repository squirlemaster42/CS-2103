class SinglyLinkedList<K, T> {

    //TODO Make sure generics are correct
    //TODO Implement a size?
    private Node head;
    private Node tail;

    SinglyLinkedList(){
        head = null;
        tail = null;
    }

    void push(Node<K, T> node) {
        if (head == null) {
            head = node;
            tail = node;
            return;
        }
        Node tempHead = head;
        head = node;
        tempHead.prev = head;
    }

    K removeTail(){
        Node tempTail = tail;
        tail = tempTail.prev;
        //TODO Fix
        return (K) tempTail.key;
    }

    Node getHead(){
        return head;
    }

    Node getTail(){
        return tail;
    }

    static class Node<K, T> {

        //TODO Format with _
        private T value;
        private K key;
        private Node prev;

        Node(final K key, final T value){
            this.value = value;
            this.key = key;
        }

        T getValue(){
            return value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}
