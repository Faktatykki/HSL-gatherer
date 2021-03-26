package logic;

import classes.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.*;

public class Logic {

    private DBConnector db;
    private APIhandle API;

    public Logic() throws SQLException, MalformedURLException {
        this.db = new DBConnector();
        this.API = new APIhandle();
    }

    public void searchForTrips(String stop) throws IOException {
        List<String[]> graphResp = this.API.makeHttpRequest(false,stop);
        List<Trip> trips = new ArrayList<>();

        for(int i = 0; i < graphResp.size(); i++) {
            String[] arr = graphResp.get(i);
            trips.add(new Trip(arr[0], arr[1], arr[2], arr[3], arr[4]));
        }

        for(int j = 0; j < trips.size(); j++) {
            System.out.println(trips.get(j));
            System.out.println();
        }
        System.out.println();
    }

    public void searchStops(String stop) throws IOException {
        List<String[]> graphResp = this.API.makeHttpRequest(true, stop);
        Set<Stop> stops = new HashSet<>();

        for(int i = 0; i < graphResp.size(); i++) {
            String[] arr = graphResp.get(i);
            stops.add(new Stop(arr[0]));
        }

        for(Stop node: stops) {
            System.out.println(node);
        }
        System.out.println();
    }
}
