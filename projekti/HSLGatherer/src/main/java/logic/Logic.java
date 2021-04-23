package logic;

import classes.*;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Logic {

    private DBConnector db;
    private APIhandle api;

    public Logic(boolean test) throws SQLException, MalformedURLException {
        this.db = new DBConnector(test);
        this.api = new APIhandle();
    }

    public List<Trip> searchForTrips(String stop) throws IOException {
        List<String[]> graphResp = this.api.makeHttpRequest(false, stop);
        List<Trip> trips = new ArrayList<>();

        for (int i = 0; i < graphResp.size(); i++) {
            String[] arr = graphResp.get(i);
            trips.add(new Trip(arr[0], arr[1], arr[2], arr[3], arr[4]));
        }

        return trips;
    }

    public TreeSet<Stop> searchStops(String stop) throws IOException {
        List<String[]> graphResp = this.api.makeHttpRequest(true, stop);
        TreeSet<Stop> stops = new TreeSet<>();

        for (int i = 0; i < graphResp.size(); i++) {
            String[] arr = graphResp.get(i);
            stops.add(new Stop(arr[0]));
        }

        return stops;
    }

    public boolean addStopToDb(String stop) throws SQLException {
        return db.addStop(stop);
    }

    public boolean addTripToDb(Trip trip, String stop) throws SQLException {
        String sign = trip.getSign();
        String route = trip.getRoute();

        return db.addTrip(sign, route, stop);
    }

    public List<String> getSavedStops() throws SQLException {
        ResultSet rs = db.getSavedStops();

        List<String> stops = new ArrayList<>();

        if (rs != null) {
            while (rs.next()) {
                stops.add(rs.getString("name"));
            }
        }

        Collections.sort(stops);

        return stops;
    }

    public Set<Trip> getSavedRoutes() throws SQLException {
        ResultSet rs = db.getSavedRoutes();
        HashSet<Trip> trips = new HashSet<>();

        if (rs != null) {
            while (rs.next()) {
                Trip trip = new Trip(rs.getString("sign"), rs.getString("route"));
                trips.add(trip);
            }
        }

        return trips;
    }

    public boolean deleteStop(String stop) throws SQLException {
        return db.deleteStop(stop);
    }

    public boolean deleteRoute(Trip trip) throws SQLException {
        String sign = trip.getSign();
        String route = trip.getRoute();

        return db.deleteRoute(sign, route);
    }

    public boolean deleteTestDb() throws SQLException {
        return db.deleteDb();
    }
}
