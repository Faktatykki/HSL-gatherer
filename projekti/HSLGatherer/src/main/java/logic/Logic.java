package logic;

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

    public void searchForStops(String stop) throws IOException {
        List<String[]> trips = this.API.makeHttpRequest(true,"Herttoni");

        for(int i = 0; i < trips.size(); i++) {
            System.out.println(Arrays.toString(trips.get(i)));
        }
    }

    public void addStop(String stop) {

    }
}
