import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class BSTTest {

    private DefaultMap<Integer, String> map = new BST<>();

    private void addData() {
        map.put(2, "B");
        map.put(5, "E");
        map.put(1, "A");
        map.put(3, "C");
        map.put(6, "F");
        map.put(4, "D");
    }

    @Test
    public void test1() {
        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
        addData();
        assertFalse(map.isEmpty());
        assertEquals(6, map.size());
    }

    @Test
    public void test2() {
        assertFalse(map.containsKey(1));
        assertFalse(map.containsKey(3));
        assertFalse(map.containsKey(6));
        addData();
        assertTrue(map.containsKey(1));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsKey(6));
    }

    @Test
    public void test3() {
        addData();
        map.replace(1, "H");
        map.replace(3, "Z");
        map.replace(6, "P");
        assertEquals("H", map.get(1));
        assertEquals("B", map.get(2));
        assertEquals("Z", map.get(3));
        assertEquals("D", map.get(4));
        assertEquals("E", map.get(5));
        assertEquals("P", map.get(6));
    }

    @Test
    public void test4() {
        addData();
        assertEquals("C", map.get(3));
        assertEquals("D", map.get(4));
        map.set(3, "Z");
        map.set(4, "T");
        map.set(7, "J");
        map.set(8, "W");
        assertEquals("Z", map.get(3));
        assertEquals("T", map.get(4));
        assertEquals("J", map.get(7));
        assertEquals("W", map.get(8));
    }
}
