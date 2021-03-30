package ui;

import services.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.*;
import logic.*;

public class TextUI {

    Scanner s;
    Service service;
    Logic l;

    public TextUI() throws MalformedURLException, SQLException {
        this.s = new Scanner(System.in);
        this.l = new Logic();
        this.service = new Service();
    }

    public void start() throws IOException {
        generalView();
    }

    public void generalView() throws IOException {

        while(true) {
            System.out.println("=======================");
            System.out.println("***********************");
            System.out.println("=======================");
            System.out.println("Welcome to HSL-Gatherer");
            System.out.println("=======================");
            System.out.println("***********************");
            System.out.println("=======================\n");

            System.out.println("1. Search Trips");
            System.out.println("2. Search Stops");
            System.out.println("3. Exit\n");

            System.out.println("(Navigate by typing a number and pressing 'Enter')\n");

            System.out.println("Type a number and press 'Enter': ");

            String nav = s.nextLine();

            System.out.println("\n");

            if(nav.equals("1")) {
                searchTrips();
                continue;
            }
            if(nav.equals("2")) {
                searchStops();
                continue;
            }
            if(nav.equals("3")) break;
        }
    }

    public void searchTrips() throws IOException {
        System.out.println("================");
        System.out.println("****************");
        System.out.println("================");
        System.out.println("SEARCH FOR STOPS");
        System.out.println("================");
        System.out.println("****************");
        System.out.println("================\n");

        System.out.println("Type a stop name and press 'Enter': ");
        String param = s.nextLine();
        System.out.println();

        System.out.println("SCHEDULES\n");
        l.searchForTrips(param);
	System.out.println("\n================\n\n");
    }

    public void searchStops() throws IOException {
        System.out.println("================");
        System.out.println("****************");
        System.out.println("================");
        System.out.println("SEARCH FOR STOPS");
        System.out.println("================");
        System.out.println("****************");
        System.out.println("================\n");

        System.out.println("Type a stop name and press 'Enter': ");
        String param = s.nextLine();
        System.out.println();

        System.out.println("STOPS\n");
        l.searchStops(param);
	System.out.println("\n================\n\n");
    }
}
