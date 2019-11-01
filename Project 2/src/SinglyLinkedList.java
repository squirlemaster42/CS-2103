public class SinglyLinkedList<T> {

    //TODO Make sure generics are correct
    //TODO Implement a size?
    private Node head;
    private Node tail;

    SinglyLinkedList(){
        head = null;
        tail = null;
    }

    void push(Node<T> node) {
        if (head == null) {
            head = node;
            tail = node;
            return;
        }
        Node tempHead = head;
        head = node;
        tempHead.prev = head;
    }

    void removeTail(){
        Node tempTail = tail;
        tail = tempTail.prev;
        tempTail = null;
    }

    public Node getHead(){
        return head;
    }

    public Node getTail(){
        return tail;
    }

    static class Node<T> {

        T value;
        Node prev;

        Node(final T value){
            this.value = value;
        }

        public T getValue(){
            return value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}
