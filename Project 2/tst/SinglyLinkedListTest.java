import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SinglyLinkedListTest {

    @Test
    public void testInsertAndRemoveTail(){
        SinglyLinkedList<Integer> lst = new SinglyLinkedList<>();
        lst.push(new SinglyLinkedList.Node<>(5));
        lst.push(new SinglyLinkedList.Node<>(8));
        lst.push(new SinglyLinkedList.Node<>(9));
        assertEquals(lst.getHead().getValue(), 9);
        assertEquals(lst.getTail().getValue(), 5);
        lst.removeTail();
        assertEquals(lst.getHead().getValue(), 9);
        assertEquals(lst.getTail().getValue(), 8);
    }
}
