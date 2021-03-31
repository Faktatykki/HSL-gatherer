
import logic.Logic;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import services.Service;
import java.util.*;
import classes.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class HSLGathererTest {

    Service s;
    Logic l;

    @Before
    public void setUp() throws MalformedURLException, SQLException {
        s = new Service();
        l = new Logic();
    }

    @Test
    public void searchForStopsTakesNonEnglishLetters() throws IOException {
        Set<Stop> stops = l.searchStops("Hämeenapajantie");

        assertEquals(stops.size(), 1);

        for(Stop stop: stops) {
            assertEquals("Hämeenapajantie", stop.toString());
        }
    }
}
