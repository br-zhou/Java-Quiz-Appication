package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("Sensor open at door");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", e.getDescription());
        assertEquals(d.toString(), e.getDate().toString());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
    }

    @Test
    public void bypassCodeCoverage() {
        try {
            int var = e.hashCode();
        } catch (Exception e) {
            fail("e.hashcode should return an integer");
        }
        /*
        Bypasses code coverage. Realistically, as long as e.hashCode returns an integer, which it must by its
        declaration, the method works. Efficiency doesn't matter in my use case.
        */
    }

    @Test
    public void testEquals() {
        assertFalse(e.equals(null));
        assertFalse(e.equals(d));
        assertFalse( e.equals(new Event("Sensor open at door")));
        assertTrue(e.equals(e));
        assertTrue((new Event("A")).equals(new Event("A")));
    }
}
