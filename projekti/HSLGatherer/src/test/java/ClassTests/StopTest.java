package ClassTests;

import classes.Stop;
import classes.Trip;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import static org.junit.Assert.*;

public class StopTest {

    @Test
    public void equalsMethodWorks() {
        Stop s1 = new Stop("Kumpulan kampus");
        Stop s2 = new Stop("Kumpulan kampus");
        Trip t1 = new Trip("666", "Kumpulan kampus");

        assertTrue(s1.equals(s1));
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals("Kumpulan kampus"));
        assertFalse(s1.equals(t1));
    }

    @Test
    public void compareToWorks() {
        Stop a = new Stop("a");
        Stop b = new Stop("b");
        Stop c = new Stop("c");
        Stop d = new Stop("d");

        List<Stop> stops = new ArrayList<>();

        stops.add(c);
        stops.add(b);
        stops.add(a);
        stops.add(d);

        assertTrue(stops.get(0).getName().equals("c"));
        assertTrue(stops.get(3).getName().equals("d"));

        Collections.sort(stops);

        assertTrue(stops.get(0).getName().equals("a"));
        assertTrue(stops.get(3).getName().equals("d"));
    }

    @Test
    public void hashCodeWorks() {
        Map<Stop, String> map = new HashMap<>();

        Stop s1 = new Stop("Hämeenapajantie");
        Stop s2 = new Stop("Kumpulan kampus");

        map.put(s1, "testi1");
        map.put(s2, "testi2");

        assertEquals(map.get(s1), "testi1");
        assertEquals(map.get(s2), "testi2");
    }
}
