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

    public Service(boolean test) throws SQLException, MalformedURLException {
        l = new Logic(test);
    }

    public List<Stop> searchForStops(String stop) throws IOException {
        if (stop == null) {
            return null;
        }

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
        if (trip == null || stop == null || trip.getRoute() == null || trip.getSign() == null) {
            return false;
        }

        return l.addTripToDb(trip, stop);
    }

    public List<Trip> searchForTrips(String stop) throws IOException {
        if (stop == null) {
            return null;
        }

        List<Trip> trips = l.searchForTrips(stop);

        Collections.sort(trips);

        return trips;
    }

    public List<Trip> searchForRoutes(String stop) throws IOException {
        if (stop == null) {
            return null;
        }

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
        if (stop == null) {
            return false;
        }

        return l.deleteStop(stop);
    }

    public boolean deleteRoute(Trip trip) throws SQLException {
        if (trip == null || trip.getSign() == null || trip.getRoute() == null) {
            return false;
        }

        return l.deleteRoute(trip);
    }

    public boolean deleteTestDb() throws SQLException {
        return l.deleteTestDb();
    }
}
