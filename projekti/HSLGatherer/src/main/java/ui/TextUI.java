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

    public TextUI() throws MalformedURLException, SQLException {
        this.s = new Scanner(System.in);
        this.service = new Service();
    }

    public void start() throws IOException {
	addSpaces();
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
		addSpaces();
                searchTrips();
            }
            if(nav.equals("2")) {
		addSpaces();
                searchStops();
            }
            if(nav.equals("3")) break;
        }

	addSpaces();
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
	System.out.println("\n==============\n\n");
        service.searchForTrips(param);
        System.out.println("\n==============\n\n");
	System.out.println("Press 'Enter' to continue");
	String cont = s.nextLine();
	addSpaces();
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
	System.out.println("\n===============\n\n");
        service.searchForStops(param);
        System.out.println("\n===============\n\n");
	System.out.println("Press 'Enter' to continue");
	String cont = s.nextLine();
	addSpaces();
    }

    public void addSpaces() {
    	for(int i = 0; i <= 50; i++) {
		System.out.println();
	}
    }
}
