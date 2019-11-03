import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LinkedHashMapTest {

    @Test
    public void testLinkedHashMap(){
        LinkedHashMap<String, Integer> lst = new LinkedHashMap<>();
        lst.put("a", 1);
        lst.put("b", 2);
        lst.put("c", 3);
        lst.put("d", 4);
        assertEquals(4, lst.getSize());
        assertEquals(Integer.valueOf(1), lst.get("a"));
        assertEquals(Integer.valueOf(2), lst.get("b"));
        assertEquals(Integer.valueOf(3), lst.get("c"));
        assertEquals(Integer.valueOf(4), lst.get("d"));
        lst.evictTail();
        assertNull(lst.get("a"));
    }
}
