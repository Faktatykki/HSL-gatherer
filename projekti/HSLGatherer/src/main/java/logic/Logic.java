package logic;

import classes.*;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Luokka on vastuussa koko sovelluslogiikasta
 * ja toimii myös Service-luokan ja muiden logiikan komponenttien
 * (tietokanta, rajapinta) välisenä kommunikoijana
 */
public class Logic {

    private DBConnector db;
    private APIhandle api;

    public Logic(boolean test) throws SQLException, MalformedURLException {
        this.db = new DBConnector(test);
        this.api = new APIhandle();
    }

    /**
     * Etsii reittejä ja niiden aikatauluja pysäkkikohtaisesti
     * ottaen yhteyttä rajapinnan käsittelijään "APIhandleen".
     * Metodi luo APIhandlen palauttaman listan taulukoista
     * Trip-olioita.
     *
     * @param stop käyttäjän syöttämä hakuparametri
     *
     * @see logic.APIhandle#makeHttpRequest(boolean, String)
     *
     * @return käyttäjän syöttämän parametrin mukaan hakutulokset
     *
     * @throws IOException
     */
    public List<Trip> searchForTrips(String stop) throws IOException {
        List<String[]> graphResp = this.api.makeHttpRequest(false, stop);
        List<Trip> trips = new ArrayList<>();

        for (int i = 0; i < graphResp.size(); i++) {
            String[] arr = graphResp.get(i);
            trips.add(new Trip(arr[0], arr[1], arr[2], arr[3], arr[4]));
        }

        return trips;
    }

    /**
     * Etsii pysäkkejä ottaen yhteyttä rajapinnan käsittelijään
     * "APIhandleen". Metodi luo APIhandlen palauttaman listan
     * taulukoista Stop-olioita.
     *
     * @param stop käyttäjän syöttämä hakuparametri
     *
     * @see logic.APIhandle#makeHttpRequest(boolean, String)
     *
     * @return käyttäjän syöttämän parametrin mukaan hakutulokset
     *
     * @throws IOException
     */
    public TreeSet<Stop> searchStops(String stop) throws IOException {
        List<String[]> graphResp = this.api.makeHttpRequest(true, stop);
        TreeSet<Stop> stops = new TreeSet<>();

        for (int i = 0; i < graphResp.size(); i++) {
            String[] arr = graphResp.get(i);
            stops.add(new Stop(arr[0]));
        }

        return stops;
    }

    /**
     * Kutsuu DBConnector-luokan metodia addStop(String),
     * joka lisää tietokantaan parametrina saadun pysäkin.
     *
     * @param stop käyttäjän valitsema pysäkki
     *
     * @see logic.DBConnector#addStop(String)
     *
     * @return true jos lisääminen onnistui, muuten false
     *
     * @throws SQLException
     */
    public boolean addStopToDb(String stop) throws SQLException {
        return db.addStop(stop);
    }

    /**
     * Kutsuu DBConnector-luokan metodia addTrip(String, String),
     * joka lisää tietokantaan linjan parametrina saatujen linjan
     * ja pysäkin mukaan.
     *
     * @param trip käyttäjän valitsema reitti
     * @param stop valitsemaan reittiin liittyvä pysäkki
     *
     * @see logic.DBConnector#addTrip(String, String, String)
     *
     * @return true jos lisääminen onnistui, muuten false
     *
     * @throws SQLException
     */
    public boolean addTripToDb(Trip trip, String stop) throws SQLException {
        String sign = trip.getSign();
        String route = trip.getRoute();

        return db.addTrip(sign, route, stop);
    }

    /**
     * Hakee tietokantaan tallennetut pysäkit kutsuen
     * DBConnector-luokan metodia getSavedStops(). Metodi
     * muuttaa tietokannan palauttaman ResultSet
     * vastauksen ArrayListaksi ja järjestää sen.
     *
     * @see logic.DBConnector#getSavedStops()
     *
     * @return käyttäjän tietokantaan tallettamat pysäkit
     *
     * @throws SQLException
     */
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

    /**
     * Hakee tietokantaan tallennetut reitit kutsuen
     * DBConnector-luokan metodia getSavedRoutes(). Metodi
     * muuttaa tietokannan palauttaman ResultSet
     * vastauksen HashSet ja täten karsii mahdolliset
     * duplikaatit.
     *
     * @return käyttäjän tietokantaan tallettamat reitit
     *
     * @throws SQLException
     */
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

    /**
     * Poistaa tietokantaan tallennetun pysäkin kutsuen DBConnector-luokan
     * metodia deleteStop(String).
     *
     * @param stop käyttäjän käyttöliittymästä valitsema parametri
     *
     * @see logic.DBConnector#deleteStop(String)
     *
     * @return true jos poistaminen onnistui, false jos ei
     *
     * @throws SQLException
     */
    public boolean deleteStop(String stop) throws SQLException {
        return db.deleteStop(stop);
    }

    /**
     * Poistaa tietokantaan tallennetun reitin kutsuen DBConnector-luokan
     * metodia deleteRoute(String, String).
     *
     * @param trip käyttäjän käyttöliittymästä valitsema parametri
     *
     * @see logic.DBConnector#deleteRoute(String, String)
     *
     * @return true jos poistaminen onnistui, false jos ei
     *
     * @throws SQLException
     */
    public boolean deleteRoute(Trip trip) throws SQLException {
        String sign = trip.getSign();
        String route = trip.getRoute();

        return db.deleteRoute(sign, route);
    }

    /**
     * Poistaa tietokannan. Ainoastaan käytössä testien suorituksen aikana.
     *
     * @return true jos poistaminen onnistui, false jos ei
     *
     * @throws SQLException
     */
    public boolean deleteTestDb() throws SQLException {
        return db.deleteDb();
    }
}
