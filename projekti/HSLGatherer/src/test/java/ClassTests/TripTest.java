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

public class TripTest {

    @Test
    public void equalsMethodWorks() {
        Trip t1 = new Trip("666", "Kumpula");
        Trip t2 = new Trip("666", "Kumpula");
        Trip t3 = new Trip("555", "Kumpula");
        Stop s1 = new Stop("Kumpula");

        assertTrue(t1.equals(t1));
        assertTrue(t1.equals(t2));
        assertFalse(t1.equals(s1));
        assertFalse(t1.equals(t3));
    }

    @Test
    public void compareToMethodWorks() {
        Trip t1 = new Trip("1", "testi");
        Trip t2 = new Trip("2", "testi");
        Trip t3 = new Trip("3", "testi");
        Trip t4 = new Trip("4", "testi");

        for (int i = 0; i < 2; i++) {
            if(i == 1) {
                t1 = new Trip("testi", "1");
                t2 = new Trip("testi", "2");
                t3 = new Trip("testi", "3");
                t4 = new Trip("testi", "4");
            }

            List<Trip> trips = new ArrayList<>();

            trips.add(t3);
            trips.add(t4);
            trips.add(t1);
            trips.add(t2);

            assertEquals(trips.get(0), t3);
            assertEquals(trips.get(3), t2);

            Collections.sort(trips);

            assertEquals(trips.get(0), t1);
            assertEquals(trips.get(3), t4);
        }
    }

    @Test
    public void hashCodeWorks() {
        Map<Trip, String> map = new HashMap<>();

        Trip t1 = new Trip("666", "Kumpula");
        Trip t2 = new Trip("777", "Jollas");

        map.put(t1, "testi1");
        map.put(t2, "testi2");

        assertEquals(map.get(t1), "testi1");
        assertEquals(map.get(t2), "testi2");
    }
}
