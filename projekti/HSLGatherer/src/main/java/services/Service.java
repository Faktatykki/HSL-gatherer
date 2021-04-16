package services;

import logic.*;
import classes.*;

import javax.swing.event.TreeSelectionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.*;

public class Service {

    Logic l;

    public Service() throws SQLException, MalformedURLException {
        l = new Logic();
    }


    public List<Stop> searchForStops(String stop) throws IOException {
        List<Stop> stops = new ArrayList<>();

        try {
            TreeSet<Stop> tempStops = l.searchStops(stop);
            stops = new ArrayList<>(tempStops);
        } catch (NullPointerException e) {
        }

        return stops;
    }

    public boolean addStop(String stop) throws SQLException {
        if (stop == null) {
            return false;
        }

        return l.addStopToDb(stop);
    }

    public boolean addTrip(Trip trip, String stop) throws SQLException {
        if (trip == null || stop == null) {
            return false;
        }

        return l.addTripToDb(trip, stop);
    }

    public List<Trip> searchForTrips(String stop) throws IOException {
        List<Trip> trips = l.searchForTrips(stop);

        Collections.sort(trips);

        return trips;
    }

    public List<Trip> searchForRoutes(String stop) throws IOException {
        Set<Trip> tempTrips = new TreeSet<>(l.searchForTrips(stop));

        return new ArrayList<>(tempTrips);
    }

    public List<String> getSavedStops() throws SQLException {
        return l.getSavedStops();
    }

    public Set<Trip> getSavedRoutes() throws SQLException {
        return l.getSavedRoutes();
    }

    public boolean deleteStop(String stop) throws SQLException {
        return l.deleteStop(stop);
    }

    public boolean deleteRoute(Trip trip) throws SQLException {
        return l.deleteRoute(trip);
    }
}
