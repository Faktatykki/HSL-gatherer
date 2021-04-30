package services;

import logic.*;
import classes.*;

import javax.swing.event.TreeSelectionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.*;

/**
 * Luokka toimii sovelluslogiikan ja käyttöliittymän välisenä
 * kommunikoijana
 */
public class Service {

    Logic l;

    public Service(boolean test) throws SQLException, MalformedURLException {
        l = new Logic(test);
    }

    /**
     * Välittää käyttäjän syöttämän hakuparametrin logic-luokan
     * searchStops(String) metodille. Vastauksena saadaan TreeSet
     * joka on valmiiksi järjestyksessä, joka muutetaan ArrayListaksi.
     *
     * @param stop käyttäjän syöttämä hakuparametri
     *
     * @see logic.Logic#searchStops(String)
     *
     * @return lista hakutuloksista
     *
     * @throws IOException
     */
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

    /**
     * Välittää käyttäjän valitseman parametrin logic-luokan
     * addStopToDb(String)-metodille jonka kautta lisätään
     * pysäkki tietokantaan.
     *
     * @param stop käyttäjän valitsema pysäkkiparametri
     *
     * @see logic.Logic#addStopToDb(String)
     *
     * @return true jos lisääminen onnistui, false jos ei
     *
     * @throws SQLException
     */
    public boolean addStop(String stop) throws SQLException {
        if (stop == null) {
            return false;
        }

        return l.addStopToDb(stop);
    }

    /**
     * Välittää käyttäjän valitseman parametrin logic-luokan
     * addTripToDb(Trip, String)-metodille, jonka kautta lisätään
     * reitti tietokantaan.
     *
     * @param trip käyttäjän valitsema reittiparametri
     * @param stop käyttäjän valitsema pysäkkiparametri
     *
     * @see logic.Logic#addTripToDb(Trip, String)
     *
     * @return true jos lisääminen onnistui, false jos ei
     *
     * @throws SQLException
     */
    public boolean addTrip(Trip trip, String stop) throws SQLException {
        if (trip == null || stop == null || trip.getRoute() == null || trip.getSign() == null) {
            return false;
        }

        return l.addTripToDb(trip, stop);
    }

    /**
     * Välittää hakuparametrin logic-luokan searchForTrips(String)
     * metodille. Vastauksena saadaan lista joka järjestetään.
     *
     * @param stop pysäkki miltä linjoja haetaan
     *
     * @see logic.Logic#searchForTrips(String)
     *
     * @return lista haulla löydetyistä aikatauluista
     *
     * @throws IOException
     */
    public List<Trip> searchForTrips(String stop) throws IOException {
        if (stop == null) {
            return null;
        }

        List<Trip> trips = l.searchForTrips(stop);

        Collections.sort(trips);

        return trips;
    }

    /**
     * Välittää hakuparametrin Logic-luokan searchForTrips(String)
     * metodille. Vastauksena saadaan lista löydetyistä aikatauluista,
     * josta tehdään TreeSet, jolloin duplikaatit karsitaan ja tulos
     * on valmiiksi järjestyksessä. Tämän jälkeen muodostetaan tuloksista lista.
     * Metodin tarkoitus on näyttää yksittäiset linjat ilman kaikkia aikatauluja.
     *
     * @param stop käyttäjän valitsema pysäkkiparametri
     *
     * @see logic.Logic#searchForTrips(String)
     *
     * @return lista haulla löydetyistä reiteistä
     *
     * @throws IOException
     */
    public List<Trip> searchForRoutes(String stop) throws IOException {
        if (stop == null) {
            return null;
        }

        Set<Trip> tempTrips = new TreeSet<>(l.searchForTrips(stop));

        return new ArrayList<>(tempTrips);
    }

    /**
     * Haetaan tietokantaan tallennetut pysäkit kutsumalla
     * Logic-luokan getSavedStops()-metodia.
     *
     * @see logic.Logic#getSavedStops()
     *
     * @return lista tietokantaan tallennetuista pysäkeistä
     *
     * @throws SQLException
     */
    public List<String> getSavedStops() throws SQLException {
        return l.getSavedStops();
    }

    /**
     * Haetaan tietokantaan tallennetut linjat kutsumalla
     * Logic-luokan getSavedRoutes()-metodia.
     *
     * @see logic.Logic#getSavedRoutes()
     *
     * @return lista tietokantaan tallennetuista linjoista
     *
     * @throws SQLException
     */
    public Set<Trip> getSavedRoutes() throws SQLException {
        return l.getSavedRoutes();
    }

    /**
     * Välittää parametrin, jonka mukaan poistetaan tietokannasta
     * pysäkki kutsumalla Logic-luokan deleteStop(String)-metodia.
     *
     * @param stop käyttäjän valitsema poistettava pysäkki
     *
     * @return true jos poistaminen onnistui, false jos ei
     *
     * @throws SQLException
     */
    public boolean deleteStop(String stop) throws SQLException {
        if (stop == null) {
            return false;
        }

        return l.deleteStop(stop);
    }

    /**
     * Välittää parametrin, jonka mukaan poistetaan tietokannasta
     * linja kutsumalla Logic-luokan deleteRoute(Trip)-metodia.
     *
     * @param trip käyttäjän valitsema poistettava linja
     *
     * @return true jos poistaminen onnistui, false jos ei
     *
     * @throws SQLException
     */
    public boolean deleteRoute(Trip trip) throws SQLException {
        if (trip == null || trip.getSign() == null || trip.getRoute() == null) {
            return false;
        }

        return l.deleteRoute(trip);
    }

    /**
     * Kutsuu Logic-luokan metodia deleteTestDb(), joka poistaa
     * tietokannan testauksen yhteydessä.
     *
     * @return true jos poistaminen onnistui, false jos ei
     *
     * @throws SQLException
     */
    public boolean deleteTestDb() throws SQLException {
        return l.deleteTestDb();
    }
}
