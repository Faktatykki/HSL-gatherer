package logic;

import java.sql.*;

public class DBConnector {

    private Connection db;

    public DBConnector() throws SQLException {
        try {
            this.db = DriverManager.getConnection("jdbc:sqlite:hsldatabase.db");
            createTables();
        } catch(SQLException e) {
        }
    }

    public void createTables() throws SQLException {

        Statement stmt = db.createStatement();

        try {
            stmt.execute("PRAGMA foreign_keys = ON");
            stmt.execute("CREATE TABLE Stops (id INTEGER PRIMARY KEY, name TEXT UNIQUE)");
            stmt.execute("CREATE TABLE Trips (id INTEGER PRIMARY KEY, sign TEXT UNIQUE, stop_id INTEGER REFERENCES Stops(id), route TEXT UNIQUE)");
        } catch(SQLException e) {
        }
    }

    public void addStop(String stop) throws SQLException {
        Statement stmt = db.createStatement();

        try {
            stmt.execute("INSERT INTO Stops (nimi) VALUES ('" + stop + "')");
        } catch (SQLException e) {
        }

        stmt.execute("COMMIT");
    }

    //public void addTrip
}
