package logic;

import javax.xml.transform.Result;
import java.io.File;
import java.sql.*;
import java.util.*;

/**
 * Luokka käsittelee kaiken tietokannan ja ohjelman välillä
 * tapahtuvan kommunikoinnin
 */
public class DBConnector {

    private Connection db;
    private boolean test;

    /**
     * Luo yhteyden tietokantaan. Jos true, niin kyseessä on testien ajo.
     *
     * @param test true jos kyseessä on testi, muuten ei
     *
     * @throws SQLException
     */
    public DBConnector(boolean test) throws SQLException {
        try {
            if (test) {
                this.db = DriverManager.getConnection("jdbc:sqlite:test.db");
            } else {
                this.db = DriverManager.getConnection("jdbc:sqlite:hsldatabase.db");
            }

            createTables();
        } catch (SQLException e) {
        }
    }

    /**
     * Luo ohjelman tarvitsemat taulut, eli
     * taulut pysäkeille ja linjoille.
     *
     * @throws SQLException
     */
    public void createTables() throws SQLException {

        Statement stmt = db.createStatement();

        try {
            stmt.execute("PRAGMA foreign_keys = ON");
            stmt.execute("CREATE TABLE Stops (id INTEGER PRIMARY KEY, name TEXT UNIQUE)");
            stmt.execute("CREATE TABLE Trips (id INTEGER PRIMARY KEY, sign TEXT, stop_id INTEGER REFERENCES Stops(id) ON DELETE CASCADE, route TEXT, UNIQUE(sign, route))");
        } catch (SQLException e) {
        }
    }

    /**
     * Lisää Stops-tauluun uuden pysäkin.
     *
     * @param stop käyttäjän valitsema pysäkki
     *
     * @return true jos lisääminen onnistui, false jos ei
     *
     * @throws SQLException
     */
    public boolean addStop(String stop) throws SQLException {
        Statement stmt = db.createStatement();
        stmt.execute("BEGIN TRANSACTION");

        boolean saved = false;

        try {
            stmt.execute("INSERT INTO Stops (name) VALUES ('" + stop + "')");
            saved = true;
        } catch (SQLException e) {
        }

        stmt.execute("COMMIT");

        return saved;
    }

    /**
     * Lisää Trips-tauluun uuden käyttäjän valitseman linjan.
     *
     * @param sign linjan tunnus
     * @param route linjan nimi
     * @param stop pysäkin nimi, mihin viitaten linja tallennetaan
     *
     * @return true jos lisääminen onnistui, false jos ei
     *
     * @throws SQLException
     */
    public boolean addTrip(String sign, String route, String stop) throws SQLException {
        Statement stmt = db.createStatement();
        stmt.execute("BEGIN TRANSACTION");

        boolean saved = false;

        try {
            stmt.execute("INSERT INTO Trips (sign, stop_id, route) " +
                    "VALUES ('" + sign + "', (SELECT id FROM Stops WHERE name = '" + stop + "'), '" + route + "')");
            saved = true;
        } catch (Exception e) {
        }

        stmt.execute("COMMIT");

        return saved;
    }

    /**
     * Etsii käyttäjän tietokantaan lisäämät pysäkit.
     *
     * @return käyttäjän tietokantaan lisäämät pysäkit
     *
     * @throws SQLException
     */
    public ResultSet getSavedStops() throws SQLException {
        ResultSet rs = null;

        try {
            Statement stmt = db.createStatement();
            rs = stmt.executeQuery("SELECT name FROM Stops");
        } catch (NullPointerException e) {
        }

        return rs;
    }

    /**
     * Etsii käyttäjän tietokantaan lisäämät linjat.
     *
     * @return käyttäjän tietokantaan lisäämät linjat
     *
     * @throws SQLException
     */
    public ResultSet getSavedRoutes() throws SQLException {
        ResultSet rs = null;

        try {
            Statement stmt = db.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Trips");
        } catch (Exception e) {
        }

        return rs;
    }

    /**
     * Poistaa parametrina saatavan pysäkin tietokannasta.
     *
     * @param stop käyttäjän valitsema poistettava pysäkki
     *
     * @return true jos poistaminen onnistui, false jos ei
     *
     * @throws SQLException
     */
    public boolean deleteStop(String stop) throws SQLException {
        Statement stmt = db.createStatement();
        boolean deleted = false;

        try {
            stmt.execute("DELETE FROM Stops WHERE name = '" + stop + "'");
            deleted = true;
        } catch (Exception e) {
            deleted = false;
        }

        return deleted;
    }

    /**
     * Poistaa parametrina saatavan linjan tunnuksen ja nimen mukaan
     * linjan tietokannasta.
     *
     * @return true jos poistaminen onnistui, false jos ei
     *
     * @throws SQLException
     */
    public boolean deleteRoute(String sign, String route) throws SQLException {
        Statement stmt = db.createStatement();
        boolean deleted = false;

        try {
            stmt.execute("DELETE FROM Trips WHERE sign = '" + sign + "' AND route = '" + route + "'");
            deleted = true;
        } catch (Exception e) {
        }

        return deleted;
    }

    /**
     * Poistaa tietokannan, käytössä vain testejä suorittaessa.
     *
     * @return true jos poistaminen onnistui, false jos ei
     *
     * @throws SQLException
     */
    public boolean deleteDb() throws SQLException {
        try {
            Statement stmt = db.createStatement();

            stmt.close();
            this.db.close();

            return new File("test.db").delete();
        } catch (Exception e) {
            return false;
        }
    }
}
