package ServiceTests;

import classes.Stop;
import classes.Trip;
import services.Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import java.util.List;

import static org.junit.Assert.*;

public class ServiceTest {

    Service s;

    @Before
    public void setUp() throws MalformedURLException, SQLException {
        s = new Service(true);
    }

    @After
    public void after() throws SQLException {
        s.deleteTestDb();
    }

    @Test
    public void nullOrFalseForServiceMethods() throws IOException, SQLException {
        assertEquals(s.searchForStops(null), null);

        assertEquals(s.addStop(null), false);

        assertEquals(s.addTrip(null, null), false);
        assertEquals(s.addTrip(null, "Kumpulan kampus"), false);
        assertEquals(s.addTrip(new Trip("506", null), "Kumpulan kampus"), false);
        assertEquals(s.addTrip(new Trip(null, "Arabia"), "Kumpulan kampus"), false);

        assertEquals(s.searchForTrips(null), null);

        assertEquals(s.searchForRoutes(null), null);

        assertEquals(s.deleteStop(null), false);

        assertEquals(s.deleteRoute(null), false);
    }

    @Test
    public void searchForStopsTakesSpecialLetters() throws IOException {
        String stop = "Hämeenapajantie";

        List<Stop> stops = s.searchForStops(stop);

        assertEquals(stops.size(), 1);
        assertEquals(stops.get(0).getName(), stop);
    }

    @Test
    public void searchForStopsFindsResultsWithNoPerfectParameter() throws IOException {
        String stop = "Hämeenapa";

        List<Stop> stops = s.searchForStops(stop);

        assertEquals(stops.size(), 1);
        assertEquals(stops.get(0).getName(), "Hämeenapajantie");
    }

    //searchForTrips brings whole schedule
    //searchForRoutes only the general route
    @Test
    public void searchForRoutesNoDuplicates() throws IOException {
        List<Trip> list = s.searchForRoutes("Hämeenapajantie");

        assertEquals(list.size(), 2);

        Trip t1 = list.get(0);
        Trip t2 = list.get(1);

        assertTrue(t1.getSign().equals(t2.getSign()));
        assertFalse(t1.getRoute().equals(t2.getRoute()));

        assertEquals(list.size(), 2);
    }

    @Test
    public void addingStopToDatabaseWorks() throws IOException, SQLException {
        String stop = "Kumpulan kampus";

        List<Stop> stops = s.searchForStops(stop);

        assertEquals(s.addStop(stops.get(0).getName()), true);
        assertEquals(s.getSavedStops().size(), 1);
        assertEquals(s.getSavedStops().get(0), stop);
    }

    @Test
    public void addingTripToDatabaseWorks() throws IOException, SQLException {
        String stop = "Hämeenapajantie";

        List<Trip> trips = s.searchForTrips(stop);
        List<Stop> stops = s.searchForStops(stop);

        assertTrue(s.addStop(stop));
        assertTrue(s.addTrip(trips.get(0), stops.get(0).getName()));

        assertEquals(s.getSavedStops().size(), 1);
        assertEquals(s.getSavedRoutes().size(), 1);
    }

    @Test
    public void deleteStopWorks() throws SQLException {
        String stop = "Hämeenapajantie";

        assertTrue(s.addStop(stop));

        assertEquals(s.getSavedStops().size(), 1);
        assertTrue(s.deleteStop(stop));
        assertEquals(s.getSavedStops().size(), 0);
    }

    @Test
    public void deleteRouteWorks() throws SQLException, IOException {
        String stop = "Hämeenapajantie";

        List<Trip> trips = s.searchForTrips(stop);
        List<Stop> stops = s.searchForStops(stop);

        assertTrue(s.addStop(stop));
        assertTrue(s.addTrip(trips.get(0), stops.get(0).getName()));

        assertEquals(s.getSavedStops().size(), 1);
        assertEquals(s.getSavedRoutes().size(), 1);

        assertTrue(s.deleteRoute(trips.get(0)));

        assertEquals(s.getSavedRoutes().size(), 0);
        assertEquals(s.getSavedStops().size(), 1);
    }
}

