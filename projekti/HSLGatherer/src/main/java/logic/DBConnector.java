package logic;

import javax.xml.transform.Result;
import java.io.File;
import java.sql.*;
import java.util.*;

public class DBConnector {

    private Connection db;
    private boolean test;

    public DBConnector(boolean test) throws SQLException {
        try {
            if(test) {
                this.db = DriverManager.getConnection("jdbc:sqlite:test.db");
            } else {
                this.db = DriverManager.getConnection("jdbc:sqlite:hsldatabase.db");
            }

            createTables();
        } catch (SQLException e) {
        }
    }

    public void createTables() throws SQLException {

        Statement stmt = db.createStatement();

        try {
            stmt.execute("PRAGMA foreign_keys = ON");
            stmt.execute("CREATE TABLE Stops (id INTEGER PRIMARY KEY, name TEXT UNIQUE)");
            stmt.execute("CREATE TABLE Trips (id INTEGER PRIMARY KEY, sign TEXT, stop_id INTEGER REFERENCES Stops(id) ON DELETE CASCADE, route TEXT, UNIQUE(sign, route))");
        } catch (SQLException e) {
        }
    }

    public boolean addStop(String stop) throws SQLException {
        Statement stmt = db.createStatement();
        stmt.execute("BEGIN TRANSACTION");

        boolean saved = false;

        try {
            stmt.execute("INSERT INTO Stops (name) VALUES ('" + stop + "')");
            saved = true;
        } catch (SQLException e) {
            System.out.println("\nStop is already saved! Continuing..");
        }

        stmt.execute("COMMIT");

        return saved;
    }

    public boolean addTrip(String sign, String route, String stop) throws SQLException {
        Statement stmt = db.createStatement();
        stmt.execute("BEGIN TRANSACTION");

        boolean saved = false;

        try {
            stmt.execute("INSERT INTO Trips (sign, stop_id, route) " +
                    "VALUES ('" + sign + "', (SELECT id FROM Stops WHERE name = '" + stop + "'), '" + route + "')");
            saved = true;
        } catch (Exception e) {
            System.out.println("\nRoute is already saved! Continuing..");
        }

        stmt.execute("COMMIT");

        return saved;
    }

    public ResultSet getSavedStops() throws SQLException {
        ResultSet rs = null;

        try {
            Statement stmt = db.createStatement();
            rs = stmt.executeQuery("SELECT name FROM Stops");
        } catch (NullPointerException e) {
        }

        return rs;
    }

    public ResultSet getSavedRoutes() throws SQLException {
        ResultSet rs = null;

        try {
            Statement stmt = db.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Trips");
        } catch (Exception e) {
        }

        return rs;
    }

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
