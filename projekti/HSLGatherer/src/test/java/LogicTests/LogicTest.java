package LogicTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import logic.Logic;
import classes.Trip;
import classes.Stop;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import java.util.TreeSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class LogicTest {

    Logic l;

    @Before
    public void setUp() throws MalformedURLException, SQLException {
        l = new Logic(true);
    }

    @After
    public void after() throws SQLException {
        l.deleteTestDb();
    }

    @Test
    public void searchForTripsWorksAndWontGiveNull() throws IOException {
        List<Trip> trips = l.searchForTrips("Rautatieasema");

        boolean anyNulls = false;

        for (Trip t: trips) {
            if (t.getSign() == null || t.getRoute() == null ||
                t.getDelay() == null || t.getDeparture() == null ||
                t.getUpdated() == null) {
                anyNulls = true;
            }
        }

        assertFalse(anyNulls);
    }

    @Test
    public void searchForStopsWorksAndWontGiveNull() throws IOException {
        TreeSet<Stop> stops = l.searchStops("Rauta");

        boolean anyNulls = false;

        for (Stop s: stops) {
            if (s.getName() == null) {
                anyNulls = true;
            }
        }

        assertFalse(anyNulls);
    }

    @Test
    public void addingStopToDbWorksAndWontTakeDuplicates() throws SQLException {
        assertEquals(l.addStopToDb("Hämeenapajantie"), true);
        assertEquals(l.addStopToDb("Hämeenapajantie"), false);
    }

    @Test
    public void addingTripToDbWorksAndWontTakeDuplicates() throws SQLException {
        Trip trip = new Trip("85", "Jollas");
        String stop = "Hämeenapajantie";

        assertEquals(l.addTripToDb(trip, stop), true);
        assertEquals(l.addTripToDb(trip, stop), false);
    }

    @Test
    public void savedStopsAreInOrder() throws SQLException {
        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";

        assertTrue(l.addStopToDb(c));
        assertTrue(l.addStopToDb(d));
        assertTrue(l.addStopToDb(a));
        assertTrue(l.addStopToDb(b));

        List<String> stops = l.getSavedStops();

        assertEquals(stops.get(0), "a");
        assertEquals(stops.get(3), "d");
    }

    @Test
    public void savedRoutesWontGiveDuplicates() throws SQLException {
        Trip trip = new Trip("85", "Jollas");
        String stop = "Hämeenapajantie";

        assertEquals(l.addTripToDb(trip, stop), true);
        assertEquals(l.addTripToDb(trip, stop), false);

        Set<Trip> trips = l.getSavedRoutes();

        assertEquals(trips.size(), 1);
    }

    @Test
    public void deleteStopWorksAndHandlesBadRequest() throws SQLException {
        String stop = "Hämeenapajantie";

        assertTrue(l.addStopToDb(stop));

        assertTrue(l.deleteStop(stop));
        assertTrue(l.deleteStop(stop));
    }

    @Test
    public void deleteRouteWorksAndHandlesBadRequest() throws SQLException {
        Trip trip = new Trip("85", "Jollas");
        String stop = "Hämeenapajantie";

        assertTrue(l.addTripToDb(trip, stop));

        assertTrue(l.deleteRoute(trip));
        assertTrue(l.deleteRoute(trip));
    }
}
