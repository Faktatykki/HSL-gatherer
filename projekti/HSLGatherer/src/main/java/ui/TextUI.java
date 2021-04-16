package ui;

import services.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import classes.*;

public class TextUI {

    Scanner s;
    Service service;

    public TextUI() throws MalformedURLException, SQLException {
        this.s = new Scanner(System.in);
        this.service = new Service();
    }

    public void start() throws IOException, InterruptedException, SQLException {
        addSpaces();
        generalView();
    }

    public void generalView() throws IOException, InterruptedException, SQLException {

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
            System.out.println("3. Add Stop");
            System.out.println("4. Add Trip");
            System.out.println("5. Show Stops");
            System.out.println("6. Delete Stops or Routes");
            System.out.println("7. Exit\n");

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
            if(nav.equals("3")) {
                addSpaces();
                addStop();
            }
            if(nav.equals("4")) {
                addSpaces();
                addTrips();
            }
            if(nav.equals("5")) {
                addSpaces();
                showSavedRoutes();
            }
            if(nav.equals("6")) {
                addSpaces();
                deleteStopsOrTrips();
            }
            if(nav.equals("7")) break;
        }

        addSpaces();
    }

    public void searchTrips() throws IOException {
        System.out.println("================");
        System.out.println("****************");
        System.out.println("================");
        System.out.println("SEARCH FOR TRIPS");
        System.out.println("================");
        System.out.println("****************");
        System.out.println("================\n");

        System.out.println("Type a stop name and press 'Enter': ");
        String param = s.nextLine();
        System.out.println();

        System.out.println("SCHEDULES\n");
        System.out.println("\n==============\n\n");

        List<Trip> trips = service.searchForTrips(param);

        for(int i = 0; i < trips.size(); i++) {
            System.out.println(trips.get(i));
        }

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

        List<Stop> stops = service.searchForStops(param);

        for(int i = 0; i < stops.size(); i++) {
            System.out.println(stops.get(i));
        }

        System.out.println("\n===============\n\n");
        System.out.println("Press 'Enter' to continue");
        String cont = s.nextLine();
        addSpaces();
    }

    public void addStop() throws IOException, InterruptedException, SQLException {
        System.out.println("==============================");
        System.out.println("******************************");
        System.out.println("==============================");
        System.out.println("SEARCH AND POSSIBLY ADD a STOP");
        System.out.println("==============================");
        System.out.println("******************************");
        System.out.println("==============================\n");

        System.out.println("Type a stop name and press 'Enter': ");
        String param = s.nextLine();
        System.out.println();

        System.out.println("STOPS\n");
        System.out.println("\n===============\n\n");

        List<Stop> stops = service.searchForStops(param);

        for(int i = 0; i < stops.size(); i++) {
            System.out.println(i + 1 + ". " + stops.get(i));
        }

        System.out.println("\n===============\n\n");
        System.out.println("Press 'Enter' to continue or save stop by typing a number and press 'Enter': ");

        param = s.nextLine();

        if(!param.isBlank()) {
            String stop = null;

            try {
                stop = stops.get(Integer.parseInt(param) - 1).getName();
            } catch(Exception e) {
                System.out.println("Something went very wrong, I guess you put something else than numbers, you twat");
                TimeUnit.SECONDS.sleep(3);
                addSpaces();
                return;
            }

            if(service.addStop(stop)) {
                System.out.println("\nSaved! Continuing..");
            }

            TimeUnit.SECONDS.sleep(3);
        }

        addSpaces();
    }

    public void addTrips() throws SQLException, InterruptedException {
        System.out.println("====================");
        System.out.println("********************");
        System.out.println("====================");
        System.out.println("SAVED STOPS ARE HERE");
        System.out.println("====================");
        System.out.println("********************");
        System.out.println("====================\n");

        List<String> stops = service.getSavedStops();

        while(true) {
            System.out.println("SAVED STOPS\n");
            System.out.println("\n===============\n\n");

            for (int i = 0; i < stops.size(); i++) {
                System.out.println(i + 1 + ". " + stops.get(i));
            }

            System.out.println("\n===============\n\n");
            System.out.println("Press 'Enter' to exit or search specific routes to be saved by typing a number and press 'Enter': ");

            String param = s.nextLine();

            if (!param.isBlank()) {
                String stop = null;

                try {
                    stop = stops.get(Integer.parseInt(param) - 1);
                    addSpaces();
                    addRoute(stop);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Something went very wrong, I guess you put something else than numbers, you twat");
                    TimeUnit.SECONDS.sleep(3);
                    addSpaces();
                    return;
                }

            } else {
                break;
            }
        }

        addSpaces();
    }

    public void addRoute(String stop) throws IOException, InterruptedException, SQLException {
        System.out.println("==============");
        System.out.println("   ROUTES     ");
        System.out.println("==============\n");

        List<Trip> routes = service.searchForRoutes(stop);

        for(int i = 0; i < routes.size(); i++) {
            System.out.println(i + 1 + ". SIGN: " + routes.get(i).getSign() + " || ROUTE: " + routes.get(i).getRoute());
        }

        System.out.println("\n===============\n\n");
        System.out.println("Press 'Enter' to exit or save route by typing a number and press 'Enter': ");

        String param = s.nextLine();

        if(!param.isBlank()) {
            Trip route = null;

            try {
                route = routes.get(Integer.parseInt(param) - 1);
            } catch(Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Something went very wrong, I guess you put something else than numbers, you twat");
                TimeUnit.SECONDS.sleep(3);
                addSpaces();
                return;
            }

            if(service.addTrip(route, stop)) {
                System.out.println("\nSaved! Continuing..");
            }

            TimeUnit.SECONDS.sleep(3);
        }

        addSpaces();
    }

    public void showSavedRoutes() throws SQLException, IOException {
        System.out.println("================");
        System.out.println("  SAVED ROUTES  ");
        System.out.println("================\n");

        List<String> stops = service.getSavedStops();
        Set<Trip> routes = service.getSavedRoutes();

        for(String stop: stops) {
            List<Trip> trips = service.searchForTrips(stop);

            System.out.println("\n" + stop + "\n");

            Trip prev = null;

            for(Trip trip: trips) {

                if (routes.contains(trip)) {
                    if(!trip.equals(prev)) System.out.println();
                    System.out.println(trip);
                    prev = trip;
                }
            }

            System.out.println();
        }

        System.out.println("\n===============\n\n");
        System.out.println("Press 'Enter' to continue");
        String cont = s.nextLine();
        addSpaces();
    }

    public void deleteStopsOrTrips() throws IOException, SQLException, InterruptedException {
        System.out.println("======================");
        System.out.println("DELETE ROUTES OR STOPS");
        System.out.println("======================\n");

        System.out.println("1. Stop");
        System.out.println("2. Route\n");

        System.out.println("Press 'Enter' to continue or navigate by typing a number and press 'Enter': ");
        String nav = s.nextLine();

        if(nav.isBlank()) {
            return;
        }
        if(nav.equals("1")) {
            addSpaces();
            deleteStops();
        }
        if(nav.equals("2")) {
            addSpaces();
            deleteRoutes();
        }
    }

    public void deleteStops() throws SQLException, IOException, InterruptedException {
        System.out.println("==================");
        System.out.println("   SAVED STOPS    ");
        System.out.println("==================\n");

        List<String> stops = service.getSavedStops();

        for(int i = 0; i < stops.size(); i++) {
            System.out.println(i + 1 + ". " + stops.get(i));
        }

        System.out.println("\nPress 'Enter' to continue or type a stop number to be removed");
        String param = s.nextLine();

        if(!param.isBlank()) {
            String stop = null;

            try {
                stop = stops.get(Integer.parseInt(param) - 1);
            } catch(Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Something went very wrong, I guess you put something else than numbers, you twat");
                TimeUnit.SECONDS.sleep(3);
                addSpaces();
                return;
            }

            if(service.deleteStop(stop)) {
                System.out.println("\nDeleted! Continuing..");
            }

            TimeUnit.SECONDS.sleep(3);
        }

        addSpaces();
    }

    public void deleteRoutes() throws SQLException, InterruptedException {
        System.out.println("==============");
        System.out.println(" SAVED ROUTES ");
        System.out.println("==============\n");

        ArrayList<Trip> routes = new ArrayList<>(service.getSavedRoutes());

        Collections.sort(routes);

        for(int i = 0; i < routes.size(); i++) {
            System.out.println(i + 1 + ". " + routes.get(i));
        }

        System.out.println("\nPress 'Enter' to continue or type a stop number to be removed");
        String param = s.nextLine();

        if(!param.isBlank()) {
            Trip route = null;

            try {
                route = routes.get(Integer.parseInt(param) - 1);
            } catch(Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Something went very wrong, I guess you put something else than numbers, you twat");
                TimeUnit.SECONDS.sleep(3);
                addSpaces();
                return;
            }

            if(service.deleteRoute(route)) {
                System.out.println("\nDeleted! Continuing..");
            }

            TimeUnit.SECONDS.sleep(3);
        }

        addSpaces();
    }

    public void addSpaces() {
        for(int i = 0; i <= 50; i++) {
            System.out.println();
        }
    }
}