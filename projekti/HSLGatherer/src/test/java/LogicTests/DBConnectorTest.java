package LogicTests;

import classes.Trip;
import logic.DBConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DBConnectorTest {

    DBConnector db;

    @Before
    public void setUp() throws MalformedURLException, SQLException {
        this.db = new DBConnector(true);
    }

    @After
    public void after() throws SQLException {
        db.deleteDb();
    }

    @Test
    public void savedStopsReturnsZeroIfDbEmpty() throws SQLException {
        ResultSet rs = db.getSavedStops();

        int size = 0;

        while (rs.next()) {
            size++;
        }

        assertEquals(size, 0);
    }

    @Test
    public void savedRoutesReturnsZeroIfDbEmpty() throws SQLException {
        ResultSet rs = db.getSavedRoutes();

        int size = 0;

        while (rs.next()) {
            size++;
        }

        assertEquals(size, 0);
    }

    @Test
    public void savedRoutesDeletedWhenStopDeleted() throws SQLException {
        String stop = "Hämeenapajantie";
        Trip trip = new Trip("85", "Hämeenapajantie");

        assertTrue(db.addStop(stop));
        assertTrue(db.addTrip(trip.getSign(), trip.getRoute(), stop));

        ResultSet stopRs = db.getSavedStops();
        ResultSet routeRs = db.getSavedRoutes();

        assertEquals(resultSetSize(stopRs), 1);
        assertEquals(resultSetSize(routeRs), 1);

        assertTrue(db.deleteStop(stop));

        assertEquals(resultSetSize(stopRs), 0);
        assertEquals(resultSetSize(routeRs), 0);
    }

    public int resultSetSize(ResultSet rs) throws SQLException {
        int size = 0;

        while (rs.next()) {
            size++;
        }

        return size;
    }
}
