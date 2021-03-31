package services;

import logic.*;
import classes.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.*;

public class Service {

    Logic l;

    public Service() throws SQLException, MalformedURLException {
        l = new Logic();
    }


    public void searchForStops(String stop) throws IOException {
        Set<Stop> stops = l.searchStops(stop);

        for(Stop s: stops) {
            System.out.println(s);
        }
    }

    public void searchForTrips(String stop) throws IOException {
        List<Trip> trips = l.searchForTrips(stop);

        for(int i = 0; i < trips.size(); i++) {
            System.out.println(trips.get(i));
        }
    }
}
